package chess;

import java.util.Arrays;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    ChessPiece[][] chessBoard = new ChessPiece[8][8];

    public ChessBoard() {

    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        int x = position.getColumn() - 1;
        int y = position.getRow() - 1;
        chessBoard[x][y] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        int x = position.getColumn() - 1;
        int y = position.getRow() - 1;
        return chessBoard[x][y];
    }

    /**
     * |r|n|b|q|k|b|n|r|
     * |p|p|p|p|p|p|p|p|
     * | | | | | | | | |
     * | | | | | | | | |
     * | | | | | | | | |
     * | | | | | | | | |
     * |P|P|P|P|P|P|P|P|
     * |R|N|B|Q|K|B|N|R|
     * Sets the board to the default starting board (above)
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        chessBoard = new ChessPiece[8][8];

        ChessPiece.PieceType[] order = {
                ChessPiece.PieceType.ROOK,
                ChessPiece.PieceType.KNIGHT,
                ChessPiece.PieceType.BISHOP,
                ChessPiece.PieceType.QUEEN,
                ChessPiece.PieceType.KING,
                ChessPiece.PieceType.BISHOP,
                ChessPiece.PieceType.KNIGHT,
                ChessPiece.PieceType.ROOK
        };

        for (int i = 1; i <= 8; i++) {
            addPiece(new ChessPosition(1, i), new ChessPiece(ChessGame.TeamColor.WHITE, order[i - 1]));
            addPiece(new ChessPosition(2, i), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN));
            addPiece(new ChessPosition(7, i), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN));
            addPiece(new ChessPosition(8, i), new ChessPiece(ChessGame.TeamColor.BLACK, order[i - 1]));
        }
    }

    public ChessPiece[][] getChessBoard() {
        return chessBoard;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(chessBoard, that.chessBoard);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(chessBoard);
    }

    @Override
    public String toString() {
        StringBuilder rString = new StringBuilder();
        for (int i = 8; i >= 1; i--) {
            rString.append("|");
            for (int j = 1; j <= 8 ; j++) {
                rString.append(getPiece(new ChessPosition(i, j)));
                rString.append("|");
            }
            rString.append("\n");
        }
        return rString.toString();
    }
}