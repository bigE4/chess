package chess;

import java.util.Collection;

public class ChessEnPassant {
    public static void enPassantLogic(ChessPosition start, Collection<ChessMove> validMoves) {
        if (canEnPassant()) {
            ChessMove enPassant = enPassantMoveCreator(start);
            validMoves.add(enPassant);
        }
    }

    public static boolean canEnPassant() {
        return false;
    }

    public static ChessMove enPassantMoveCreator(ChessPosition start) {
        return null;
    }
}
