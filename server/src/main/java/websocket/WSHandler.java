package websocket;

import dataaccess.dao.AuthSQLDAO;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import websocket.commands.UserGameCommand;
import websocket.messages.ServerMessage;

@WebSocket
public class WSHandler {
    private final AuthSQLDAO aDAO = new AuthSQLDAO();
    private final ServerMessage loadGame = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
    private final ServerMessage error = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
    private final ServerMessage notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
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

    private void connect(WSConnectionManager connectionManager, ConnData data) {
        connectionManager.add(data.gameID, data.username, data.session);
        connectionManager.broadcastToUser(data.username, loadGame);
        connectionManager.broadcastToGame(data.gameID, notification);
    }

    private void move(WSConnectionManager connectionManager, ConnData data) {
        connectionManager.broadcastToGame(data.gameID, loadGame);
    }

    private void resign(WSConnectionManager connectionManager, ConnData data) {
        connectionManager.remove(data.username());
        connectionManager.broadcastToGame(data.gameID, notification);
    }

    private void leave(WSConnectionManager connectionManager, ConnData data) {
        connectionManager.remove(data.username());
        connectionManager.broadcastToGame(data.gameID, notification);
    }

    public static record ConnData(int gameID, String username, Session session) {}

}
