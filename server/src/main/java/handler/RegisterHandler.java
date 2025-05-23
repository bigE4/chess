package handler;

import com.google.gson.Gson;
import exceptions.BadRequestException;
import exceptions.AlreadyTakenException;
import records.ErrorResponse;
import service.RegisterService;
import records.RegisterRequest;
import records.RegisterResponse;
import spark.Request;
import spark.Response;

public class RegisterHandler implements spark.Route {

    private final Gson gson = new Gson();

    @Override
    public Object handle(Request request, Response response) {
        System.out.println(" --- Handling a Register request");
        System.out.println(request.headers());
        System.out.println(request.body());
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
        } catch (BadRequestException badRequestException) {
            response.type("application/json");
            response.status(400);
            ErrorResponse errorResponse = new ErrorResponse("Error: bad request");
            return gson.toJson(errorResponse);
        } catch (AlreadyTakenException alreadyTaken) {
            response.type("application/json");
            response.status(403);
            ErrorResponse errorResponse = new ErrorResponse("Error: already taken");
            return gson.toJson(errorResponse);
        } catch (Exception e) {
            response.type("application/json");
            response.status(500);
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return gson.toJson(errorResponse);
        }
    }
}
