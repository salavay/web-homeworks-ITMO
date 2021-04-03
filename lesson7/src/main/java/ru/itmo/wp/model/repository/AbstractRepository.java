package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.exception.RepositoryException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRepository<T> {
    private final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    protected T findBy(T ent, String statement, Setter<T, PreparedStatement> statementSetter) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS)) {
                statementSetter.set(ent, preparedStatement);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return toEntity(preparedStatement.getMetaData(), resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find", e);
        }
    }

    public void save(T ent, String statement, Setter<T, PreparedStatement> statementSetter,
                     Setter<T, ResultSet> entitySetter) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    statement,
                    Statement.RETURN_GENERATED_KEYS)) {
                statementSetter.set(ent, preparedStatement);
                if (preparedStatement.executeUpdate() != 1) {
                    throw new RepositoryException("Can't save entity.");
                } else {
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        entitySetter.set(ent, generatedKeys);
                    } else {
                        throw new RepositoryException("Can't save entity [no autogenerated fields].");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't save entity.", e);
        }
    }

    protected List<T> findAllEnt(String statement) {
        List<T> entities = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    T entity;
                    while ((entity = toEntity(preparedStatement.getMetaData(), resultSet)) != null) {
                        entities.add(entity);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find.", e);
        }
        return entities;
    }

    protected List<T> findByPred(T entity, String statement, Setter<T, PreparedStatement> statementSetter) {
        List<T> entities = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
                statementSetter.set(entity, preparedStatement);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    T curEntity;
                    while ((curEntity = toEntity(preparedStatement.getMetaData(), resultSet)) != null) {
                        entities.add(curEntity);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find.", e);
        }
        return entities;
    }

    protected T update(T entity, String statement, Setter<T, PreparedStatement> preparedStatementSetter) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatementSetter.set(entity, preparedStatement);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return toEntity(preparedStatement.getMetaData(), resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't update", e);
        }
    }

    protected T toEntity(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        return null;
    }
}
