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
        try {
            statement = conn.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS User (" +
                    "id BIGINT unsigned not null auto_increment  PRIMARY KEY," +
                    "name varchar(25) not null," +
                    "lastName varchar(25) not null," +
                    "age TINYINT not null )");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {

                } finally {
                    statement = null;
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {

                } finally {
                    conn = null;
                }
            }
        }
    }

    public void dropUsersTable() {
        conn = Util.getConnection();

        try {
            statement = conn.createStatement();
            statement.execute("drop table if exists User");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {

                } finally {
                    statement = null;
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {

                } finally {
                    conn = null;
                }
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        conn = Util.getConnection();

        try {
            pstatment = conn.prepareStatement("INSERT INTO user (name, lastName, age) VALUES (?,?,?)");
            pstatment.setString(1, name);
            pstatment.setString(2, lastName);
            pstatment.setByte(3, age);
            pstatment.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

            if (pstatment != null) {
                try {
                    pstatment.close();
                } catch (SQLException e) {

                } finally {
                    pstatment = null;
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {

                } finally {
                    conn = null;
                }
            }
        }
    }

    public void removeUserById(long id) {
        conn = Util.getConnection();

        try {
            pstatment = conn.prepareStatement("delete from user where id = ?");
            pstatment.setLong(1, id);
            pstatment.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

            if (pstatment != null) {
                try {
                    pstatment.close();
                } catch (SQLException e) {

                } finally {
                    pstatment = null;
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {

                } finally {
                    conn = null;
                }
            }
        }
    }

    public List<User> getAllUsers() {

        List<User> myUsers = new ArrayList<>();
        User tmpUsr;
       conn = Util.getConnection();

        try {
            statement = conn.createStatement();
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

            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {

                } finally {
                    resultSet = null;
                }
            }

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {

                } finally {
                    statement = null;
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {

                } finally {
                    conn = null;
                }
            }
        }
        return myUsers;
    }

    public void cleanUsersTable() {
        conn = Util.getConnection();

        try {
            statement = conn.createStatement();
            statement.execute("TRUNCATE TABLE user");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {

                } finally {
                    statement = null;
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {

                } finally {
                    conn = null;
                }
            }
        }
    }

}
