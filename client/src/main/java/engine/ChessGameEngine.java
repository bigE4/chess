package engine;

import chess.*;

import java.util.Collection;
import java.util.List;

public class ChessGameEngine {
    private static ChessGame game;
    private static ChessGame.TeamColor playerColor;

    public ChessGameEngine(ChessGame.TeamColor teamColor) {
        playerColor = teamColor;
    }

    public void loadFromMoves(List<ChessMove> moves) throws InvalidMoveException{
        game = new ChessGame();
        game.setBoard(new ChessBoard());
        for (ChessMove move : moves) {
            game.makeMove(move);
        }
    }

    public boolean isValidMove(ChessMove move) {
        ChessPosition start = move.getStartPosition();
        Collection<ChessMove> validMoves = game.validMoves(start);
        return validMoves != null && validMoves.contains(move);
    }

    public void applyMove(ChessMove move) throws InvalidMoveException {
        game.makeMove(move);
    }

    public static void render() {
        ChessBoard board = game.getBoard();
        String[][] stringBoard = ChessBoardConverter.toStringBoard(board.getChessBoard());
        if (playerColor == ChessGame.TeamColor.WHITE) {
            whitePerspective(stringBoard);
        } else if (playerColor == ChessGame.TeamColor.BLACK) {
            blackPerspective(stringBoard);
        }
    }

    private static void whitePerspective(String[][] initialBoard) {
        System.out.println("   a  b  c  d  e  f  g  h");
        for (int row = 8; row >= 1; row--) {
            System.out.print(row + " ");
            for (int col = 1; col <= 8; col++) {
                printHelper(initialBoard, row, col);
            }
            System.out.println(" " + (row));
        }
        System.out.println("   a  b  c  d  e  f  g  h");
    }

    private static void blackPerspective(String[][] initialBoard) {
        System.out.println("   h  g  f  e  d  c  b  a");
        for (int row = 1; row <= 8; row++) {
            System.out.print(row + " ");
            for (int col = 8; col >= 1; col--) {
                printHelper(initialBoard, row, col);
            }
            System.out.println(" " + row);
        }
        System.out.println("   h  g  f  e  d  c  b  a");
    }

    private static void printHelper(String[][] initialBoard, int row, int col) {
        boolean isWhite = (row + col) % 2 == 1;
        String bgColor = isWhite ? EscapeSequences.SET_BG_COLOR_WHITE : EscapeSequences.SET_BG_COLOR_BLACK;
        System.out.print(bgColor + initialBoard[8 - row][col - 1] + EscapeSequences.RESET_BG_COLOR + EscapeSequences.RESET_TEXT_COLOR);
    }

}
