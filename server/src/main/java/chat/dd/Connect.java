package chat.dd;

import chat.engine.Server;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Connect implements Runnable {

    private final static Logger LOGGER = Logger.getLogger(Server.class);

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
        LOGGER.info("Client connected");

        while (true) {
            try {
                String message = reader.readLine();
                LOGGER.info(message);

                writer.write(message + "\r\n");
                writer.flush();
            } catch (IOException e) {
                LOGGER.error("Failed to read message");
                try {
                    socket.close();
                    reader.close();
                    writer.close();
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
}
