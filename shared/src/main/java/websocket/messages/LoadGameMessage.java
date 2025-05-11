package websocket.messages;

import chess.ChessMove;

import java.util.List;

public class LoadGameMessage extends ServerMessage {
    private final List<ChessMove> moves;

    public LoadGameMessage(List<ChessMove> moves) {
        super(ServerMessageType.LOAD_GAME);
        this.moves = moves;
    }

    public List<ChessMove> getMoves() {
        return moves;
    }
}