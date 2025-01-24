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

    public Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition piecePosition) {
        throw new RuntimeException("kingMoves not implemented");
    }

    public Collection<ChessMove> queenMoves(ChessBoard board, ChessPosition piecePosition) {
        throw new RuntimeException("queenMoves not implemented");
    }

    public Collection<ChessMove> bishopMoves(ChessBoard board, ChessPosition piecePosition) {
        // validMoves init (will be returned at end of method)
        Collection<ChessMove> validMoves = new ArrayList<>();
        // x and y init
        int x = piecePosition.getRow();
        int y = piecePosition.getColumn();
        // booleans init
        boolean boolA = true;
        boolean boolB = true;
        boolean boolC = true;
        boolean boolD = true;
        // validMoves list appending
        for(int i = 1; i < 8; i++) {
            // (+1, +1) direction
            if (x + i <= 8 && y + i <= 8 && boolA) {
                ChessPosition newPos = new ChessPosition(x + i, y + i);
                ChessPiece newPiece = board.getPiece(newPos);
                ChessMove newMove = new ChessMove(piecePosition, newPos, null);
                if (newPiece != null) {
                    boolA = false;
                    if (newPiece.pieceColor != this.pieceColor) {
                        validMoves.add(newMove);
                    }
                } else {
                    validMoves.add(newMove);
                }
            }
            // (+1,-1) direction
            if (x + i <= 8 && y - i >= 1 && boolB) {
                ChessPosition newPos = new ChessPosition(x + i, y - i);
                ChessPiece newPiece = board.getPiece(newPos);
                ChessMove newMove = new ChessMove(piecePosition, newPos, null);
                if (newPiece != null) {
                    boolB = false;
                    if (newPiece.pieceColor != this.pieceColor) {
                        validMoves.add(newMove);
                    }
                } else {
                    validMoves.add(newMove);
                }
            }
            // (-1,-1) direction
            if (x - i >= 1 && y - i >= 1 && boolC) {
                ChessPosition newPos = new ChessPosition(x - i,y - i);
                ChessPiece newPiece = board.getPiece(newPos);
                ChessMove newMove = new ChessMove(piecePosition, newPos, null);
                if (newPiece != null) {
                    boolC = false;
                    if (newPiece.pieceColor != this.pieceColor) {
                        validMoves.add(newMove);
                    }
                } else {
                    validMoves.add(newMove);
                }
            }
            // (-1,+1) direction
            if (x - i >= 1 && y + i <= 8 && boolD) {
                ChessPosition newPos = new ChessPosition(x - i, y + i);
                ChessPiece newPiece = board.getPiece(newPos);
                ChessMove newMove = new ChessMove(piecePosition, newPos, null);
                if (newPiece != null) {
                    boolD = false;
                    if (newPiece.pieceColor != this.pieceColor) {
                        validMoves.add(newMove);
                    }
                } else {
                    validMoves.add(newMove);
                }
            }
        }
        return validMoves;
    }

    public Collection<ChessMove> knightMoves(ChessBoard board, ChessPosition piecePosition) {
        throw new RuntimeException("knightMoves not implemented");
    }

    public Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition piecePosition) {
        // validMoves init (will be returned at end of method)
        Collection<ChessMove> validMoves = new ArrayList<>();
        // x and y init
        int x = piecePosition.getRow();
        int y = piecePosition.getColumn();
        // booleans init
        boolean boolA = true;
        boolean boolB = true;
        boolean boolC = true;
        boolean boolD = true;
        // validMoves list appending
        for(int i = 1; i < 8; i++) {
            // (+1, 0) direction
            if (x + i <= 8 && y <= 8 && boolA) {
                ChessPosition newPos = new ChessPosition(x + i, y);
                ChessPiece newPiece = board.getPiece(newPos);
                ChessMove newMove = new ChessMove(piecePosition, newPos, null);
                if (newPiece != null) {
                    boolA = false;
                    if (newPiece.pieceColor != this.pieceColor) {
                        validMoves.add(newMove);
                    }
                } else {
                    validMoves.add(newMove);
                }
            }
            // (0, -1) direction
            if (x <= 8 && y - i >= 1 && boolB) {
                ChessPosition newPos = new ChessPosition(x, y - i);
                ChessPiece newPiece = board.getPiece(newPos);
                ChessMove newMove = new ChessMove(piecePosition, newPos, null);
                if (newPiece != null) {
                    boolB = false;
                    if (newPiece.pieceColor != this.pieceColor) {
                        validMoves.add(newMove);
                    }
                } else {
                    validMoves.add(newMove);
                }
            }
            // (-1, 0) direction
            if (x - i >= 1 && y >= 1 && boolC) {
                ChessPosition newPos = new ChessPosition(x - i,y);
                ChessPiece newPiece = board.getPiece(newPos);
                ChessMove newMove = new ChessMove(piecePosition, newPos, null);
                if (newPiece != null) {
                    boolC = false;
                    if (newPiece.pieceColor != this.pieceColor) {
                        validMoves.add(newMove);
                    }
                } else {
                    validMoves.add(newMove);
                }
            }
            // (0 , +1) direction
            if (x >= 1 && y + i <= 8 && boolD) {
                ChessPosition newPos = new ChessPosition(x, y + i);
                ChessPiece newPiece = board.getPiece(newPos);
                ChessMove newMove = new ChessMove(piecePosition, newPos, null);
                if (newPiece != null) {
                    boolD = false;
                    if (newPiece.pieceColor != this.pieceColor) {
                        validMoves.add(newMove);
                    }
                } else {
                    validMoves.add(newMove);
                }
            }
        }
        return validMoves;
    }

    public Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition piecePosition) {
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
