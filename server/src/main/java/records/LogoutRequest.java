package records;

public record LogoutRequest(String authToken) implements AuthRequest {
}
