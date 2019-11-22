package chat.engine;

import chat.services.AuthorizationService;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final Logger LOGGER = Logger.getLogger(Server.class);

    private static final int PORT = 8080;

    public void run() {
        LOGGER.info("Server started at port: " + PORT);
        AuthorizationService authorizationService = new AuthorizationService();

        while (true) {
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                Socket socket = serverSocket.accept();
                Connect connect = new Connect(socket, authorizationService);
                new Thread(connect).start();
            } catch (IOException e) {
                LOGGER.error("Failed to start server: ", e);
            }
            if (false) {
                break;
            }
        }
    }
}
