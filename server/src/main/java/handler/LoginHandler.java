package handler;

import com.google.gson.Gson;
import exceptions.UnauthorizedException;
import records.LoginRequest;
import records.ErrorResponse;
import records.LoginResponse;
import service.LoginService;
import spark.Request;
import spark.Response;

public class LoginHandler implements spark.Route {

    private final Gson gson = new Gson();

    @Override
    public Object handle(Request request, Response response) {
        System.out.println(" --- Handling a Login request");
        System.out.println(request.headers());
        System.out.println(request.body());
        try {
            LoginRequest loginRequest = gson.fromJson(request.body(), LoginRequest.class);
            LoginService loginService = new LoginService();
            LoginResponse loginResponse = loginService.login(loginRequest);
            response.type("application/json");
            response.status(200);
            return gson.toJson(loginResponse);
        } catch (UnauthorizedException unauthorizedException) {
            response.type("application/json");
            response.status(401);
            ErrorResponse errorResponse = new ErrorResponse("Error: unauthorized");
            return gson.toJson(errorResponse);
        } catch (Exception e) {
            response.type("application/json");
            response.status(500);
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return gson.toJson(errorResponse);
        }
    }
}
