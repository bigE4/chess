package model;

import chess.ChessGame;
import chess.ChessMove;

import java.util.List;

public record GameData(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game, List<ChessMove> moves) {
}
