package uz.pdp.task1.jdbc_template;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TodoRowMapper implements RowMapper<Todo> {
    @Override
    public Todo mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Todo.builder()
                .id(resultSet.getInt("id"))
                .title(resultSet.getString("title"))
                .priority(resultSet.getString("priority"))
                .created_at(resultSet.getTimestamp("created_at").toLocalDateTime())
                .build();
    }
}
