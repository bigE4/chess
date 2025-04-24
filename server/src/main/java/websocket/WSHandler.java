package websocket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import websocket.commands.UserGameCommand;

@WebSocket
public class WSHandler {
    private WSConnectionManager connectionManager = new WSConnectionManager();

    public WSHandler(WSConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) {
        try {
            UserGameCommand command = Serializer.CommandDeserializer(message);

            switch (command.getCommandType()) {
                case CONNECT -> connect(connectionManager);
                case MAKE_MOVE -> move(connectionManager);
                case RESIGN -> resign(connectionManager);
                case LEAVE -> leave(connectionManager);
            }

        } catch (Exception e) {
            System.out.println("Error. WSHandler broke something.");
        }
    }

    private void connect(WSConnectionManager connectionManager) {

    }

    private void move(WSConnectionManager connectionManager) {

    }

    private void resign(WSConnectionManager connectionManager) {

    }

    private void leave(WSConnectionManager connectionManager) {
        
    }
}
