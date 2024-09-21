package uz.pdp.task3.named_parameter_jdbc_template;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import uz.pdp.task1.jdbc_template.Todo;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext container = new ClassPathXmlApplicationContext("ioc-settings.xml");
        TodoDAO todoDAO = container.getBean(TodoDAO.class);
        Todo todo = Todo.builder()
                .title("September")
                .priority("MEDIUM")
                .build();
//        todoDAO.save(todo);
//        System.out.println(todoDAO.findById(16));
//        System.out.println(todoDAO.findByTitle("May"));
//        todoDAO.update(todo);
//        todoDAO.delete(88);
        System.out.println("id: " + todoDAO.saveReturnId(todo));
    }
}
