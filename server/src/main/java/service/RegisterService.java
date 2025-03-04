package service;

import dataaccess.UserDAO;
import dataaccess.UserDatabaseDAO;
import exceptions.BadRequestException;
import exceptions.UsernameUnavailableException;
import model.UserData;
import request.RegisterRequest;
import response.RegisterResponse;

import java.util.List;
import java.util.Objects;


public class RegisterService {

    UserDAO testDAO = new UserDatabaseDAO(List.of(
            new UserData("Taymyth", "bubbles", "ianjellsworth@gmail.com"),
            new UserData("TaylorSmith", "BUBBLES", "bubbles@gmail.com")
            ));


    public Boolean goodRequest(RegisterRequest registerRequest) {
        return !Objects.equals(registerRequest.getUsername(), "") &&
               !Objects.equals(registerRequest.getPassword(), "") &&
               !Objects.equals(registerRequest.getEmail(), "");
    }

    public Boolean usernameUnavailable(RegisterRequest registerRequest) {
        return testDAO.userExists(registerRequest.getUsername());
    }


    public RegisterResponse register(RegisterRequest registerRequest) throws Exception {

        // is the request good?
        if (!goodRequest(registerRequest)) {
            throw new BadRequestException("Error: bad request");
        }

        // is username taken?
        if (usernameUnavailable(registerRequest)) {
            throw new UsernameUnavailableException("Error: already taken");
        }

        try {
            // Try to do the thing with help from UserDAO
            String username = registerRequest.getUsername();
            String password = registerRequest.getPassword();
            String usermail = registerRequest.getEmail();

            UserData data = new UserData(username, password, usermail);

            System.out.println(username);
            System.out.println(password);
            System.out.println(usermail);

            String ex = AuthTokenGenerator.generateToken();

            return new RegisterResponse(username, ex, 200);
        } catch (Exception e) {
            throw new Exception("Error: " + e.getMessage());
        }
    }
}
