package response;

public class RegisterResponse {
    private String username;
    private String authToken;

    public RegisterResponse(String username, String authToken) {
        this.username = username;
        this.authToken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public String getAuthToken() {
        return authToken;
    }

    @Override
    public String toString() {
        return "RegisterResponse{" +
                "username='" + username + '\'' +
                ", authToken='" + authToken + '\'' +
                '}';
    }
}
