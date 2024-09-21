package uz.pdp.task4.simple_jdbc_call;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import uz.pdp.task1.jdbc_template.Todo;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext container = new ClassPathXmlApplicationContext("ioc-settings.xml");
        TodoDao2 todoDao2 = container.getBean(TodoDao2.class);
        Todo todo = Todo.builder()
                .title("February")
                .priority("EASY")
                .build();
        todoDao2.save(todo);
    }
}
