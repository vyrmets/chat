package chat.engine;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Connect implements Runnable {

    private final static Logger LOGGER = Logger.getLogger(Server.class);

    private static final String EXIT = "exit";

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
            LOGGER.info("Client: " + name + " connected");

        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {

                String message = reader.readLine();

                if(message == null) {
                    LOGGER.info("Client: " + name + " disconnect");
                    closeConnection();
                    break;
                }

                LOGGER.info(name + ": " + message);

                if (disconnectFromServer(message)) {
                    break;
                }
            } catch (IOException e) {
                LOGGER.error("Failed to read message");
                try {
                    closeConnection();
                } catch (IOException ex) {
                    ex.printStackTrace();
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

        if (message.equalsIgnoreCase(EXIT)) {
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
    }
}
