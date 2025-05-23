package websocket;

import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.ServerMessage;

import java.util.concurrent.ConcurrentHashMap;

public class WSConnectionManager {
    public final ConcurrentHashMap<String, WSConnection> connections = new ConcurrentHashMap<>();

    public void add(int gameID, String username, Session session) {
        WSConnection connection = new WSConnection(gameID, username, session);
        connections.put(username, connection);
    }

    public void remove(String username) {
        connections.remove(username);
    }

    public void broadcastToGame(int gameID, ServerMessage message) {
        for (WSConnection connection : connections.values()) {
            if (connection.isOpen() && connection.gameID() == gameID) {
                connection.send(message);
            } else if (!connection.isOpen()) {
                connections.remove(connection.username());
            }
        }
    }

    public void broadcastToUser(String username, ServerMessage message) {
        WSConnection connection = connections.get(username);
        if (connection.isOpen()) {
            connection.send(message);
        } else if (!connection.isOpen()) {
            connections.remove(connection.username());
        }
    }
}
