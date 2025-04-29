package websocket;

import dataaccess.dao.AuthSQLDAO;
import dataaccess.interfaces.AuthDAO;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import websocket.commands.UserGameCommand;
import websocket.messages.ServerMessage;

@WebSocket
public class WSHandler {
    private WSConnectionManager connectionManager = new WSConnectionManager();

    public WSHandler(WSConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) {
        try {
            // deserialize the string from client into a UserGameCommand
            UserGameCommand command = Serializer.CommandDeserializer(message);
            // Authenticate authToken, retrieve username via AuthData retrieval
            AuthDAO aDAO = new AuthSQLDAO();
            String authToken = command.getAuthToken();
            if (!aDAO.authenticateAuth(authToken)) {throw new Exception("Unauthorized access");}
            String username = aDAO.retrieveAuth(command.getAuthToken()).username();
            // initialize ConnData
            ConnData data = new ConnData(command.getGameID(), username, session);
            // CommandType switch block
            switch (command.getCommandType()) {
                case CONNECT -> connect(connectionManager, data);
                case MAKE_MOVE -> move(connectionManager, data);
                case RESIGN -> resign(connectionManager, data);
                case LEAVE -> leave(connectionManager, data);
            }
        } catch (Exception e) {
            System.out.println("Error. WSHandler.onMessage broke something.");
        }
    }

    private void connect(WSConnectionManager connectionManager, ConnData connData) {
        connectionManager.add(connData.gameID, connData.username(), connData.session());
        connectionManager.broadcast(new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION), connData.gameID);
        connectionManager.broadcast(new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME), connData.gameID);
    }

    private void move(WSConnectionManager connectionManager, ConnData connData) {

    }

    private void resign(WSConnectionManager connectionManager, ConnData data) {

    }

    private void leave(WSConnectionManager connectionManager, ConnData connData) {
        
    }

    public static record ConnData(int gameID, String username, Session session) {}

}
