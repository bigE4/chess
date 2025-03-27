package client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Map;

public class ServerFacade {
    private static String serverUrl;

    public ServerFacade(String url) {
        serverUrl = url;
    }

    public HttpURLConnection register(String username, String password, String email) throws Exception {
        var path = "/user";
        RegisterRequest request = new RegisterRequest(username, password, email);
        return makeRequest("POST", path, request);
    }

    public HttpURLConnection login(String username, String password) throws Exception {
        var path = "/session";
        LoginRequest request = new LoginRequest(username, password);
        return makeRequest("POST", path, request);
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
        }
    }

    public static record LoginRequest(String username, String password) {}

    public static record RegisterRequest(String username, String password, String email) {}

    public static record CreateRequest(String gameName) {}

    public static record JoinRequest(String playerColor, int gameID) {}

    public static record AuthResponse(String username, String authToken) {}

}
