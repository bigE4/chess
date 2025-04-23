package websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.ServerMessage;

public class WSConnection {
    private final int gameID;
    private final String username;
    private final Session session;

    public WSConnection(int gameID, String username, Session session) {
        this.gameID = gameID;
        this.username = username;
        this.session = session;
    }

    public void send(ServerMessage message) {
        if (session.isOpen()) {
            try {
                String json = new Gson().toJson(message);
                session.getRemote().sendString(json);
            } catch (Exception e) {
                System.err.println("Error Sending Message To: " + username + ". Error: " + e.getMessage());
            }
        }
    }

    public boolean isOpen() {
        return session.isOpen();
    }

    public int getGameID() {
        return gameID;
    }

    public String getUsername() {
        return username;
    }

    public Session getSession() {
        return session;
    }
}