package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.AbstractRepository;
import ru.itmo.wp.model.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserRepositoryImpl extends AbstractRepository<UserRepositoryImpl.UserWithPasswordsha> implements UserRepository {
    private final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    private final String saveStatement = "INSERT INTO `User` (`login`,`email`, `passwordSha`, `creationTime`) VALUES (?, ?, ?, NOW())";
    private final String findByIdStatement = "SELECT * FROM User WHERE id=?";
    private final String findByLoginOrEmailStatement = "SELECT * FROM User WHERE (login=? OR email=?)";
    private final String findByLoginOrEmailAndPasswordshaStatement = "SELECT * FROM User WHERE (login=? OR email=?) AND passwordSha=?";
    private final String findAllStatement = "SELECT * FROM User ORDER BY id DESC";
    private final String findCountStatement = "SELECT COUNT(*) FROM User";

    private void saveStatementSetter(UserWithPasswordsha userWithPasswordsha, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, userWithPasswordsha.getUser().getLogin());
        preparedStatement.setString(2, userWithPasswordsha.getUser().getEmail());
        preparedStatement.setString(3, userWithPasswordsha.getPasswordsha());
    }

    private void saveEntitySetter(UserWithPasswordsha userWithPasswordsha, ResultSet generatedKeys) throws SQLException {
        userWithPasswordsha.getUser().setId(generatedKeys.getLong(1));
        userWithPasswordsha.getUser().setCreationTime(find(userWithPasswordsha.getUser().getId()).getCreationTime());
    }


    private void findByIdSetter(UserWithPasswordsha userWithPasswordsha, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, userWithPasswordsha.getUser().getId());
    }

    private void findByLoginOrEmailStatementSetter(UserWithPasswordsha userWithPasswordsha, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, userWithPasswordsha.getUser().getLogin());
        preparedStatement.setString(2, userWithPasswordsha.getUser().getEmail());
    }

    private void findByLoginOrEmailAndPasswordshaStatement(UserWithPasswordsha userWithPasswordsha, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, userWithPasswordsha.getUser().getLogin());
        preparedStatement.setString(2, userWithPasswordsha.getUser().getEmail());
        preparedStatement.setString(3, userWithPasswordsha.getPasswordsha());
    }

    @Override
    public User find(long id) {
        User user = new User();
        user.setId(id);
        UserWithPasswordsha ent = new UserWithPasswordsha(user, "");
        return safeUnpackUser(super.findBy(ent, findByIdStatement, this::findByIdSetter));
    }

    @Override
    public User findByLoginOrEmail(String loginOrEmail) {
        User user = new User();
        user.setLogin(loginOrEmail);
        user.setEmail(loginOrEmail);
        UserWithPasswordsha ent = new UserWithPasswordsha(user, "");
        return safeUnpackUser(super.findBy(ent, findByLoginOrEmailStatement, this::findByLoginOrEmailStatementSetter));
    }

    @Override
    public User findByLoginOrEmailAndPasswordSha(String loginOrEmail, String passwordSha) {
        User user = new User();
        user.setLogin(loginOrEmail);
        user.setEmail(loginOrEmail);
        UserWithPasswordsha ent = new UserWithPasswordsha(user, passwordSha);
        return safeUnpackUser(super.findBy(ent, findByLoginOrEmailAndPasswordshaStatement, this::findByLoginOrEmailAndPasswordshaStatement));
    }

    @Override
    public List<User> findAll() {
        return super.findAllEnt(findAllStatement).stream().map(this::safeUnpackUser).collect(Collectors.toList());
    }

    @Override
    public void save(User user, String passwordSha) {
        UserWithPasswordsha userWithPasswordsha = new UserWithPasswordsha(user, passwordSha);
        super.save(userWithPasswordsha, saveStatement, this::saveStatementSetter, this::saveEntitySetter);
    }

    @Override
    public long findCount() {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM User")) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    resultSet.next();
                    return resultSet.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't count users.", e);
        }
    }

    @Override
    protected UserWithPasswordsha toEntity(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        User user = new User();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    user.setId(resultSet.getLong(i));
                    break;
                case "login":
                    user.setLogin(resultSet.getString(i));
                    break;
                case "creationTime":
                    user.setCreationTime(resultSet.getTimestamp(i));
                    break;
                case "email":
                    user.setEmail(resultSet.getString(i));
                default:
                    // No operations.
            }
        }
        return new UserWithPasswordsha(user, "");
    }

    protected static class UserWithPasswordsha {
        private User user;
        private String passwordsha;

        public UserWithPasswordsha(User user, String passwordsha) {
            this.user = user;
            this.passwordsha = passwordsha;
        }

        public User getUser() {
            return user;
        }

        public String getPasswordsha() {
            return passwordsha;
        }
    }

    private User safeUnpackUser(UserWithPasswordsha userWithPasswordsha) {
        if (userWithPasswordsha != null) {
            return userWithPasswordsha.getUser();
        }
        return null;
    }
}
