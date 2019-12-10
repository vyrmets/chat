package chat.store;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ClientsOnline {
    private static Map<String, Socket> clientsOn;

    public static synchronized Map<String, Socket> getInstance() {
        if (clientsOn == null) {
            clientsOn = new HashMap<>();
        }
        return clientsOn;
    }

    @Override
    public String toString() {
        return "ClientsOnline{" +
                "clientsOn=" + clientsOn +
                '}';
    }
}
