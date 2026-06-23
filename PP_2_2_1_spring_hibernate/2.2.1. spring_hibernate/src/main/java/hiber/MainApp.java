package hiber;

import hiber.config.AppConfig;
import hiber.dao.UserDao;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import hiber.service.UserServiceImp;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);
        Car car1 = new Car("Volvo", 2013);
        Car car2 = new Car("Audi", 2018);
        Car car3 = new Car("BYD", 2025);
        Car car4 = new Car("UAZ", 1980);
        Car car5 = new Car("MAN", 2006);
        User user1 = new User("Sergey", "Belyakov", "SBelyakov@mail.ru");
        User user2 = new User("Stas", "Baretskiy", "Barret@mail.ru");
        User user3 = new User("Ivan", "Ivanov", "II@mail.ru");
        User user4 = new User("Dmitriy", "Donskoy", "DM@mail.ru");
        User user5 = new User("Anna", "NaaN", "AN@mail.ru");
        user1.setCar(car1);
        user2.setCar(car2);
        user3.setCar(car3);
        user4.setCar(car4);
        user5.setCar(car5);
        userService.add(user1);
        userService.add(user2);
        userService.add(user3);
        userService.add(user4);
        userService.add(user5);
        UserDao userDao = context.getBean(UserDao.class);
        List<User> users = userService.listUsers();

        for (User user : users) {

                System.out.println("Id = " + user.getId());
                System.out.println("First Name = " + user.getFirstName());
                System.out.println("Last Name = " + user.getLastName());
                System.out.println("Email = " + user.getEmail());
                System.out.println("Car = " + user.getCar().getModel());
                System.out.println();
                userDao.getOwner(user.getCar().getModel(), user.getCar().getSeries());

       }
    }
}
