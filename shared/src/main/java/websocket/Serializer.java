package websocket;

import com.google.gson.Gson;
import websocket.commands.UserGameCommand;
import websocket.messages.ServerMessage;

public class Serializer {
    static Gson gson = new Gson();

    public static String CommandSerializer(UserGameCommand command) {
        return gson.toJson(command);
    }

    public static UserGameCommand CommandDeserializer(String command) {
        return gson.fromJson(command, UserGameCommand.class);
    }

    public static String MessageSerializer(ServerMessage message) {
        return gson.toJson(message);
    }

    public static ServerMessage MessageDeserializer(String message) {
        return gson.fromJson(message, ServerMessage.class);
    }
}
