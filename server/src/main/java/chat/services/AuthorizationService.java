package chat.services;

import chat.database.UsersBase;
import chat.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

public class AuthorizationService {
    private static final String ENTER_NUMBER = "Enter the number '1' if you want to loggin or '2' if you want to registration: ";

    private ArrayList<User> clientBase = new ArrayList<>();

    private UsersBase usersBase;

    public User authorization(PrintWriter writer, BufferedReader reader) throws IOException, SQLException, ClassNotFoundException {

        usersBase = new UsersBase();

        clientBase.addAll(usersBase.dataBase());

        for (User u : clientBase) {
            System.out.println(u);
        }

        writer.println("Welcome to chat, please login or register");
        writer.println(ENTER_NUMBER);

        while (true) {
            String choice = reader.readLine();

            switch (choice) {
                case "1":
                    User loggedInUser = loggin(writer, reader);

                    if (loggedInUser != null) {
                        return loggedInUser;
                    } else {
                        writer.println(ENTER_NUMBER);
                    }
                    break;
                case "2":
                    User registeredUser = registration(writer, reader);
                    if (registeredUser != null) {
                        return registeredUser;
                    } else {
                        writer.println(ENTER_NUMBER);
                    }
                    break;
                default:
                    writer.println(ENTER_NUMBER);
                    break;
            }
        }
    }

    private User loggin(PrintWriter writer, BufferedReader reader) throws IOException {
        User user = new User();
        writer.println("Please enter your name: ");
        user.setName(reader.readLine());
        writer.println("Please enter your password: ");
        user.setPassword(reader.readLine());
        if (clientBase.contains(user)) {
            writer.println("Login success, please enter your first message: ");
            return user;
        } else {
            writer.println("Login failed, please try again");
        }
        return null;
    }

    private User registration(PrintWriter writer, BufferedReader reader) throws IOException {
        User user = new User();
        writer.println("Please enter your name: ");
        user.setName(reader.readLine());
        for (User client : clientBase) {
            if (client.getName().equals(user.getName())) {
                writer.println("This name is alredy exist, please try again");
                return null;
            }
        }
        writer.println("Please enter your password: ");
        user.setPassword(reader.readLine());
        writer.println("Please confirm your password: ");
        String confirm = reader.readLine();
        if (user.getPassword().equals(confirm)) {
            writer.println("Registration success, please enter your first message: ");
            clientBase.add(user);
            try {
                usersBase = new UsersBase();
                usersBase.addToDataBase(user.getName(),user.getPassword());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return user;
        } else {
            writer.println("Registration failed, please try again");
        }
        return null;
    }
}
