package service;

import dataaccess.AuthDatabaseDAO;
import dataaccess.GameDatabaseDAO;
import dataaccess.UserDatabaseDAO;
import response.EmptyResponse;

public class ClearService {
    public EmptyResponse Clear() throws Exception {
        AuthDatabaseDAO aDAO = new AuthDatabaseDAO();
        GameDatabaseDAO gDAO = new GameDatabaseDAO();
        UserDatabaseDAO uDAO = new UserDatabaseDAO();
        try {
            aDAO.ClearAuth();
            gDAO.ClearGames();
            uDAO.ClearUsers();
            return new EmptyResponse();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
