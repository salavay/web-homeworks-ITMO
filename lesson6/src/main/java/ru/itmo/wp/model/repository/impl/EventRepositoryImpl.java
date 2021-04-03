package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.event.Event;
import ru.itmo.wp.model.domain.event.EventType;
import ru.itmo.wp.model.repository.AbstractRepository;
import ru.itmo.wp.model.repository.EventRepository;

import java.sql.*;

public class EventRepositoryImpl extends AbstractRepository<Event> implements EventRepository {

    private final String saveStatement = "INSERT INTO `Event` (`userId`, `type`, `creationTime`) VALUES (?, ?, NOW())";
    private final String findByIdStatement = "SELECT * FROM Event WHERE id=?";


    private void saveStatementSetter(Event event, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, event.getUserId());
        preparedStatement.setString(2, event.getType().toString());
    }

    private void saveEntitySetter(Event event, ResultSet generatedKeys) throws SQLException {
        event.setId(generatedKeys.getLong(1));
        event.setCreationTime(find(event.getId()).getCreationTime());
    }

    private void findByIdStatementSetter(Event event, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, event.getId());
    }

    @Override
    public void save(Event event) {
        super.save(event, saveStatement, this::saveStatementSetter, this::saveEntitySetter);
    }

    public Event find(long id) {
        Event ent = new Event();
        ent.setId(id);
        return super.findBy(ent, findByIdStatement, this::findByIdStatementSetter);
    }

    @Override
    protected Event toEntity(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        Event event = new Event();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    event.setId(resultSet.getLong(i));
                    break;
                case "userId":
                    event.setUserId(resultSet.getLong(i));
                    break;
                case "type":
                    event.setType(EventType.valueOf(resultSet.getString(i)));
                    break;
                case "creationTime":
                    event.setCreationTime(resultSet.getTimestamp(i));
                    break;
                default:
                    // No operations.
            }
        }

        return event;
    }
}
