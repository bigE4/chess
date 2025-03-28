package engine;

import chess.ChessBoard;

public class ChessGameEngine {

    public static void printChessboard(String playerColor) {
        ChessBoard gameBoard = new ChessBoard();
        gameBoard.resetBoard();
        String[][] stringBoard = ChessBoardConverter.toStringBoard(gameBoard.getChessBoard());
        if (playerColor.equals("WHITE")) {
            whitePerspective(stringBoard);
        } else if (playerColor.equals("BLACK")) {
            blackPerspective(stringBoard);
        }
    }

    private static void whitePerspective(String[][] initialBoard) {
        System.out.println("   a  b  c  d  e  f  g  h");
        for (int row = 8; row >= 1; row--) {
            System.out.print(row + " ");
            for (int col = 1; col <= 8; col++) {
                boolean isWhite = (row + col) % 2 == 0;
                String bgColor = isWhite ? EscapeSequences.SET_BG_COLOR_WHITE : EscapeSequences.SET_BG_COLOR_BLACK;
                System.out.print(bgColor + initialBoard[8 - row][col - 1]
                    + EscapeSequences.RESET_BG_COLOR + EscapeSequences.RESET_TEXT_COLOR);
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
                boolean isWhite = (row + col) % 2 == 0;
                String bgColor = isWhite ? EscapeSequences.SET_BG_COLOR_WHITE : EscapeSequences.SET_BG_COLOR_BLACK;
                System.out.print(bgColor + initialBoard[8 - row][col - 1] + EscapeSequences.RESET_BG_COLOR + EscapeSequences.RESET_TEXT_COLOR);
            }
            System.out.println(" " + row);
        }
        System.out.println("   h  g  f  e  d  c  b  a");
    }

}
