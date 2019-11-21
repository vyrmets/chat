package chat.engine;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

public class Server {

    private final Logger logger = Logger.getLogger(Server.class);

    public void run() throws IOException {
        logger.info("Server starting...");

        try (ServerSocket serverSocket = new ServerSocket(8000)) {

            while (true) {
                Socket socket = serverSocket.accept();
                OutputStream outputStream = socket.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, Charset.forName("UTF-8"));
                BufferedWriter writer = new BufferedWriter(outputStreamWriter);

                InputStream inputStream = socket.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        logger.info("Client connected");
                        String a = null;
                        try {
                            while (true) {
                                a = reader.readLine();
                                logger.info(a);

                                writer.write(a + "\r\n");

                                writer.flush();
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                socket.close();
                                serverSocket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        }
    }
}