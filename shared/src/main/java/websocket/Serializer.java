package websocket;

import com.google.gson.Gson;
import websocket.commands.UserGameCommand;
import websocket.messages.ServerMessage;

public class Serializer {
    Gson gson = new Gson();

    public String CommandSerializer(UserGameCommand command) {
        return gson.toJson(command);
    }

    public UserGameCommand CommandDeserializer(String command) {
        return gson.fromJson(command, UserGameCommand.class);
    }

    public String MessageSerializer(ServerMessage message) {
        return gson.toJson(message);
    }

    public ServerMessage MessageDeserializer(String message) {
        return gson.fromJson(message, ServerMessage.class);
    }
}
