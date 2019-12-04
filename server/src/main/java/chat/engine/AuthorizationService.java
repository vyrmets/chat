package chat.engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class AuthorizationService {

    private ArrayList<User> clientBase = new ArrayList<>();

    public User authorization(PrintWriter writer, BufferedReader reader) throws IOException {
        writer.println("Welcome to chat, please login or register");
        return setClientBase(writer, reader);
    }

    private User setClientBase(PrintWriter writer, BufferedReader reader) throws IOException {
        writer.println("Enter the number '1' if you want to loggin or '2' if you want to registration: ");

        while (true) {
            String choice = reader.readLine();

            switch (choice) {
                case "1":
                    return loggin(writer, reader, new User());
                case "2":
                    return registration(writer, reader, new User());
                default:
                    writer.println("Enter the number '1' if you want to loggin or '2' if you want to registration: ");
            }
        }
    }

    private User loggin(PrintWriter writer, BufferedReader reader, User user) throws IOException {
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
        return setClientBase(writer, reader);
    }

    private User registration(PrintWriter writer, BufferedReader reader, User user) throws IOException {
        writer.println("Please enter your name: ");
        user.setName(reader.readLine());
        for (User client : clientBase) {
            if (client.getName().equals(user.getName())) {
                writer.println("This name is alredy exist, please try again");
                return registration(writer, reader, user);
            }
        }
        writer.println("Please enter your password: ");
        user.setPassword(reader.readLine());
        writer.println("Please confirm your password: ");
        String confirm = reader.readLine();
        if (user.getPassword().equals(confirm)) {
            writer.println("Registration success, please enter your first message: ");
            clientBase.add(user);
            return user;
        } else {
            writer.println("Registration failed, please try again");
        }
        return registration(writer, reader, user);
    }
}
