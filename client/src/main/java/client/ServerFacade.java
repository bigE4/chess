package client;

import com.google.gson.Gson;
import records.REPLToken;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class ServerFacade {
    private static String serverUrl;

    public ServerFacade(String url) {
        serverUrl = url;
    }

    public HttpURLConnection register(String username, String password, String email) throws Exception {
        RegisterRequest request = new RegisterRequest(username, password, email);
        return makeRequest("POST", "/user", request);
    }

    public HttpURLConnection login(String username, String password) throws Exception {
        LoginRequest request = new LoginRequest(username, password);
        return makeRequest("POST", "/session", request);
    }

    public HttpURLConnection create(String gameName, REPLToken authToken) throws Exception {
        CreateRequest request = new CreateRequest(gameName, authToken.authToken);
        return makeRequest("POST", "/game", null);
    }

    public HttpURLConnection list(REPLToken authToken) throws Exception {
        return makeRequest("GET", "/game", null);
    }

    public HttpURLConnection play(String color, int gameID, REPLToken authToken) throws Exception {
        JoinRequest request = new JoinRequest(color, gameID, authToken.authToken);
        System.out.println(gameID + 5);
        return makeRequest("PUT", "/game", null);

    }

    public HttpURLConnection logout() throws Exception {
        var path = "/session";
        return makeRequest("DELETE", path, null);
    }

    public HttpURLConnection makeRequest(String method, String path, Object request) throws Exception {
         try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);
            writeBody(request, http);
            http.connect();
            return http;
        } catch (Exception e) {
             throw new RuntimeException(e);
         }
    }

    private void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String requestData = new Gson().toJson(request);
            try (OutputStream requestBody = http.getOutputStream()) {
                requestBody.write(requestData.getBytes());
            }
        } else {
            http.addRequestProperty("Content-Type", "application/json");
            try (OutputStream requestBody = http.getOutputStream()) {
                requestBody.write("{}".getBytes());
            }
        }
    }

    public static record LoginRequest(String username, String password) {}

    public static record RegisterRequest(String username, String password, String email) {}

    public static record CreateRequest(String gameName, String authToken) {}

    public static record JoinRequest(String playerColor, int gameID, String authToken) {}

    public static record AuthResponse(String username, String authToken) {}

}
