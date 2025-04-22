package handler;

import com.google.gson.Gson;
import exceptions.UnauthorizedException;
import records.ListGamesRequest;
import records.ErrorResponse;
import records.ListGamesResponse;
import service.ListGamesService;
import spark.Request;
import spark.Response;

public class ListGamesHandler implements spark.Route {

    private final Gson gson = new Gson();

    @Override
    public Object handle(Request request, Response response) {
        System.out.println(" --- Handling a List request");
        System.out.println(request.headers());
        System.out.println(request.body());
        try {
            String authToken = request.headers("authorization");
            ListGamesRequest listGamesRequest = new ListGamesRequest(authToken);
            ListGamesService listGamesService = new ListGamesService();
            ListGamesResponse listGamesResponse = listGamesService.listGames(listGamesRequest);
            response.type("application/json");
            response.status(200);
            return gson.toJson(listGamesResponse);
        } catch (UnauthorizedException e) {
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
