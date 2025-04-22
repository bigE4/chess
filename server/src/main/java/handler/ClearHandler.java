package handler;

import com.google.gson.Gson;
import records.ErrorResponse;
import records.EmptyResponse;
import service.ClearService;
import spark.Request;
import spark.Response;

public class ClearHandler implements spark.Route {

    private final Gson gson = new Gson();

    @Override
    public Object handle(Request request, Response response) {
        System.out.println(" --- Handling a Clear request");
        System.out.println(request.headers());
        System.out.println(request.body());
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
