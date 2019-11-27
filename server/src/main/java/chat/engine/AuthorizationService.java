package chat.engine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class AuthorizationService {

    private ArrayList<User> clientBase;
    private User user = new User();

    public void registration(BufferedWriter writer, BufferedReader reader) throws IOException {
        writer.write("Welcome to chat, please login or register");
        writer.flush();
        writer.newLine();
        setClientBase(writer, reader);
    }

    private void setClientBase(BufferedWriter writer, BufferedReader reader) throws IOException {
        writer.write("Enter the number '1' if you want to loggin or '2' if you want to registration: ");
        writer.flush();

        while (true) {

            String choice = reader.readLine();

            if (choice.equals("1")) {
                writer.write("Please enter your name: ");
                writer.flush();
                user.setName(reader.readLine());
                writer.write("Please enter your password: ");
                writer.flush();
                user.setPassword(reader.readLine());
                break;
            } else if (choice.equals("2")) {
                writer.write("Please enter your name: ");
                writer.flush();
                user.setName(reader.readLine());
                writer.write("Please enter your password: ");
                writer.flush();
                user.setPassword(reader.readLine());
                writer.write("Please confirm your password: ");
                writer.flush();
                String confirm =reader.readLine();
                if(user.getPassword().equals(confirm)){
                    break;
                }else{
                    writer.write("Registration failed, please try again");
                    writer.flush();
                    writer.newLine();
                    writer.write("Enter the number '1' if you want to loggin or '2' if you want to registration: ");
                    writer.flush();
                }
            }else {
                writer.write("Please enter the number 1 or 2: ");
                writer.flush();
            }
        }

    }

}
