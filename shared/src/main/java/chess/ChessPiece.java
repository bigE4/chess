package chess;

import java.util.ArrayList;
import java.util.Collection;

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
        throw new RuntimeException("Not implemented");
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
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPiece reference = board.getPiece(myPosition);
        System.out.println(reference);
        System.out.println(myPosition);
        if (reference.getPieceType() == PieceType.KING) {
            return kingMoves(board, myPosition);
        }
        else if (reference.getPieceType() == PieceType.QUEEN) {
            return queenMoves(board, myPosition);
        }
        else if (reference.getPieceType() == PieceType.BISHOP) {
            return bishopMoves(board, myPosition);
        }
        else if (reference.getPieceType() == PieceType.KNIGHT) {
            return knightMoves(board, myPosition);
        }
        else if (reference.getPieceType() == PieceType.ROOK) {
            return rookMoves(board, myPosition);
        }
        else if (reference.getPieceType() == PieceType.PAWN) {
            return pawnMoves(board, myPosition);
        }
        throw new RuntimeException("pieceMoves not implemented");
    }

    public Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition kingPosition) {
        throw new RuntimeException("kingMoves not implemented");
    }

    public Collection<ChessMove> queenMoves(ChessBoard board, ChessPosition queenPosition) {
        throw new RuntimeException("queenMoves not implemented");
    }

    public Collection<ChessMove> bishopMoves(ChessBoard board, ChessPosition bishopPosition) {
        // validMoves init (will be returned at end of method)
        Collection<ChessMove> validMoves = new ArrayList<>();
        // x and y init
        int x = bishopPosition.getRow();
        int y = bishopPosition.getColumn();
        // WORK IN PROGRESS - validMoves
        for(int i = 0; i < 7; i++) {
            if (x + i < 8 && x + y < 8) {
                validMoves.add(
                        new ChessMove(bishopPosition,
                                new ChessPosition(x + i, y + i),
                                PieceType.BISHOP)
                );

            }
            if (x + i < 8 && x - y > 1) {
                validMoves.add(
                        new ChessMove(bishopPosition,
                                new ChessPosition(x + i, y - i),
                                PieceType.BISHOP)
                );

            }
            if (x - i > 1 && x - y > 1) {
                validMoves.add(
                        new ChessMove(bishopPosition,
                                new ChessPosition(x - i, y - i),
                                PieceType.BISHOP)
                );

            }
            if (x - i > 1 && x + y < 8) {
                validMoves.add(
                        new ChessMove(bishopPosition,
                                new ChessPosition(x + i, y + i),
                                PieceType.BISHOP)
                );

            }
        }

        return validMoves;
    }

    public Collection<ChessMove> knightMoves(ChessBoard board, ChessPosition knightPosition) {
        throw new RuntimeException("knightMoves not implemented");
    }

    public Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition rookPosition) {
        throw new RuntimeException("rookMoves not implemented");
    }

    public Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition pawnPosition) {
        throw new RuntimeException("pawnMoves not implemented");
    }

    @Override
    public String toString() {
        return type + " of " + pieceColor;
    }
}
