package uz.pdp.task3.named_parameter_jdbc_template;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import uz.pdp.task1.jdbc_template.Todo;
import uz.pdp.task1.jdbc_template.TodoRowMapper;

import java.util.List;

public class TodoDAO {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public TodoDAO(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void save(Todo todo) {
        var sql = "insert into task1_jdbc_template(title, priority) values(:title, :priority)";
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(todo);
        namedParameterJdbcTemplate.update(sql, parameterSource);
    }

    public Integer saveReturnId(Todo todo) {
        var sql = "insert into task1_jdbc_template(title, priority) values(:title, :priority)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("title", todo.getTitle())
                .addValue("priority", todo.getPriority());
        namedParameterJdbcTemplate.update(sql, parameterSource, keyHolder, new String[]{"id"});
        return (Integer) keyHolder.getKeys().get("id");
    }

    public Todo findById(Integer id) {
        var sql = "select * from task1_jdbc_template where id = :id";
        return namedParameterJdbcTemplate.queryForObject(
                sql,
                new MapSqlParameterSource("id", id),
                new TodoRowMapper());
    }

    public Todo findByTitle(String title) {
        var sql = "select * from task1_jdbc_template where title = :title";
        BeanPropertyRowMapper<Todo> todoBeanPropertyRowMapper = BeanPropertyRowMapper.newInstance(Todo.class);
        return namedParameterJdbcTemplate.queryForObject(
                sql,
                new MapSqlParameterSource("title", title),
                new TodoRowMapper()
        );
    }

    public List<Todo> findAllTodo() {
        var sql = "select * from todo";
        return namedParameterJdbcTemplate.query(sql, new TodoRowMapper());
    }

    public void update(Todo todo) {
        var sql = "update task1_jdbc_template set title = :title, priority = :priority where id = :id";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("title", todo.getTitle())
                .addValue("priority", todo.getPriority())
                .addValue("id", todo.getId());
        int update = namedParameterJdbcTemplate.update(sql, sqlParameterSource);
        if (update != 0) {
            System.out.println("Todo updated with id: " + todo.getId());
        } else {
            System.out.println("Todo not found with id: " + todo.getId());
        }
    }

    public void delete(Integer id) {
        var sql = "delete from task1_jdbc_template where id = :id";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", id);
        int delete = namedParameterJdbcTemplate.update(sql, sqlParameterSource);
        if (delete != 0) {
            System.out.println("Todo deleted with id: " + id);
        } else {
            System.out.println("Todo not found with id: " + id);
        }
    }
}
