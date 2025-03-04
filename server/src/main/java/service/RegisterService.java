package service;

import dataaccess.AuthDatabaseDAO;
import dataaccess.UserDatabaseDAO;
import exceptions.BadRequestException;
import exceptions.UsernameUnavailableException;
import model.UserData;
import request.RegisterRequest;
import response.RegisterResponse;

import java.util.Objects;


public class RegisterService {

    UserDatabaseDAO uDAO = new UserDatabaseDAO();
    AuthDatabaseDAO aDAO = new AuthDatabaseDAO();

        public Boolean badRequest(RegisterRequest registerRequest) {
        return Objects.equals(registerRequest.getUsername(), "") &&
               Objects.equals(registerRequest.getPassword(), "") &&
               Objects.equals(registerRequest.getEmail(), "");
    }

    public Boolean usernameUnavailable(RegisterRequest registerRequest) {
        return uDAO.userExists(registerRequest.getUsername());
    }


    public RegisterResponse register(RegisterRequest registerRequest) throws Exception {

        // bad request?
        if (badRequest(registerRequest)) {
            throw new BadRequestException("Error: bad request");
        }

        // already taken?
        if (usernameUnavailable(registerRequest)) {
            throw new UsernameUnavailableException("Error: already taken");
        }

        try {
            // Try to do the thing with help from UserDAO
            String username = registerRequest.getUsername();
            String password = registerRequest.getPassword();
            String usermail = registerRequest.getEmail();

            UserData data = new UserData(username, password, usermail);

            System.out.println("New User Data: " + data);

            uDAO.storeUser(username, password, usermail);
            System.out.println("User Stored!");

            String token = AuthTokenGenerator.generateToken();
            System.out.println("Token Generated! " + token);

            aDAO.storeAuth(token, username);
            System.out.println("AuthData Stored");

            return new RegisterResponse(username, token);

        } catch (Exception e) {
            throw new Exception("Error: " + e.getMessage());
        }
    }
}
