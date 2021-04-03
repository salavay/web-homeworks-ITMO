package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.talks.Talk;
import ru.itmo.wp.model.repository.AbstractRepository;
import ru.itmo.wp.model.repository.TalkRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

public class TalkRepositoryImpl extends AbstractRepository<Talk> implements TalkRepository {

    private final String saveStatement = "INSERT INTO `Talk`  ( `sourceUserId`, `targetUserId`, `text`, `creationTime`) VALUES (?, ?, ?, NOW())";
    private final String findAllStatement = "SELECT * FROM Talk ORDER BY id DESC";
    private final String findPrivateStatement = "SELECT * FROM Talk WHERE (sourceUserId=? OR targetUserId=?)";

    private void saveStatementSetter(Talk talk, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, talk.getSourceUserId());
        preparedStatement.setLong(2, talk.getTargetUserId());
        preparedStatement.setString(3, talk.getText());
    }

    private void saveEntitySetter(Talk talk, ResultSet generatedKeys) throws SQLException {
        talk.setId(generatedKeys.getLong(1));
    }

    private void findPrivateStatement(Talk talk, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, talk.getSourceUserId());
        preparedStatement.setLong(2, talk.getSourceUserId());
    }

    @Override
    public void save(Talk talk) {
        super.save(talk, saveStatement, this::saveStatementSetter, this::saveEntitySetter);
    }

    @Override
    public List<Talk> findAll() {
        List<Talk> check = super.findAllEnt(findAllStatement);
        return super.findAllEnt(findAllStatement);
    }

    @Override
    public List<Talk> findPrivateMessages(long userId) {
        Talk talk = new Talk();
        talk.setSourceUserId(userId);
        return super.findByPred(talk, findPrivateStatement, this::findPrivateStatement);
    }

    @Override
    protected Talk toEntity(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        Talk talk = new Talk();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    talk.setId(resultSet.getLong(i));
                    break;
                case "sourceUserId":
                    talk.setSourceUserId(resultSet.getLong(i));
                    break;
                case "targetUserId":
                    talk.setTargetUserId(resultSet.getLong(i));
                    break;
                case "text":
                    talk.setText(resultSet.getString(i));
                    break;
                case "creationTime":
                    talk.setCreationTime(resultSet.getTimestamp(i));
                    break;
                default:
                    // No operations.
            }
        }
        return talk;
    }
}
