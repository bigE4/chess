package service;

import exceptions.BadRequestException;
import exceptions.UsernameUnavailableException;
import request.RegisterRequest;
import response.RegisterResponse;

import java.util.Objects;


public class RegisterService {

    public Boolean goodRequest(RegisterRequest registerRequest) {
        return !Objects.equals(registerRequest.getUsername(), "") &&
               !Objects.equals(registerRequest.getPassword(), "") &&
               !Objects.equals(registerRequest.getEmail(), "");
    }

    public Boolean usernameNotTaken(RegisterRequest registerRequest) {
        return !Objects.equals(registerRequest.getUsername(), "");
    }


    public RegisterResponse register(RegisterRequest registerRequest) throws Exception {
        // is the request good?
        if (!goodRequest(registerRequest)) {
            throw new BadRequestException("Error: bad request");
        }
        // is username taken?
        if (!usernameNotTaken(registerRequest)) {
            throw new UsernameUnavailableException("Error: already taken");
        }
        try {
            // Try to do the thing with help from UserDAO
            String username = registerRequest.getUsername();
            String password = registerRequest.getPassword();
            String usermail = registerRequest.getEmail();


            System.out.println(username);
            System.out.println(password);
            System.out.println(usermail);
            return new RegisterResponse(username, "exampleToken", 200);
        } catch (Exception e) {
            throw new Exception("Error: " + e.getMessage());
        }
    }
}
