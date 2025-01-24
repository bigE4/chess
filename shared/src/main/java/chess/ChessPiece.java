package chess;

import java.util.ArrayList;
import java.util.Arrays;
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
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPiece reference = board.getPiece(myPosition);
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
        // validMoves init (will be returned at end of method)
        Collection<ChessMove> validMoves = new ArrayList<>();
        // x and y init
        int x = piecePosition.getRow();
        int y = piecePosition.getColumn();
        // validMoves list appending
        // (+1, +1) direction
        if (x + 1 <= 8 && y + 1 <= 8) {
            ChessPosition newPos = new ChessPosition(x + 1, y + 1);
            ChessPiece newPiece = board.getPiece(newPos);
            ChessMove newMove = new ChessMove(piecePosition, newPos, null);
            if (newPiece != null) {
                if (newPiece.pieceColor != this.pieceColor) {
                    validMoves.add(newMove);
                }
            } else {
                validMoves.add(newMove);
            }
        }
        // (+1 0) direction
        if (x + 1 <= 8 && y <= 8) {
            ChessPosition newPos = new ChessPosition(x + 1, y);
            ChessPiece newPiece = board.getPiece(newPos);
            ChessMove newMove = new ChessMove(piecePosition, newPos, null);
            if (newPiece != null) {
                if (newPiece.pieceColor != this.pieceColor) {
                    validMoves.add(newMove);
                }
            } else {
                validMoves.add(newMove);
            }
        }
        // (+1, -1) direction
        if (x + 1 <= 8 && y - 1 >= 1) {
            ChessPosition newPos = new ChessPosition(x + 1,y - 1);
            ChessPiece newPiece = board.getPiece(newPos);
            ChessMove newMove = new ChessMove(piecePosition, newPos, null);
            if (newPiece != null) {
                if (newPiece.pieceColor != this.pieceColor) {
                    validMoves.add(newMove);
                }
            } else {
                validMoves.add(newMove);
            }
        }
        // (0 , -1) direction
        if (x <= 8 && y - 1 >= 1) {
            ChessPosition newPos = new ChessPosition(x, y - 1);
            ChessPiece newPiece = board.getPiece(newPos);
            ChessMove newMove = new ChessMove(piecePosition, newPos, null);
            if (newPiece != null) {
                if (newPiece.pieceColor != this.pieceColor) {
                    validMoves.add(newMove);
                }
            } else {
                validMoves.add(newMove);
            }
        }
        // (-1 , -1) direction
        if (x - 1 >= 1 && y - 1 >= 1) {
            ChessPosition newPos = new ChessPosition(x - 1, y - 1);
            ChessPiece newPiece = board.getPiece(newPos);
            ChessMove newMove = new ChessMove(piecePosition, newPos, null);
            if (newPiece != null) {
                if (newPiece.pieceColor != this.pieceColor) {
                    validMoves.add(newMove);
                }
            } else {
                validMoves.add(newMove);
            }
        }
        // (-1 , 0) direction
        if (x - 1 >= 1 && y - 1 >= 1) {
            ChessPosition newPos = new ChessPosition(x - 1, y);
            ChessPiece newPiece = board.getPiece(newPos);
            ChessMove newMove = new ChessMove(piecePosition, newPos, null);
            if (newPiece != null) {
                if (newPiece.pieceColor != this.pieceColor) {
                    validMoves.add(newMove);
                }
            } else {
                validMoves.add(newMove);
            }
        }
        // (-1 , +1) direction
        if (x - 1 >= 1 && y + 1 <= 8) {
            ChessPosition newPos = new ChessPosition(x - 1, y + 1);
            ChessPiece newPiece = board.getPiece(newPos);
            ChessMove newMove = new ChessMove(piecePosition, newPos, null);
            if (newPiece != null) {
                if (newPiece.pieceColor != this.pieceColor) {
                    validMoves.add(newMove);
                }
            } else {
                validMoves.add(newMove);
            }
        }
        // (0 , +1) direction
        if (x >= 1 && y + 1 <= 8) {
            ChessPosition newPos = new ChessPosition(x, y + 1);
            ChessPiece newPiece = board.getPiece(newPos);
            ChessMove newMove = new ChessMove(piecePosition, newPos, null);
            if (newPiece != null) {
                if (newPiece.pieceColor != this.pieceColor) {
                    validMoves.add(newMove);
                }
            } else {
                validMoves.add(newMove);
            }
        }
        return validMoves;
    }

    public Collection<ChessMove> queenMoves(ChessBoard board, ChessPosition piecePosition) {
        Collection<ChessMove> bMoves = bishopMoves(board, piecePosition);
        Collection<ChessMove> rMoves = rookMoves(board, piecePosition);
        Collection<ChessMove> validMoves = new ArrayList<>();
        validMoves.addAll(bMoves);
        validMoves.addAll(rMoves);
        return validMoves;
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
        // validMoves init (will be returned at end of method)
        Collection<ChessMove> validMoves = new ArrayList<>();
        // x and y init
        int x = piecePosition.getRow();
        int y = piecePosition.getColumn();
        // validMoves list appending
        // (+1, +2) direction
        if (x + 1 <= 8 && y + 2 <= 8) {
            ChessPosition newPos = new ChessPosition(x + 1, y + 2);
            ChessPiece newPiece = board.getPiece(newPos);
            ChessMove newMove = new ChessMove(piecePosition, newPos, null);
            if (newPiece != null) {
                if (newPiece.pieceColor != this.pieceColor) {
                    validMoves.add(newMove);
                }
            } else {
                validMoves.add(newMove);
            }
        }
        // (+2, +1) direction
        if (x + 2 <= 8 && y + 1 <= 8) {
            ChessPosition newPos = new ChessPosition(x + 2, y + 1);
            ChessPiece newPiece = board.getPiece(newPos);
            ChessMove newMove = new ChessMove(piecePosition, newPos, null);
            if (newPiece != null) {
                if (newPiece.pieceColor != this.pieceColor) {
                    validMoves.add(newMove);
                }
            } else {
                validMoves.add(newMove);
            }
        }
        // (+2, -1) direction
        if (x + 2 <= 8 && y - 1 >= 1) {
            ChessPosition newPos = new ChessPosition(x + 2,y - 1);
            ChessPiece newPiece = board.getPiece(newPos);
            ChessMove newMove = new ChessMove(piecePosition, newPos, null);
            if (newPiece != null) {
                if (newPiece.pieceColor != this.pieceColor) {
                    validMoves.add(newMove);
                }
            } else {
                validMoves.add(newMove);
            }
        }
        // (+1 , -2) direction
        if (x + 1 <= 8 && y - 2 >= 1) {
            ChessPosition newPos = new ChessPosition(x + 1, y - 2);
            ChessPiece newPiece = board.getPiece(newPos);
            ChessMove newMove = new ChessMove(piecePosition, newPos, null);
            if (newPiece != null) {
                if (newPiece.pieceColor != this.pieceColor) {
                    validMoves.add(newMove);
                }
            } else {
                validMoves.add(newMove);
            }
        }
        // (-1 , -2) direction
        if (x - 1 >= 1 && y - 2 >= 1) {
            ChessPosition newPos = new ChessPosition(x - 1, y - 2);
            ChessPiece newPiece = board.getPiece(newPos);
            ChessMove newMove = new ChessMove(piecePosition, newPos, null);
            if (newPiece != null) {
                if (newPiece.pieceColor != this.pieceColor) {
                    validMoves.add(newMove);
                }
            } else {
                validMoves.add(newMove);
            }
        }
        // (-2 , -1) direction
        if (x - 2 >= 1 && y - 1 >= 1) {
            ChessPosition newPos = new ChessPosition(x - 2, y - 1);
            ChessPiece newPiece = board.getPiece(newPos);
            ChessMove newMove = new ChessMove(piecePosition, newPos, null);
            if (newPiece != null) {
                if (newPiece.pieceColor != this.pieceColor) {
                    validMoves.add(newMove);
                }
            } else {
                validMoves.add(newMove);
            }
        }
        // (-2 , +1) direction
        if (x - 2 >= 1 && y + 1 <= 8) {
            ChessPosition newPos = new ChessPosition(x - 2, y + 1);
            ChessPiece newPiece = board.getPiece(newPos);
            ChessMove newMove = new ChessMove(piecePosition, newPos, null);
            if (newPiece != null) {
                if (newPiece.pieceColor != this.pieceColor) {
                    validMoves.add(newMove);
                }
            } else {
                validMoves.add(newMove);
            }
        }
        // (-1 , +2) direction
        if (x - 1 >= 1 && y + 2 <= 8) {
            ChessPosition newPos = new ChessPosition(x - 1, y + 2);
            ChessPiece newPiece = board.getPiece(newPos);
            ChessMove newMove = new ChessMove(piecePosition, newPos, null);
            if (newPiece != null) {
                if (newPiece.pieceColor != this.pieceColor) {
                    validMoves.add(newMove);
                }
            } else {
                validMoves.add(newMove);
            }
        }
        return validMoves;
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
        System.out.println(board);
        // validMoves init (will be returned at end of method)
        Collection<ChessMove> validMoves = new ArrayList<>();
        // x and y init
        int x = piecePosition.getColumn();
        int y = piecePosition.getRow();
        // WHITE PAWN
        if (this.pieceColor == ChessGame.TeamColor.WHITE) {
            // Forward Move (x, y + 1)
            if (x <= 8 && y + 1 <= 8) {
                ChessPosition newPos = new ChessPosition(y + 1, x);
                ChessPiece newPiece = board.getPiece(newPos);
                ChessMove newMove = new ChessMove(piecePosition, newPos, null);
                // Promotion logic
                if (y + 1 == 8) {
                    validMoves.addAll(Arrays.asList(
                            new ChessMove(piecePosition, newPos, PieceType.ROOK),
                            new ChessMove(piecePosition, newPos, PieceType.KNIGHT),
                            new ChessMove(piecePosition, newPos, PieceType.BISHOP),
                            new ChessMove(piecePosition, newPos, PieceType.QUEEN))
                    );
                } else if (newPiece == null) {
                    validMoves.add(newMove);
                }
            }
            // Left Capture (x - 1, y + 1)
            if (x - 1 >= 1 && y + 1 <= 8) {
                ChessPosition newPos = new ChessPosition(y + 1, x - 1);
                ChessPiece newPiece = board.getPiece(newPos);
                ChessMove newMove = new ChessMove(piecePosition, newPos, null);
                if (newPiece != null && newPiece.pieceColor != this.pieceColor) {
                    if (y + 1 == 8) {
                        validMoves.addAll(Arrays.asList(
                                new ChessMove(piecePosition, newPos, PieceType.ROOK),
                                new ChessMove(piecePosition, newPos, PieceType.KNIGHT),
                                new ChessMove(piecePosition, newPos, PieceType.BISHOP),
                                new ChessMove(piecePosition, newPos, PieceType.QUEEN))
                        );
                    } else {
                        validMoves.add(newMove);
                    }
                }
            }
            // Right Capture (x + 1, y + 1)
            if (x + 1 <= 8 && y + 1 <= 8) {
                ChessPosition newPos = new ChessPosition(y + 1, x + 1);
                ChessPiece newPiece = board.getPiece(newPos);
                ChessMove newMove = new ChessMove(piecePosition, newPos, null);
                if (newPiece != null && newPiece.pieceColor != this.pieceColor) {
                    if (y + 1 == 8) {
                        validMoves.addAll(Arrays.asList(
                                new ChessMove(piecePosition, newPos, PieceType.ROOK),
                                new ChessMove(piecePosition, newPos, PieceType.KNIGHT),
                                new ChessMove(piecePosition, newPos, PieceType.BISHOP),
                                new ChessMove(piecePosition, newPos, PieceType.QUEEN))
                        );
                    } else {
                        validMoves.add(newMove);
                    }
                }
            }
            // Double Move (x, y + 2) when y = 2
            if (y == 2 && x <= 8) {
                ChessPosition doubleCheck = new ChessPosition(y + 1, x);
                ChessPiece doubleCheckPiece = board.getPiece(doubleCheck);
                ChessPosition newPos = new ChessPosition(y + 2, x);
                ChessPiece newPiece = board.getPiece(newPos);
                ChessMove newMove = new ChessMove(piecePosition, newPos, null);
                if (newPiece == null && doubleCheckPiece == null) {
                    validMoves.add(newMove);
                }
            }
        }
        // BLACK PAWN
        if (this.pieceColor == ChessGame.TeamColor.BLACK) {
            // Forward Move (x, y - 1)
            if (x <= 8 && y - 1 >= 1) {
                ChessPosition newPos = new ChessPosition(y - 1, x);
                ChessPiece newPiece = board.getPiece(newPos);
                ChessMove newMove = new ChessMove(piecePosition, newPos, null);
                // Promotion logic
                if (y - 1 == 1) {
                    validMoves.addAll(Arrays.asList(
                            new ChessMove(piecePosition, newPos, PieceType.ROOK),
                            new ChessMove(piecePosition, newPos, PieceType.KNIGHT),
                            new ChessMove(piecePosition, newPos, PieceType.BISHOP),
                            new ChessMove(piecePosition, newPos, PieceType.QUEEN))
                    );
                } else if (newPiece == null) {
                    validMoves.add(newMove);
                }
            }
            // Left Capture (x - 1, y - 1)
            if (x - 1 >= 1 && y - 1 >= 1) {
                ChessPosition newPos = new ChessPosition(y - 1, x - 1);
                ChessPiece newPiece = board.getPiece(newPos);
                ChessMove newMove = new ChessMove(piecePosition, newPos, null);
                if (newPiece != null && newPiece.pieceColor != this.pieceColor) {
                    if (y - 1 == 1) {
                        validMoves.addAll(Arrays.asList(
                                new ChessMove(piecePosition, newPos, PieceType.ROOK),
                                new ChessMove(piecePosition, newPos, PieceType.KNIGHT),
                                new ChessMove(piecePosition, newPos, PieceType.BISHOP),
                                new ChessMove(piecePosition, newPos, PieceType.QUEEN))
                        );
                    } else {
                        validMoves.add(newMove);
                    }
                }
            }
            // Right Capture (x + 1, y - 1)
            if (x + 1 <= 8 && y - 1 >= 1) {
                ChessPosition newPos = new ChessPosition(y - 1, x + 1);
                ChessPiece newPiece = board.getPiece(newPos);
                ChessMove newMove = new ChessMove(piecePosition, newPos, null);
                if (newPiece != null && newPiece.pieceColor != this.pieceColor) {
                    if (y - 1 == 1) {
                        validMoves.addAll(Arrays.asList(
                                new ChessMove(piecePosition, newPos, PieceType.ROOK),
                                new ChessMove(piecePosition, newPos, PieceType.KNIGHT),
                                new ChessMove(piecePosition, newPos, PieceType.BISHOP),
                                new ChessMove(piecePosition, newPos, PieceType.QUEEN))
                        );
                    } else {
                        validMoves.add(newMove);
                    }
                }
            }
            // Double Move (x, y - 2) when y = 7
            if (y == 7 && x <= 8) {
                ChessPosition doubleCheck = new ChessPosition(y - 1, x);
                ChessPiece doubleCheckPiece = board.getPiece(doubleCheck);
                ChessPosition newPos = new ChessPosition(y - 2, x);
                ChessPiece newPiece = board.getPiece(newPos);
                ChessMove newMove = new ChessMove(piecePosition, newPos, null);
                if (newPiece == null && doubleCheckPiece == null) {
                    validMoves.add(newMove);
                }
            }
        }

        // Forward Move
        // return validMoves
        System.out.println(validMoves);
        return validMoves;
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
        String returnString = "";
        if (pieceColor == ChessGame.TeamColor.WHITE && type == PieceType.PAWN) {
            returnString += "P";
        } else if (pieceColor == ChessGame.TeamColor.WHITE && type == PieceType.ROOK) {
            returnString += "R";
        } else if (pieceColor == ChessGame.TeamColor.WHITE && type == PieceType.KNIGHT) {
            returnString += "N";
        } else if (pieceColor == ChessGame.TeamColor.WHITE && type == PieceType.BISHOP) {
            returnString += "B";
        } else if (pieceColor == ChessGame.TeamColor.WHITE && type == PieceType.QUEEN) {
            returnString += "Q";
        } else if (pieceColor == ChessGame.TeamColor.WHITE && type == PieceType.KING) {
            returnString += "K";
        } else if (pieceColor == ChessGame.TeamColor.BLACK && type == PieceType.PAWN) {
            returnString += "p";
        } else if (pieceColor == ChessGame.TeamColor.BLACK && type == PieceType.ROOK) {
            returnString += "r";
        } else if (pieceColor == ChessGame.TeamColor.BLACK && type == PieceType.KNIGHT) {
            returnString += "n";
        } else if (pieceColor == ChessGame.TeamColor.BLACK && type == PieceType.BISHOP) {
            returnString += "b";
        } else if (pieceColor == ChessGame.TeamColor.BLACK && type == PieceType.QUEEN) {
            returnString += "q";
        } else if (pieceColor == ChessGame.TeamColor.BLACK && type == PieceType.KING) {
            returnString += "k";
        } else {
            returnString += " ";
        }
        returnString += "|";
        return returnString;
    }
}
