package chat;

import chat.engine.Server;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        Server server = new Server();

        server.run();
    }
}
