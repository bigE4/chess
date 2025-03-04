package handler;

import com.google.gson.Gson;
import exceptions.AlreadyTakenException;
import exceptions.BadRequestException;
import exceptions.UnauthorizedException;
import request.ListGamesRequest;
import response.ErrorResponse;
import response.ListGamesResponse;
import service.ListGamesService;
import spark.Request;
import spark.Response;

public class CreateGameHandler implements spark.Route {

    private final Gson gson = new Gson();

    @Override
    public Object handle(Request request, Response response) {
        try {
            // Implement handle here
            return null;
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
