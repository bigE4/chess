package handler;

import com.google.gson.Gson;
import exceptions.BadRequestException;
import exceptions.UnauthorizedException;
import org.eclipse.jetty.io.EndPoint;
import request.LogoutRequest;
import response.ErrorResponse;
import response.EmptyResponse;
import service.ClearService;
import service.LogoutService;
import spark.Request;
import spark.Response;

public class ClearHandler implements spark.Route {

    private final Gson gson = new Gson();

    @Override
    public Object handle(Request request, Response response) {
        try {
            ClearService clearService = new ClearService();
            EmptyResponse logoutResponse = clearService.clear();
            response.type("application/json");
            response.status(200);
            return gson.toJson(logoutResponse);
        } catch (Exception e) {
            response.type("application/json");
            response.status(500);
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return gson.toJson(errorResponse);
        }
    }
}
