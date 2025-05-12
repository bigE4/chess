package engine;

import chess.*;
import client.REPL3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ChessGameEngine {
    public ChessGame game;
    private final ChessGame.TeamColor playerColor;

    public ChessGameEngine(ChessGame.TeamColor teamColor, REPL3 repl3) throws InvalidMoveException {
        playerColor = teamColor;
        loadFromMoves(repl3.getMoves());
    }

    public void loadFromMoves(List<ChessMove> moves) throws InvalidMoveException{
        game = new ChessGame();
        game.setBoard(new ChessBoard());
        if (!moves.isEmpty()) {
            for (ChessMove move : moves) {
                game.makeMove(move);
            }
        }
    }

    public boolean isValidMove(ChessMove move) {
        ChessPosition start = move.getStartPosition();
        Collection<ChessMove> validMoves = game.validMoves(start);
        return validMoves != null && validMoves.contains(move);
    }

    public void applyMove(ChessMove move) throws InvalidMoveException {
        game.makeMove(move);
        render();
    }

    public void render() {
        ChessBoard board = game.getBoard();
        String[][] stringBoard = ChessBoardConverter.toStringBoard(board.getChessBoard());
        if (playerColor == ChessGame.TeamColor.WHITE) {
            whitePerspective(stringBoard, null);
        } else if (playerColor == ChessGame.TeamColor.BLACK) {
            blackPerspective(stringBoard, null);
        }
    }

    public void legalRender(ChessPosition pos) {
        Collection<ChessMove> moves = game.validMoves(pos);
        List<ChessPosition> positions = new ArrayList<>();
        positions.add(pos);
        for (ChessMove move : moves) {
            positions.add(move.getEndPosition());
        }
        ChessBoard board = game.getBoard();
        String[][] stringBoard = ChessBoardConverter.toStringBoard(board.getChessBoard());
        if (playerColor == ChessGame.TeamColor.WHITE) {
            whitePerspective(stringBoard, positions);
        } else if (playerColor == ChessGame.TeamColor.BLACK) {
            blackPerspective(stringBoard, positions);
        }
    }

    private void whitePerspective(String[][] initialBoard, List<ChessPosition> positions) {
        System.out.println("   a  b  c  d  e  f  g  h");
        for (int row = 8; row >= 1; row--) {
            System.out.print(row + " ");
            for (int col = 1; col <= 8; col++) {
                printHelper(initialBoard, row, col, positions);
            }
            System.out.println(" " + (row));
        }
        System.out.println("   a  b  c  d  e  f  g  h");
    }

    private void blackPerspective(String[][] initialBoard, List<ChessPosition> positions) {
        System.out.println("   h  g  f  e  d  c  b  a");
        for (int row = 1; row <= 8; row++) {
            System.out.print(row + " ");
            for (int col = 8; col >= 1; col--) {
                printHelper(initialBoard, row, col, positions);
            }
            System.out.println(" " + row);
        }
        System.out.println("   h  g  f  e  d  c  b  a");
    }

    private void printHelper(String[][] initialBoard, int row, int col, List<ChessPosition> positions) {
        boolean isWhite = (row + col) % 2 == 1;
        String bgColor = isWhite ? EscapeSequences.SET_BG_COLOR_WHITE : EscapeSequences.SET_BG_COLOR_BLACK;
        if (positions != null && !positions.isEmpty()) {
            ChessPosition current = new ChessPosition(row, col);
            ChessPosition first = positions.getFirst();
            if (current.equals(first)) {
                bgColor = EscapeSequences.SET_BG_COLOR_YELLOW;
            } else if (positions.contains(current)) {
                bgColor = isWhite ? EscapeSequences.SET_BG_COLOR_GREEN : EscapeSequences.SET_BG_COLOR_DARK_GREEN;
            }
        }
        System.out.print(bgColor + initialBoard[8 - row][col - 1] + EscapeSequences.RESET_BG_COLOR + EscapeSequences.RESET_TEXT_COLOR);
    }
}
