package chat.engine;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Connect implements Runnable {

    private static final String CLIENT = "Client: ";

    private static final String EXIT = "exit";

    private static final Logger LOGGER = Logger.getLogger(Connect.class);

    private String name = "";

    private Socket socket;

    private BufferedWriter writer;

    private BufferedReader reader;

    public Connect(Socket socket) throws IOException {
        this.socket = socket;
        writer = getWriter(socket);
        reader = getReader(socket);
    }

    @Override
    public void run() {

        try {
            writer.write("Please enter your name: ");
            writer.flush();
            name = reader.readLine();
            LOGGER.info(CLIENT + name + " connected");

        } catch (IOException e) {
            LOGGER.info("context", e);
        }

        while (true) {
            try {

                String message = reader.readLine();

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
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private BufferedReader getReader(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        return new BufferedReader(inputStreamReader);
    }

    private BufferedWriter getWriter(Socket socket) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
        return new BufferedWriter(outputStreamWriter);
    }

    private boolean disconnectFromServer(String message) throws IOException {

        if (message == null || message.equalsIgnoreCase(EXIT)) {
            LOGGER.info(CLIENT + name + " disconnect");
            closeConnection();
            return true;
        }
        return false;
    }

    private void closeConnection() throws IOException {
        socket.close();
        reader.close();
        writer.close();
    }
}
