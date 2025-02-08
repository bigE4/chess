package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class ChessCastle extends ChessGame {

    static boolean queensideWhite;
    static boolean kingsideWhite;
    static boolean queensideBlack;
    static boolean kingsideBlack;

    public ChessCastle() {
        queensideWhite = true;
        kingsideWhite = true;
        queensideBlack = true;
        kingsideBlack = true;
    }

    public static void castleUpdate(ChessPosition start, ChessPosition end) {

        System.out.println("castleUpdate called.");

        ChessPosition
                K = new ChessPosition(1,5),
                lR = new ChessPosition(1,1),
                rR = new ChessPosition(1,8),
                k = new ChessPosition(8,5),
                lr = new ChessPosition(8,1),
                rr = new ChessPosition(8,8);

        if (start.equals(K)) {
            queensideWhite = false;
            kingsideWhite = false;
        }
        if (start.equals(k)) {
            queensideBlack = false;
            kingsideBlack = false;
        }
        if (start.equals(lR) || end.equals(lR)) {
            queensideWhite = false;
        }
        if (start.equals(rR) || end.equals(rR)) {
            kingsideWhite = false;
        }
        if (start.equals(lr) || end.equals(lr)) {
            queensideBlack = false;
        }
        if (start.equals(rr) || end.equals(rr)) {
            kingsideBlack = false;
        }
    }

    public static void castleLogic(ChessPosition start, Collection<ChessMove> validMoves) {
        System.out.println("We are starting the move at: " + start);
        System.out.println("queensideBlack is: " + queensideBlack);
        System.out.println("The PieceType is: " + chessBoard.getPiece(start).getPieceType());


        if (chessBoard.getPiece(start).getPieceType() == ChessPiece.PieceType.KING) {
            System.out.println(canQueenside());
            if (canQueenside()) {
                ChessMove queenside = queensideCreator(start);
                validMoves.add(queenside);
            }
            if (canKingside()) {
                ChessMove kingside = kingsideCreator(start);
                validMoves.add(kingside);
            }
        }
    }

    public static boolean canQueenside() {
        System.out.println("canQueenside called.");
        System.out.println("teamColor is: "+ teamTurn);
        // whiteSide
        if (teamTurn == TeamColor.WHITE && queensideWhite && !isInCheck(ChessGame.TeamColor.WHITE)) {
            ArrayList<ChessMove> blackMoves = getEnemyMoves(ChessGame.TeamColor.WHITE);

            ArrayList<ChessPosition> illegalPositions = new ArrayList<>();
            illegalPositions.add(new ChessPosition(1,3));
            illegalPositions.add(new ChessPosition(1,4));

            // check if there are any pieces at P(1,3) or P(1,4)
            for (ChessPosition illegalPosition : illegalPositions) {
                ChessPiece piece = chessBoard.getPiece(illegalPosition);
                if (piece != null) {
                    return false;
                }
            }

            // check if any of blackMoves have end positions at P(1,3) or P(1,4)
            for (ChessMove move : blackMoves) {
                ChessPosition end = move.getEndPosition();
                for (ChessPosition illegalPosition : illegalPositions) {
                    if(illegalPosition.equals(end)) {
                        return false;
                    }
                }
            }
            return true;
        }

        // blackSide
        System.out.println(teamTurn == TeamColor.BLACK);
        System.out.println("queensideBlack is: " + queensideBlack);
        System.out.println(!isInCheck(ChessGame.TeamColor.BLACK));
        System.out.println("Conds ^^^");

        if (teamTurn == TeamColor.BLACK && queensideBlack && !isInCheck(ChessGame.TeamColor.BLACK)) {
            ArrayList<ChessMove> whiteMoves = getEnemyMoves(ChessGame.TeamColor.BLACK);

            ArrayList<ChessPosition> illegalPositions = new ArrayList<>();
            illegalPositions.add(new ChessPosition(8,3));
            illegalPositions.add(new ChessPosition(8,4));

            // check if there are any pieces at P(8,3) or P(8,4)
            for (ChessPosition illegalPosition : illegalPositions) {
                ChessPiece piece = chessBoard.getPiece(illegalPosition);
                if (piece != null) {
                    return false;
                }
            }

            // check if any of whiteMoves have end positions at P(8,3) or P(8,4)
            for (ChessMove move : whiteMoves) {
                ChessPosition end = move.getEndPosition();
                for (ChessPosition illegalPosition : illegalPositions) {
                    if(end.equals(illegalPosition)) {
                        return false;
                    }
                }
            }
            return true;
        }

        return false;
    }

    public static  ChessMove queensideCreator(ChessPosition start) {
        return new ChessMove(start, new ChessPosition(start.getRow(), start.getColumn() - 2), null);
    }

    public static  boolean canKingside() {
        // whiteSide
        if (teamTurn == ChessGame.TeamColor.WHITE && kingsideWhite && !isInCheck(ChessGame.TeamColor.WHITE)) {
            ArrayList<ChessMove> blackMoves = getEnemyMoves(ChessGame.TeamColor.WHITE);

            ArrayList<ChessPosition> illegalPositions = new ArrayList<>();
            illegalPositions.add(new ChessPosition(1,6));
            illegalPositions.add(new ChessPosition(1,7));

            // check if there are any pieces at P(1,6) or P(1,7)
            for (ChessPosition illegalPosition : illegalPositions) {
                ChessPiece piece = chessBoard.getPiece(illegalPosition);
                if (piece != null) {
                    return false;
                }
            }

            // check if any of blackMoves have end positions at P(1,6) or P(1,7)
            for (ChessMove move : blackMoves) {
                ChessPosition end = move.getEndPosition();
                for (ChessPosition illegalPosition : illegalPositions) {
                    if(end.equals(illegalPosition)) {
                        return false;
                    }
                }
            }
            return true;
        }
        // blackSide
        if (teamTurn == ChessGame.TeamColor.BLACK && kingsideBlack && !isInCheck(ChessGame.TeamColor.BLACK)) {
            ArrayList<ChessMove> whiteMoves = getEnemyMoves(ChessGame.TeamColor.BLACK);

            ArrayList<ChessPosition> illegalPositions = new ArrayList<>();
            illegalPositions.add(new ChessPosition(8,6));
            illegalPositions.add(new ChessPosition(8,7));

            // check if there are any pieces at P(8,6) or P(8,7)
            for (ChessPosition illegalPosition : illegalPositions) {
                ChessPiece piece = chessBoard.getPiece(illegalPosition);
                if (piece != null) {
                    return false;
                }
            }

            // check if any of whiteMoves have end positions at P(8,6) or P(8,7)
            for (ChessMove move : whiteMoves) {
                ChessPosition end = move.getEndPosition();
                for (ChessPosition illegalPosition : illegalPositions) {
                    if(end.equals(illegalPosition)) {
                        return false;
                    }
                }
            }
            return true;
        }

        return false;
    }

    public static  ChessMove kingsideCreator(ChessPosition start) {
        return new ChessMove(start, new ChessPosition(start.getRow(), start.getColumn() + 2), null);
    }
}
