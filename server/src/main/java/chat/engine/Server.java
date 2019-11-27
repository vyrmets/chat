package chat.engine;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final Logger LOGGER = Logger.getLogger(Server.class);

    private static final int PORT = 8080;

    public void run() throws IOException {
        LOGGER.info("Server started at port: " + PORT);

        while (true) {
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                Socket socket = serverSocket.accept();
                new Thread(new Connect(socket)).start();
            } catch (IOException e) {
                LOGGER.error("Failed to start server: ", e);
            }
            if (false) {
                break;
            }
        }
    }

}