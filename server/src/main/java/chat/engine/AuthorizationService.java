package chat.engine;

import org.apache.log4j.Logger;

import javax.jws.soap.SOAPBinding;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class AuthorizationService {

   private ArrayList<User> clientBase;
   private String choice;
   private User user = new User();

    public void registration(Logger logger, BufferedWriter writer, BufferedReader reader) throws IOException {
        writer.write("Welcome to chat, please login or register");
        writer.flush();
        writer.newLine();
        setClientBase(logger, writer,reader);
    }

    private void setClientBase(Logger logger ,BufferedWriter writer, BufferedReader reader) throws IOException {

        writer.write("Enter the number '1' if you want to loggin or '2' if you want to registration: ");
        writer.flush();

        while (true) {

            choice = reader.readLine();

            switch (choice){
                case "1":
                    writer.write("Please enter your name: ");
                    writer.flush();
                    user.setName(reader.readLine());
                    writer.write("Please enter your password: ");
                    writer.flush();
                    user.setPassword(reader.readLine());
                    break;
                case "2":
                    logger.info("OOKKK");
                    break;
            }
            break;
        }

    }




}
