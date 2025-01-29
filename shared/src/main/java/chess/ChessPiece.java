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
        Collection<ChessMove> validMoves = new ArrayList<>();
        switch (type){
            case BISHOP -> validMoves.addAll(bHelper(board, start));
            case ROOK -> validMoves.addAll(rHelper(board, start));
            case QUEEN -> validMoves.addAll(qHelper(board, start));
            case KING -> validMoves.addAll(kHelper(board, start));
            case KNIGHT -> validMoves.addAll(nHelper(board, start));
            case PAWN -> validMoves.addAll(pHelper(board, start));
        }
        return validMoves;
    }

    public Collection<ChessMove> bHelper(ChessBoard board, ChessPosition start) {
        Collection<ChessMove> validMoves = new ArrayList<>();
        validMoves.addAll(movesCalculator(board, start, 7, 1, 1, true, false));
        validMoves.addAll(movesCalculator(board, start, 7, 1, -1, true, false));
        validMoves.addAll(movesCalculator(board, start, 7, -1, -1, true, false));
        validMoves.addAll(movesCalculator(board, start, 7, -1, 1, true, false));
        return validMoves;
    }

    public Collection<ChessMove> rHelper(ChessBoard board, ChessPosition start) {
        Collection<ChessMove> validMoves = new ArrayList<>();
        validMoves.addAll(movesCalculator(board, start, 7, 1, 0, true, false));
        validMoves.addAll(movesCalculator(board, start, 7, 0, -1, true, false));
        validMoves.addAll(movesCalculator(board, start, 7, -1, 0, true, false));
        validMoves.addAll(movesCalculator(board, start, 7, 0, 1, true, false));
        return validMoves;
    }

    public Collection<ChessMove> qHelper(ChessBoard board, ChessPosition start) {
        Collection<ChessMove> validMoves = new ArrayList<>();
        validMoves.addAll(movesCalculator(board, start, 7, 1, 1, true, false));
        validMoves.addAll(movesCalculator(board, start, 7, 1, -1, true, false));
        validMoves.addAll(movesCalculator(board, start, 7, -1, -1, true, false));
        validMoves.addAll(movesCalculator(board, start, 7, -1, 1, true, false));
        validMoves.addAll(movesCalculator(board, start, 7, 1, 0, true, false));
        validMoves.addAll(movesCalculator(board, start, 7, 0, -1, true, false));
        validMoves.addAll(movesCalculator(board, start, 7, -1, 0, true, false));
        validMoves.addAll(movesCalculator(board, start, 7, 0, 1, true, false));
        return validMoves;
    }

    public Collection<ChessMove> kHelper(ChessBoard board, ChessPosition start) {
        Collection<ChessMove> validMoves = new ArrayList<>();
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

    public Collection<ChessMove> nHelper(ChessBoard board, ChessPosition start) {
        Collection<ChessMove> validMoves = new ArrayList<>();
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

    public Collection<ChessMove> pHelper(ChessBoard board, ChessPosition start) {
        Collection<ChessMove> validMoves = new ArrayList<>();

        int pawn_dy = 0;
        int pawn_range = 1;

        switch (pieceColor) {
            case WHITE -> pawn_dy = 1;
            case BLACK -> pawn_dy = -1;
        }

        switch (start.getRow()) {
            case 2 -> pawn_range = pieceColor == ChessGame.TeamColor.WHITE ? 2 : 1;
            case 7 -> pawn_range = pieceColor == ChessGame.TeamColor.BLACK ? 2 : 1;
        }

        validMoves.addAll(movesCalculator(board, start, pawn_range, 0, pawn_dy, false, false));
        validMoves.addAll(movesCalculator(board, start, pawn_range, -1, pawn_dy, true, true));
        validMoves.addAll(movesCalculator(board, start, pawn_range, 1, pawn_dy, true, true));

        return validMoves;
    }

    public Collection<ChessMove> movesCalculator(ChessBoard board, ChessPosition start,
                                                 int range, int dx, int dy,
                                                 boolean capture, boolean moveIFFcapture) {
        Collection<ChessMove> validMoves = new ArrayList<>();

        int x = start.getColumn();
        int y = start.getRow();

        for (int i = 1; i <= range ; i++) {

            int newX = x + dx * i;
            int newY = y + dy * i;

            if (1 <= newX && newX <= 8 && 1 <= newY && newY <= 8) {
                ChessPosition end = new ChessPosition(newY, newX);
                ChessPiece newPiece = board.getPiece(end);
                ChessMove newMove = new ChessMove(start, end, null);
                ChessMove[] promoMoves = getPromoMoves(start, end, newY);

                // capture logic
                if (newPiece != null) {
                    if (pieceColor != newPiece.pieceColor && capture) {
                        // promo only
                        if (type == PieceType.PAWN && (newY == 8 || newY == 1)){
                            validMoves.addAll(List.of(promoMoves));
                            break;
                        }
                        validMoves.add(newMove);
                    }
                    break;
                }

                if(moveIFFcapture){
                    break;
                }

                if (type == PieceType.PAWN && (newY == 8 || newY == 1)){
                    validMoves.addAll(List.of(promoMoves));
                    break;
                }
                validMoves.add(newMove);
            }
        }
        return validMoves;
    }

    private ChessMove[] getPromoMoves(ChessPosition start, ChessPosition end, int newY) {
        ChessMove[] pawnMoves= new ChessMove[4];

        if (type == PieceType.PAWN && (newY == 8 || newY == 1)) {
            pawnMoves[0] = new ChessMove(start, end, PieceType.ROOK);
            pawnMoves[1] = new ChessMove(start, end, PieceType.BISHOP);
            pawnMoves[2] = new ChessMove(start, end, PieceType.KNIGHT);
            pawnMoves[3] = new ChessMove(start, end, PieceType.QUEEN);
        }
        return pawnMoves;
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
            case PieceType.PAWN -> rString = pieceColor == ChessGame.TeamColor.WHITE ? "P" : "p";
            case PieceType.ROOK -> rString = pieceColor == ChessGame.TeamColor.WHITE ? "R" : "r";
            case PieceType.KNIGHT -> rString = pieceColor == ChessGame.TeamColor.WHITE ? "N" : "n";
            case PieceType.BISHOP -> rString = pieceColor == ChessGame.TeamColor.WHITE ? "B" : "b";
            case PieceType.QUEEN -> rString = pieceColor == ChessGame.TeamColor.WHITE ? "Q" : "q";
            case PieceType.KING -> rString = pieceColor == ChessGame.TeamColor.WHITE ? "K" : "k";
            default -> rString = " ";
        }
        return rString;
    }
}
