package dataaccess;

import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class DBUtils {
    private static final Gson gson = new Gson();

    public static String serializeGame(ChessGame game) {
        return gson.toJson(game);
    }

    public static ChessGame deserializeGame(String game) {
        return gson.fromJson(game, ChessGame.class);
    }

    public static String serializeMoves(List<ChessMove> moves) {
        return gson.toJson(moves);
    }

    public static List<ChessMove> deserializeMoves(String moves) {
        return gson.fromJson(moves, new TypeToken<List<ChessMove>>(){}.getType());
    }
}
