package response;

public class RegisterResponse {
    private String username;
    private String authToken;
    private int statusCode;

    public RegisterResponse(String username, String authToken, int statusCode) {
        this.username = username;
        this.authToken = authToken;
        this.statusCode = statusCode;
    }

    public String getUsername() {
        return username;
    }

    public String getAuthToken() {
        return authToken;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
