package uz.pdp.task4.simple_jdbc_call;

import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.JdbcTemplate;
import uz.pdp.task1.jdbc_template.Todo;

import javax.sql.DataSource;

public class TodoDao2 {
    private final SimpleJdbcCall simpleJdbcCall;

    public TodoDao2(DataSource dataSource, SimpleJdbcCall simpleJdbcCall) {
        SimpleJdbcCall simpleJdbcCall1;
        simpleJdbcCall1 = simpleJdbcCall;
        simpleJdbcCall1 = new SimpleJdbcCall(new JdbcTemplate(dataSource))
                .withProcedureName("your_stored_procedure_name");
        this.simpleJdbcCall = simpleJdbcCall1;
    }

    public void save(Todo todo) {
        simpleJdbcCall.execute(todo.getTitle(), todo.getPriority());
    }

//    public void update(Todo todo) {
//        simpleJdbcCall.execute(todo.getId(), todo.getName(), user.getEmail());
//    }
//
//    public void delete(int userId) {
//        jdbcCall.execute(userId);
//    }
//
//    public List<User> getAll() {
//        Map<String, Object> result = jdbcCall.execute();
//    }
}
