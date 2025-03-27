package client;

import com.google.gson.Gson;

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
        return makeRequest("POST", "/user", request, null);
    }

    public HttpURLConnection login(String username, String password) throws Exception {
        LoginRequest request = new LoginRequest(username, password);
        return makeRequest("POST", "/session", request, null);
    }

    public HttpURLConnection list(ServerFacade facade, String authToken) throws Exception {
        System.out.println(authToken);
        return makeRequest("GET", "/game", null, authToken);
    }

    public HttpURLConnection create() throws Exception {
        return makeRequest("POST", "/game", null, null);
    }

    public HttpURLConnection join() throws Exception {
        return makeRequest("PUT", "/game", null, null);
    }

    private HttpURLConnection makeRequest(String method, String path, Object request, String authToken) throws Exception {
        System.out.println("Making the request");
        System.out.println(method);
        System.out.println(path);
        System.out.println(request);
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);
            writeBody(request, http);
            if (authToken != null) {
                http.addRequestProperty("Authorization", authToken);
            }
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

    public static record AuthResponse(String username, String authToken) {}

}
