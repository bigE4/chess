package handler;

import com.google.gson.Gson;
import exceptions.AlreadyTakenException;
import exceptions.BadRequestException;
import exceptions.UnauthorizedException;
import records.JoinGameRequest;
import records.EmptyResponse;
import records.ErrorResponse;
import service.JoinGameService;
import spark.Request;
import spark.Response;

public class JoinGameHandler implements spark.Route {

    private final Gson gson = new Gson();

    @Override
    public Object handle(Request request, Response response) {
        System.out.println(" --- Handling a Join request");
        System.out.println(request.headers());
        System.out.println(request.body());
        try {
            String authToken = request.headers("authorization");
            JoinGameRequest body = gson.fromJson(request.body(), JoinGameRequest.class);
            JoinGameRequest joinGameRequest = new JoinGameRequest(authToken, body);
            JoinGameService joinGameService = new JoinGameService();
            EmptyResponse emptyResponse = joinGameService.joinGame(joinGameRequest);
            response.type("application/json");
            response.status(200);
            return gson.toJson(emptyResponse);
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
        } catch (AlreadyTakenException e) {
            response.type("application/json");
            response.status(403);
            ErrorResponse errorResponse = new ErrorResponse("Error: already taken");
            return gson.toJson(errorResponse);
        }catch (Exception e) {
            response.type("application/json");
            response.status(500);
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return gson.toJson(errorResponse);
        }
    }
}
