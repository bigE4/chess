package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    TeamColor teamTurn;
    ChessBoard chessBoard;
    ChessMove lastMove;

    public ChessGame() {
        teamTurn = TeamColor.WHITE;
        chessBoard = new ChessBoard();
        chessBoard.resetBoard();
        lastMove = null;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    public void switchTeamTurn() {
        if (teamTurn == TeamColor.WHITE) {
            teamTurn = TeamColor.BLACK;
        } else if (teamTurn == TeamColor.BLACK) {
            teamTurn = TeamColor.WHITE;
        }
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece chessPiece = chessBoard.getPiece(startPosition);
        ArrayList<ChessMove> validMoves = new ArrayList<>();
        for(ChessMove move : chessPiece.pieceMoves(chessBoard, startPosition)) {
            if (isValid(move, chessPiece.getTeamColor())) {
                validMoves.add(move);
            }
        }
        return validMoves;
    }

    private boolean isValid(ChessMove move, TeamColor teamColor) {
        ChessPiece temp = makeMoveHelper1(move);
        boolean inCheck = isInCheck(teamColor);
        makeMoveHelper2(move, temp);
        System.out.println(move);
        System.out.println(!inCheck);
        return !inCheck;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition start = move.getStartPosition(), end = move.getEndPosition();
        ChessPiece piece = chessBoard.getPiece(start);
        if (!isValid(move, piece.getTeamColor())) {
            throw new InvalidMoveException("Move is not valid");
        }



        chessBoard.addPiece(end, piece);
        chessBoard.addPiece(start, null);
        lastMove = move;
        switchTeamTurn();
    }

    public ChessPiece makeMoveHelper1(ChessMove move) {
        ChessPosition start = move.getStartPosition(), end = move.getEndPosition();
        ChessPiece startPiece = chessBoard.getPiece(start);
        ChessPiece endPiece = chessBoard.getPiece(end);
        chessBoard.addPiece(end, startPiece);
        chessBoard.addPiece(start, null);
        return endPiece;
    }

    public void makeMoveHelper2(ChessMove move, ChessPiece piece) {
        ChessPosition start = move.getStartPosition(), end = move.getEndPosition();
        ChessPiece endPiece = chessBoard.getPiece(end);
        chessBoard.addPiece(start, endPiece);
        chessBoard.addPiece(end, piece);
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {

        ChessPosition kingPos = getKingPos(teamColor);

        ArrayList<ChessMove> enemyMoves = getEnemyMoves(teamColor);

        // given the king's position, and the list of the enemy's moves, determines if king isInCheck
        for (ChessMove move : enemyMoves) {
            if (Objects.equals(move.getEndPosition(), kingPos)) {
                return true;
            }
        }
        return false;
    }

    private ChessPosition getKingPos(TeamColor teamColor) {
        // for loop to iterate through the chessBoard and find the kingPos of teamColor

        ChessPosition kingPos = null;

        kingLoop:
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPosition pos = new ChessPosition(i, j);
                ChessPiece piece = chessBoard.getPiece(pos);
                if (piece != null && piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() == teamColor) {
                    kingPos = pos;
                    break kingLoop;
                }
            }
        }
        return kingPos;
    }

    private ArrayList<ChessMove> getEnemyMoves(TeamColor teamColor) {
        ArrayList<ChessMove> enemyMoves = new ArrayList<>();

        iLoop:
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPosition pos = new ChessPosition(i, j);
                ChessPiece piece = chessBoard.getPiece(pos);
                if (piece != null && piece.getTeamColor() != teamColor) {
                    enemyMoves.addAll(piece.pieceMoves(chessBoard, pos));
                }
            }
        }
        return enemyMoves;
    }

    private ArrayList<ChessMove> getFriendMoves(TeamColor teamColor) {
        ArrayList<ChessMove> friendMoves = new ArrayList<>();

        iLoop:
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPosition pos = new ChessPosition(i, j);
                ChessPiece piece = chessBoard.getPiece(pos);
                if (piece != null && piece.getTeamColor() == teamColor) {
                    friendMoves.addAll(piece.pieceMoves(chessBoard, pos));
                }
            }
        }
        return friendMoves;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        ArrayList<ChessMove> friendMoves = getFriendMoves(teamColor);
        ArrayList<ChessMove> validMoves = new ArrayList<>();

        for (ChessMove move : friendMoves) {
            if (isValid(move, teamColor)) {
                validMoves.add(move);
            }
        }
        // if isInCheck && validMoves.isEmpty();
        return validMoves.isEmpty() && isInCheck(teamColor);
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        ArrayList<ChessMove> friendMoves = getFriendMoves(teamColor);
        ArrayList<ChessMove> validMoves = new ArrayList<>();

        for (ChessMove move : friendMoves) {
            if (isValid(move, teamColor)) {
                validMoves.add(move);
            }
        }
        // if !isInCheck && validMoves.isEmpty();
        return validMoves.isEmpty() && !isInCheck(teamColor);
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        chessBoard = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return chessBoard;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return teamTurn == chessGame.teamTurn && Objects.equals(chessBoard, chessGame.chessBoard) && Objects.equals(lastMove, chessGame.lastMove);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamTurn, chessBoard, lastMove);
    }
}
