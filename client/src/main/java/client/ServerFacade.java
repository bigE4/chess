package client;

import com.google.gson.Gson;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class ServerFacade {
    private static String serverUrl;

    public ServerFacade(String url) {
        serverUrl = url;
    }

    public HttpURLConnection register(String username, String password, String email) throws Exception {
        RegisterRequest request = new RegisterRequest(username, password, email);
        return makeRequest("POST", "/user", request, null);
    }

    public HttpURLConnection login(String username, String password) throws Exception {
        LoginRequest request = new LoginRequest(username, password);
        return makeRequest("POST", "/session", request, null);
    }

    public HttpURLConnection list(ServerFacade facade, String authToken) throws Exception {
        return makeRequest("GET", "/game", null, authToken);
    }

    public HttpURLConnection create(String gameName, String authToken) throws Exception {
        CreateRequest request = new CreateRequest(gameName);
        return makeRequest("POST", "/game", request, authToken);
    }

    public HttpURLConnection join(String authToken) throws Exception {

        return makeRequest("PUT", "/game", null, authToken);
    }

    private HttpURLConnection makeRequest(String method, String path, Object request, String authToken) throws Exception {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);
            if (authToken != null) {
                http.addRequestProperty("Authorization", authToken);
            }
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
        }
    }

    public static record LoginRequest(String username, String password) {}

    public static record RegisterRequest(String username, String password, String email) {}

    public static record CreateRequest(String gameName) {}

    public static record AuthResponse(String username, String authToken) {}

    public static record Game(int gameID, String gameName) {};

    public static record ListResponse(List<Game> games) {}

}
