package request;

public record JoinGameRequest(String authToken, String playerColor, int gameID) implements AuthRequest {
    public JoinGameRequest(String authToken, JoinGameRequest request) {
        this(authToken, request.playerColor(), request.gameID());
    }
}
