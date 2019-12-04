package chat.engine;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ClientsOnline {
    private static ClientsOnline instance;

    public Map<String, Socket> clientsOn;

    private ClientsOnline(Map<String, Socket> clientsOnline) {
        this.clientsOn = clientsOnline;
    }

    public static synchronized ClientsOnline getInstance() {

        if (instance == null) {
            instance = new ClientsOnline(new HashMap<>());
        }
        return instance;
    }

    public void removeClient(String name) {
        clientsOn.remove(name);
    }

    @Override
    public String toString() {
        return "ClientsOnline{" +
                "clientsOn=" + clientsOn +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientsOnline that = (ClientsOnline) o;
        return Objects.equals(clientsOn, that.clientsOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientsOn);
    }
}
