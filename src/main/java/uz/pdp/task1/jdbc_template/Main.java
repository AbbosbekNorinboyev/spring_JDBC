package uz.pdp.task1.jdbc_template;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext container = new ClassPathXmlApplicationContext("ioc-settings.xml");
        TodoDao todoDao = container.getBean(TodoDao.class);
        Todo todo = Todo.builder()
                .title("April")
                .priority("EASY")
                .build();
//        todoDao.save(todo);
//        System.out.println(todoDao.save2(todo));
//        todoDao.saveSimpleJDBCInsertReturnId(todo);
        todoDao.saveSimpleJDBCInsertReturnType(todo);
//        System.out.println(todoDao.findById(1));
//        System.out.println(todoDao.findByTitle("January"));
//        todoDao.findAll().forEach(System.out::println);
//        todoDao.update(todo);
//        todoDao.delete(3);
    }
}