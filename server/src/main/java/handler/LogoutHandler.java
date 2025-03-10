package handler;

import com.google.gson.Gson;
import exceptions.UnauthorizedException;
import request.LogoutRequest;
import response.ErrorResponse;
import response.EmptyResponse;
import service.LogoutService;
import spark.Request;
import spark.Response;

public class LogoutHandler implements spark.Route {

    private final Gson gson = new Gson();

    @Override
    public Object handle(Request request, Response response) {
        try {
            String authToken = request.headers("authorization");
            LogoutRequest logoutRequest = new LogoutRequest(authToken);
            LogoutService logoutService = new LogoutService();
            EmptyResponse logoutResponse = logoutService.logout(logoutRequest);
            response.type("application/json");
            response.status(200);
            return gson.toJson(logoutResponse);
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
