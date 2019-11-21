package chat.server;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

public class Server {

    public void run() {

        try (ServerSocket serverSocket = new ServerSocket(8000)) {
            ;

            msg("Server is working!");

            while (true) {

                try (
                        Socket socket = serverSocket.accept();
                        OutputStream outputStream = socket.getOutputStream();
                        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, Charset.forName("UTF-8"));
                        BufferedWriter writer = new BufferedWriter(outputStreamWriter);

                        InputStream inputStream = socket.getInputStream();
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                        BufferedReader reader = new BufferedReader(inputStreamReader);
                ) {

                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            msg("Client connected");
                            String a = null;
                            try {
                                while (true) {
                                    a = reader.readLine();
                                    msg(a);

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
                    while (true) {
                        thread.start();
                    }

               /* msg("Client connected");
                String a = reader.readLine();
                msg(a);

                writer.write(a + "\r\n");

                writer.flush(); */

                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void msg(String s) {
        System.out.println(s);
    }
}