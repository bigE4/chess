package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition start) {
        ArrayList<ChessMove> validMoves = new ArrayList<>();
        switch (type){
            case BISHOP -> validMoves.addAll(bishopHelper(board, start));
            case ROOK -> validMoves.addAll(rookHelper(board, start));
            case QUEEN -> validMoves.addAll(queenHelper(board, start));
            case KING -> validMoves.addAll(kingHelper(board, start));
            case KNIGHT -> validMoves.addAll(knightHelper(board, start));
            case PAWN -> validMoves.addAll(pawnHelper(board, start));
        }
        return validMoves;
    }

    public Collection<ChessMove> bishopHelper(ChessBoard board, ChessPosition start) {
        ArrayList<ChessMove> validMoves = new ArrayList<>();
        validMoves.addAll(movesCalculator(board, start, 7, 1, 1, true, false));
        validMoves.addAll(movesCalculator(board, start, 7, 1, -1, true, false));
        validMoves.addAll(movesCalculator(board, start, 7, -1, -1, true, false));
        validMoves.addAll(movesCalculator(board, start, 7, -1, 1, true, false));
        return validMoves;
    }

    public Collection<ChessMove> rookHelper(ChessBoard board, ChessPosition start) {
        ArrayList<ChessMove> validMoves = new ArrayList<>();
        validMoves.addAll(movesCalculator(board, start, 7, 1, 0, true, false));
        validMoves.addAll(movesCalculator(board, start, 7, 0, -1, true, false));
        validMoves.addAll(movesCalculator(board, start, 7, -1, 0, true, false));
        validMoves.addAll(movesCalculator(board, start, 7, 0, 1, true, false));
        return validMoves;
    }

    public Collection<ChessMove> queenHelper(ChessBoard board, ChessPosition start) {
        ArrayList<ChessMove> validMoves = new ArrayList<>();
        validMoves.addAll(bishopHelper(board, start));
        validMoves.addAll(rookHelper(board, start));
        return validMoves;
    }

    public Collection<ChessMove> kingHelper(ChessBoard board, ChessPosition start) {
        ArrayList<ChessMove> validMoves = new ArrayList<>();
        validMoves.addAll(movesCalculator(board, start, 1, 1, 1, true, false));
        validMoves.addAll(movesCalculator(board, start, 1, 1, -1, true, false));
        validMoves.addAll(movesCalculator(board, start, 1, -1, -1, true, false));
        validMoves.addAll(movesCalculator(board, start, 1, -1, 1, true, false));
        validMoves.addAll(movesCalculator(board, start, 1, 1, 0, true, false));
        validMoves.addAll(movesCalculator(board, start, 1, 0, -1, true, false));
        validMoves.addAll(movesCalculator(board, start, 1, -1, 0, true, false));
        validMoves.addAll(movesCalculator(board, start, 1, 0, 1, true, false));
        return validMoves;
    }

    public Collection<ChessMove> knightHelper(ChessBoard board, ChessPosition start) {
        ArrayList<ChessMove> validMoves = new ArrayList<>();
        validMoves.addAll(movesCalculator(board, start, 1, 1, 2, true, false));
        validMoves.addAll(movesCalculator(board, start, 1, 2, 1, true, false));
        validMoves.addAll(movesCalculator(board, start, 1, 2, -1, true, false));
        validMoves.addAll(movesCalculator(board, start, 1, 1, -2, true, false));
        validMoves.addAll(movesCalculator(board, start, 1, -1, -2, true, false));
        validMoves.addAll(movesCalculator(board, start, 1, -2, -1, true, false));
        validMoves.addAll(movesCalculator(board, start, 1, -2, 1, true, false));
        validMoves.addAll(movesCalculator(board, start, 1, -1, 2, true, false));
        return validMoves;
    }

    public Collection<ChessMove> pawnHelper(ChessBoard board, ChessPosition start) {
        ArrayList<ChessMove> validMoves = new ArrayList<>();

        int pawnDy = 0;
        int pawnRange = 1;

        switch (pieceColor) {
            case WHITE -> pawnDy = 1;
            case BLACK -> pawnDy = -1;
        }

        switch (start.getRow()) {
            case 2 -> pawnRange = pieceColor == ChessGame.TeamColor.WHITE ? 2 : 1;
            case 7 -> pawnRange = pieceColor == ChessGame.TeamColor.BLACK ? 2 : 1;
        }

        validMoves.addAll(movesCalculator(board, start, pawnRange, 0, pawnDy, false, false));
        validMoves.addAll(movesCalculator(board, start, pawnRange, -1, pawnDy, true, true));
        validMoves.addAll(movesCalculator(board, start, pawnRange, 1, pawnDy, true, true));

        return validMoves;
    }

    public Collection<ChessMove> movesCalculator(ChessBoard board, ChessPosition start,
                                                 int range, int dx, int dy,
                                                 boolean capture, boolean moveIFFcapture) {
        ArrayList<ChessMove> validMoves = new ArrayList<>();

        int x = start.getColumn();
        int y = start.getRow();

        for (int i = 1; i <= range ; i++) {

            int newX = x + dx * i;
            int newY = y + dy * i;

            if (1 <= newX && newX <= 8 && 1 <= newY && newY <= 8) {
                var end = new ChessPosition(newY, newX);
                var newPiece = board.getPiece(end);
                var newMove = new ChessMove(start, end, null);
                var promoMoves = getPromoMoves(start, end, newY);

                if (handleCaptureLogic(capture, newPiece, newY, validMoves, promoMoves, newMove)) {
                    break;
                }

                if(moveIFFcapture){
                    break;
                }

                // promotion logic
                if (type == PieceType.PAWN && (newY == 8 || newY == 1)){
                    validMoves.addAll(List.of(promoMoves));
                    break;
                }

                validMoves.add(newMove);
            }
        }

        return validMoves;
    }

    private boolean handleCaptureLogic(boolean capture, ChessPiece newPiece,
                                       int newY, ArrayList<ChessMove> validMoves,
                                       ChessMove[] promoMoves, ChessMove newMove) {
        // capture logic
        if (newPiece != null) {
            if (pieceColor != newPiece.pieceColor && capture) {
                // promotion logic
                if (type == PieceType.PAWN && (newY == 8 || newY == 1)){
                    validMoves.addAll(List.of(promoMoves));
                    return true;
                }
                validMoves.add(newMove);
            }
            return true;
        }
        return false;
    }

    private ChessMove[] getPromoMoves(ChessPosition start, ChessPosition end, int newY) {
        var promoMoves= new ChessMove[4];

        if (type == PieceType.PAWN && (newY == 8 || newY == 1)) {
            promoMoves[0] = new ChessMove(start, end, PieceType.ROOK);
            promoMoves[1] = new ChessMove(start, end, PieceType.BISHOP);
            promoMoves[2] = new ChessMove(start, end, PieceType.KNIGHT);
            promoMoves[3] = new ChessMove(start, end, PieceType.QUEEN);
        }
        return promoMoves;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    @Override
    public String toString() {
        String rString;
        switch (type) {
            case PieceType.PAWN     -> rString = pieceColor == ChessGame.TeamColor.WHITE ? "P" : "p";
            case PieceType.ROOK     -> rString = pieceColor == ChessGame.TeamColor.WHITE ? "R" : "r";
            case PieceType.KNIGHT   -> rString = pieceColor == ChessGame.TeamColor.WHITE ? "N" : "n";
            case PieceType.BISHOP   -> rString = pieceColor == ChessGame.TeamColor.WHITE ? "B" : "b";
            case PieceType.QUEEN    -> rString = pieceColor == ChessGame.TeamColor.WHITE ? "Q" : "q";
            case PieceType.KING     -> rString = pieceColor == ChessGame.TeamColor.WHITE ? "K" : "k";
            default                 -> rString = " ";
        }
        return rString;
    }
}
