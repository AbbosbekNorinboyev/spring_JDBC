package uz.pdp.task1.jdbc_template;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

public class TodoDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public TodoDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
    }

    public void save(Todo todo) {
        var sql = "insert into task1_jdbc_template(title, priority) values (?,?);";
        jdbcTemplate.update(sql, todo.getTitle(), todo.getPriority());
    }

    public Integer save2(Todo todo) {
        var sql = "insert into task1_jdbc_template(title, priority) values (? ,?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator creator = connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, todo.getTitle());
            preparedStatement.setString(2, todo.getPriority());
            return preparedStatement;
        };
        jdbcTemplate.update(creator, keyHolder);
        return keyHolder.getKey().intValue();
    }

    public void saveSimpleJDBCInsert(Todo todo) {  // bu task2 ni topshirig'i
        var paramSource = Map.of(
                "title", todo.getTitle(),
                "priority", todo.getPriority()
        );
        simpleJdbcInsert.withTableName("task1_jdbc_template")
                .usingColumns("title", "priority")
                .execute(paramSource);
    }

    public void saveSimpleJDBCInsertReturnId(Todo todo) {  // bu task2 ni topshirig'i
        var paramSource = new BeanPropertySqlParameterSource(todo);
        Number number = simpleJdbcInsert.withTableName("task1_jdbc_template")
                .usingColumns("title", "priority")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(paramSource);
        System.out.println("id: " + number);
    }

    public void saveSimpleJDBCInsertReturnType(Todo todo) {  // bu task2 ni topshirig'i
        var paramSource = new BeanPropertySqlParameterSource(todo);
        KeyHolder keyHolder = simpleJdbcInsert.withTableName("task1_jdbc_template")
                .usingColumns("title", "priority")
                .usingGeneratedKeyColumns("id", "created_at")
                .executeAndReturnKeyHolder(paramSource);
        Map<String, Object> keys = keyHolder.getKeys();
        keys.forEach((key, value) -> {
            System.out.println(key + " : " + value);
        });
    }

    public Todo findById(Integer id) {
        var sql = "select * from task1_jdbc_template where id = ?";
        Todo todo = jdbcTemplate.queryForObject(sql, new TodoRowMapper(), id);
        return todo;
    }

    public Todo findByTitle(String title) {
        var sql = "select * from task1_jdbc_template where title = ?";
        BeanPropertyRowMapper<Todo> todoBeanPropertyRowMapper = BeanPropertyRowMapper.newInstance(Todo.class);
        return jdbcTemplate.queryForObject(sql, todoBeanPropertyRowMapper, title);
    }

    public List<Todo> findAll() {
        var sql = "select * from task1_jdbc_template";
        return jdbcTemplate.query(sql, new TodoRowMapper());
    }

    public void update(Todo todo) {
        var sql = "update task1_jdbc_template set title = ?, priority = ? where id = ?";
        int update = jdbcTemplate.update(sql, todo.getTitle(), todo.getPriority(), todo.getId());
        if (update != 0) {
            System.out.println("Todo updated with id: " + todo.getId());
        } else {
            System.out.println("Todo not found with id: " + todo.getId());
        }
    }

    public void delete(Integer id) {
        var sql = "delete from task1_jdbc_template where id = ?";
        int delete = jdbcTemplate.update(sql, id);
        if (delete != 0) {
            System.out.println("Todo deleted with id: " + id);
        } else {
            System.out.println("Todo not found with id: " + id);
        }
    }
}
