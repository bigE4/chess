package request;

public record ListGamesRequest(String authToken) implements AuthRequest {
}
