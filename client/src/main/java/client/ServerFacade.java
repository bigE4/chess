package client;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class ServerFacade {
    private static String serverUrl;

    public ServerFacade(String url) {
        serverUrl = url;
    }

    public int register(String username, String password, String email) throws Exception {
        var path = "/user";
        RegisterRequest request = new RegisterRequest(username, password, email);
        return makeRequest("POST", path, request);
    }

    public int login(String username, String password) throws Exception {
        var path = "/session";
        LoginRequest request = new LoginRequest(username, password);
        return makeRequest("POST", path, request);
    }

    public int makeRequest(String method, String path, Object request) throws Exception {
         try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);
            writeBody(request, http);
            http.connect();
            return http.getResponseCode();
        } catch (Exception e) {
             throw new RuntimeException(e);
         }
    }

    private void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private static class LoginRequest {
        String username;
        String password;

        public LoginRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    private static class RegisterRequest {
        String username;
        String password;
        String email;

        public RegisterRequest(String username, String password, String email) {
            this.username = username;
            this.password = password;
            this.email = email;
        }
    }

    private static class CreateRequest {
        String gameName;

        public CreateRequest(String gameName) {
            this.gameName = gameName;
        }
    }

    private static class JoinRequest {
        String playerColor;
        int gameID;

        public JoinRequest(String playerColor, int gameID) {
            this.playerColor = playerColor;
            this.gameID = gameID;
        }
    }
}
