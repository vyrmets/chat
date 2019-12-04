package chat.engine;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Connect implements Runnable {

    private static final String EXIT = "exit";

    private static final Logger LOGGER = Logger.getLogger(Connect.class);

    private String name = "";

    private Socket socket;

    private PrintWriter writer;

    private BufferedReader reader;

    private AuthorizationService authorizationService;

    private ClientsOnline clientsOnline = ClientsOnline.getInstance();

    public Connect(Socket socket, AuthorizationService authorizationService) throws IOException {
        this.socket = socket;
        this.authorizationService = authorizationService;
        writer = getWriter(socket);
        reader = getReader(socket);
    }

    @Override
    public void run() {
        try {

            User user = authorizationService.authorization(writer, reader);

            if (clientsOnline.clientsOn.containsKey(user.getName())) {
                LOGGER.info("This account is alredy exist");
                writer.println("This account is alredy exist");
                closeConnection();
                return;
            }

            name = user.getName();

            clientsOnline.clientsOn.put(name, socket);

            LOGGER.info(clientsOnline.clientsOn.size());

            LOGGER.info("Client: " + name + " connected");
        } catch (IOException e) {
            LOGGER.info("Failed to proceed message: ", e);
        }
        while (true) {
            try {
                String message = reader.readLine();

                for (Map.Entry<String, Socket> entry : clientsOnline.clientsOn.entrySet()) {
                    writer = getWriter(entry.getValue());
                    writer.println(message);
                }

                if (disconnectFromServer(message)) {
                    break;
                } else {
                    LOGGER.info(name + ": " + message);
                }

            } catch (IOException e) {
                LOGGER.error("Failed to read message");
                try {
                    closeConnection();
                } catch (IOException ex) {
                    LOGGER.info("Failed to proceed message: ", e);
                }
            }
        }
    }

    private BufferedReader getReader(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        return new BufferedReader(inputStreamReader);
    }

    private PrintWriter getWriter(Socket socket) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
        return new PrintWriter(outputStreamWriter, true);
    }

    private boolean disconnectFromServer(String message) throws IOException {
        if (message == null || message.equalsIgnoreCase(EXIT)) {
            LOGGER.info("Client: " + name + " disconnect");
            closeConnection();
            return true;
        }
        return false;
    }

    private void closeConnection() throws IOException {
        socket.close();
        reader.close();
        writer.close();
        clientsOnline.removeClient(name);

    }
}
