package handler;

import com.google.gson.Gson;
import exceptions.BadRequestException;
import exceptions.UnauthorizedException;
import request.ListGamesRequest;
import response.ErrorResponse;
import response.ListGamesResponse;
import service.ListGamesService;
import spark.Request;
import spark.Response;

public class ListGamesHandler implements spark.Route {

    private final Gson gson = new Gson();

    @Override
    public Object handle(Request request, Response response) {
        try {
            String authToken = request.headers("authorization");
            if (authToken == null || authToken.isEmpty()) { throw new BadRequestException("Error: bad request"); }
            ListGamesRequest listGamesRequest = new ListGamesRequest(authToken);
            ListGamesService listGamesService = new ListGamesService();
            ListGamesResponse listGamesResponse = listGamesService.listGames(listGamesRequest);
            response.type("application/json");
            response.status(200);
            return gson.toJson(listGamesResponse);
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
