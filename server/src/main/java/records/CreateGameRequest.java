package records;

public record CreateGameRequest(String authToken, String gameName) implements AuthRequest {
    public CreateGameRequest(String authToken, CreateGameRequest request) {
        this(authToken, request.gameName());
    }
}
