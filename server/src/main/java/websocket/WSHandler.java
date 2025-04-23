package websocket;

import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class WSHandler {
    private final WSConnectionManager connections = new WSConnectionManager();

    @OnWebSocketMessage
    public void onMessage() {

    }
}
