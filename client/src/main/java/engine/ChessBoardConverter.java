package engine;

import chess.ChessGame;
import chess.ChessPiece;

public class ChessBoardConverter {

    public static String[][] toStringBoard(ChessPiece[][] chessBoard) {
        String[][] stringBoard = new String[8][8];
        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
                ChessPiece piece = chessBoard[col][row];
                int flippedRow = 7 - row;
                if (piece == null) {
                    stringBoard[flippedRow][col] = "   ";
                } else {
                    String color = (piece.getTeamColor() == ChessGame.TeamColor.WHITE)
                            ? EscapeSequences.SET_TEXT_COLOR_RED
                            : EscapeSequences.SET_TEXT_COLOR_BLUE;
                    String symbol = getCharacter(piece.getPieceType());
                    stringBoard[flippedRow][col] = color + " " + symbol + " ";
                }
            }
        }
        return stringBoard;
    }

    private static String getCharacter(ChessPiece.PieceType type) {
        return switch (type) {
            case KING -> "K";
            case QUEEN -> "Q";
            case BISHOP -> "B";
            case KNIGHT -> "N";
            case ROOK -> "R";
            case PAWN -> "P";
        };
    }
}
