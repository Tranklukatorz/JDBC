package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection conn;
    private Statement statement;
    private PreparedStatement pstatment;
    private ResultSet resultSet;


    public void createUsersTable() {

        conn = Util.getConnection();
        statement = Util.getStatement(conn);
        try {
            statement.execute("CREATE TABLE IF NOT EXISTS User (" +
                    "id BIGINT unsigned not null auto_increment  PRIMARY KEY," +
                    "name varchar(25) not null," +
                    "lastName varchar(25) not null," +
                    "age TINYINT not null )");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnections();
        }

    }

    public void dropUsersTable() {
        conn = Util.getConnection();
        statement = Util.getStatement(conn);

        try {
            statement.execute("drop table if exists User");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnections();
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        try {
            conn = Util.getConnection();
            pstatment = conn.prepareStatement("INSERT INTO user (name, lastName, age) VALUES (?,?,?)");
            pstatment.setString(1, name);
            pstatment.setString(2, lastName);
            pstatment.setByte(3, age);
            pstatment.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnections();
        }
    }

    public void removeUserById(long id) {
        try {
            conn = Util.getConnection();
            pstatment = conn.prepareStatement("delete from user where id = ?");
            pstatment.setLong(1, id);
            pstatment.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnections();
        }
    }

    public List<User> getAllUsers() {

        List<User> myUsers = new ArrayList<>();
        User tmpUsr;
        conn = Util.getConnection();
        statement = Util.getStatement(conn);

        try {

            resultSet = statement.executeQuery("select * from user");

            while (resultSet.next()) {
                tmpUsr = new User(resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age"));
                tmpUsr.setId(resultSet.getLong("id"));
                myUsers.add(tmpUsr);
            }
            System.out.println();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnections();
        }
        return myUsers;
    }

    public void cleanUsersTable() {
        conn = Util.getConnection();
        statement = Util.getStatement(conn);

        try {
            statement.execute("TRUNCATE TABLE user");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnections();
        }
    }

    private void closeConnections() {

        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {

                try {
                    if (pstatment != null) {
                        pstatment.close();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } finally {

                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }

        resultSet = null;
        pstatment = null;
        statement = null;
        conn = null;
    }

}
