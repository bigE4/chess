package client;

import websocket.messages.ServerMessage;

public interface NotificationHandler {
     void notify(ServerMessage notification);
}
