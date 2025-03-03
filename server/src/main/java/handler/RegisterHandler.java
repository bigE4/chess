package handler;

import com.google.gson.Gson;
import service.RegisterService;
import request.RegisterRequest;
import response.RegisterResponse;
import spark.Request;
import spark.Response;
import spark.Route;

public class RegisterHandler implements Route {

    private final Gson gson = new Gson();

    @Override
    public Object handle(Request request, Response response) throws Exception {
        // try to parse the request, call the service, and return the correct response and throw an exception if unsuccessful
        try {
            // Parse the request from the server into a RegisterRequest object
            RegisterRequest registerRequest = gson.fromJson(request.body(), RegisterRequest.class);
            // Call the RegisterService class to do the thing
            RegisterService registerService = new RegisterService();
            RegisterResponse registerResponse = registerService.register(registerRequest);
            // Set successful response
            response.type("application/json");
            response.status(200);
            return gson.toJson(registerResponse);
        } catch (Exception e) {
            response.type("application/json");
            response.status(500);
            return gson.toJson(e.getMessage());
        }
    }
}
