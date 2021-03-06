package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://localhost:3306/test";
    private static final String USR = "adm";
    private static final String PASS = "1234";
    private Util() {
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USR, PASS);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}