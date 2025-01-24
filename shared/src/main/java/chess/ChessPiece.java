package chess;

import java.util.ArrayList;
import java.util.Collection;
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
        throw new RuntimeException("pieceMoves did not call piecetype Moves");
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
        // booleans init
        boolean a = true;
        boolean b = true;
        boolean c = true;
        boolean d = true;
        // WORK IN PROGRESS - validMoves
        for(int i = 1; i < 8; i++) {
            System.out.println("For Loop Iteration: " + i);
            if (x + i <= 8 && y + i <= 8 && a) {
                ChessPosition newPos = new ChessPosition(x + i, y + i);
                if (board.getPiece(newPos) != null) {
                    a = false;
                    if (board.getPiece(newPos).pieceColor != board.getPiece(bishopPosition).pieceColor) {
                        System.out.println("Adding " + newPos);
                        validMoves.add(new ChessMove(bishopPosition, newPos, null));
                    }
                    System.out.println(board.getPiece(newPos));
                } else {
                    System.out.println("Adding " + newPos);
                    validMoves.add(new ChessMove(bishopPosition, newPos, null));
                }
            }
            if (x + i <= 8 && y - i >= 1 && b) {
                ChessPosition newPos = new ChessPosition(x + i, y - i);
                if (board.getPiece(newPos) != null) {
                    b = false;
                    if (board.getPiece(newPos).pieceColor != board.getPiece(bishopPosition).pieceColor) {
                        System.out.println("Adding " + newPos);
                        validMoves.add(new ChessMove(bishopPosition, newPos, null));
                    }
                } else {
                    System.out.println("Adding " + newPos);
                    validMoves.add(new ChessMove(bishopPosition, new ChessPosition(x + i, y - i), null));
                }
            }
            if (x - i >= 1 && y - i >= 1 && c) {
                ChessPosition newPos = new ChessPosition(x - i,y - i);
                if (board.getPiece(newPos) != null) {
                    c = false;
                    if (board.getPiece(newPos).pieceColor != board.getPiece(bishopPosition).pieceColor) {
                        System.out.println("Adding " + newPos);
                        validMoves.add(new ChessMove(bishopPosition, newPos, null));
                    }
                } else {
                    System.out.println("Adding " + newPos);
                    validMoves.add(new ChessMove(bishopPosition, new ChessPosition(x - i, y - i), null));
                }
            }
            if (x - i >= 1 && y + i <= 8 && d) {
                ChessPosition newPos = new ChessPosition(x - i, y + i);
                if (board.getPiece(newPos) != null) {
                    d = false;
                    if (board.getPiece(newPos).pieceColor != board.getPiece(bishopPosition).pieceColor) {
                        System.out.println("Adding " + newPos);
                        validMoves.add(new ChessMove(bishopPosition, newPos, null));
                    }
                } else {
                    System.out.println("Adding " + newPos);
                    validMoves.add(new ChessMove(bishopPosition, new ChessPosition(x - i, y + i), null));
                }
            }
        }
        System.out.println(validMoves);
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
        return "ChessPiece: " + type + " of " + pieceColor;
    }
}
