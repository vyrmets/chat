package chat.database;

import chat.model.User;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UsersRepository {

    private static final Logger LOGGER = Logger.getLogger(UsersRepository.class);
    private static final String SQL_INSERT = "INSERT INTO user (name, password) VALUES (?,?)";
    private static final String SQL_SHOW = "SELECT * FROM user";

    public void saveUser(User user) {
        try (Connection connection = connectionBase(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.info("Failed to save user in Database ", e);
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Connection connection = connectionBase(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_SHOW); ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                User user = new User();
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
                list.add(user);
            }
        } catch (SQLException e) {
            LOGGER.info("Failed to get user in Database ", e);
        }
        return list;
    }

    private Connection connectionBase() throws SQLException {
        Properties properties = new Properties();
        String URL = "";
        String NAME_BASE = "";
        String PASS_BASE = "";
        try (FileInputStream fileInputStream = new FileInputStream("db.properties")) {
            properties.load(fileInputStream);
            URL = properties.getProperty("url");
            NAME_BASE = properties.getProperty("user");
            PASS_BASE = properties.getProperty("pass");
        } catch (IOException e) {
            LOGGER.info("Faild to take url and password", e);
        }
        LOGGER.info("Registering JDBC driver...");
        Connection connectionDB = DriverManager.getConnection(URL, NAME_BASE, PASS_BASE);
        LOGGER.info("Database connected!");

        return connectionDB;
    }
}
