package handler;

import com.google.gson.Gson;
import exceptions.BadRequestException;
import exceptions.UnauthorizedException;
import records.CreateGameRequest;
import records.CreateGameResponse;
import records.ErrorResponse;
import service.CreateGameService;
import spark.Request;
import spark.Response;

public class CreateGameHandler implements spark.Route {

    private final Gson gson = new Gson();

    @Override
    public Object handle(Request request, Response response) {
        System.out.println(" --- Handling a Create request");
        System.out.println(request.headers());
        System.out.println(request.body());        try {
            String authToken = request.headers("authorization");
            CreateGameRequest body = gson.fromJson(request.body(), CreateGameRequest.class);
            CreateGameRequest createGameRequest = new CreateGameRequest(authToken, body);
            CreateGameService createGameService = new CreateGameService();
            CreateGameResponse createGameResponse = createGameService.createGame(createGameRequest);
            response.type("application/json");
            response.status(200);
            return gson.toJson(createGameResponse);
        } catch (BadRequestException e) {
            response.type("application/json");
            response.status(400);
            ErrorResponse errorResponse = new ErrorResponse("Error: bad request");
            return gson.toJson(errorResponse);
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
