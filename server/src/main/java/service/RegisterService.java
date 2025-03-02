package service;

import request.RegisterRequest;
import response.RegisterResponse;

import java.util.Objects;


public class RegisterService implements Service {

    String example = "Taymyth";

    public Boolean goodRequest(RegisterRequest registerRequest) {
        return !Objects.equals(registerRequest.getUsername(), "") &&
               !Objects.equals(registerRequest.getPassword(), "") &&
               !Objects.equals(registerRequest.getEmail(), "");
    }

    public Boolean usernameNotTaken(RegisterRequest registerRequest) {
        return !Objects.equals(registerRequest.getUsername(), "Taymyth");
    }


    public RegisterResponse register(RegisterRequest registerRequest) throws Exception {
        // is the request good?
        if (!goodRequest(registerRequest)) {
            throw new Exception("Error: bad request");
        }
        // is username taken?
        if (!usernameNotTaken(registerRequest)) {
            throw new Exception("Error: already taken");
        }
        try {
            // DO THE THINGS!!!
            return new RegisterResponse("exampleUser", "exampleToken", 200);
        } catch (Exception e) {
            throw new Exception("Error: " + e.getMessage());
        }
    }
}
