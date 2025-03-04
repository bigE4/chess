package response;

public record RegisterResponse(String username, String authToken) {

    @Override
    public String toString() {
        return "RegisterResponse{" +
                "username='" + username + '\'' +
                ", authToken='" + authToken + '\'' +
                '}';
    }
}
