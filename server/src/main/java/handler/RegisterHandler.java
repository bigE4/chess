package handler;

import com.google.gson.Gson;
import service.RegisterService;
import request.RegisterRequest;
import response.RegisterResponse;
import spark.Request;
import spark.Response;
import spark.Route;

public class RegisterHandler implements Route {

    private final Gson gson = new Gson();

    @Override
    public Object handle(Request request, Response response) throws Exception {
        RegisterRequest registerRequest = gson.fromJson(request.body(), RegisterRequest.class);
        return null;
    }
}
