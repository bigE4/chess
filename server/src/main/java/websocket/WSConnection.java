package websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.ServerMessage;

public record WSConnection(int gameID, String username, Session session) {

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
}