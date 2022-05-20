package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        UserServiceImpl myTabInBd = new UserServiceImpl();

        List<User> myAllUser = new ArrayList<>();
        myAllUser.add(new User("Masha", "Petrova", (byte)23));
        myAllUser.add(new User("Vasya", "Petrov", (byte)30));
        myAllUser.add(new User("Petr", "Vasechkin", (byte)17));
        myAllUser.add(new User("Darya", "Subbotina", (byte)32));

        myTabInBd.createUsersTable();
        for(User user : myAllUser){
            myTabInBd.saveUser(user.getName(), user.getLastName(), user.getAge());
            System.out.printf("User с именем – %s добавлен в базу данных\n", user.getName());
        }

        System.out.println();

        myTabInBd.getAllUsers().forEach(System.out::println);;


        myTabInBd.createUsersTable();
        myTabInBd.dropUsersTable();
    }
}
