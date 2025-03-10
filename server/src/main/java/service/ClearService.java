package service;

import dataaccess.AuthDatabaseDAO;
import dataaccess.GameDatabaseDAO;
import dataaccess.UserDatabaseDAO;
import response.EmptyResponse;

public class ClearService {
    public EmptyResponse clear() throws Exception {
        AuthDatabaseDAO aDAO = new AuthDatabaseDAO();
        GameDatabaseDAO gDAO = new GameDatabaseDAO();
        UserDatabaseDAO uDAO = new UserDatabaseDAO();
        try {
            aDAO.clearAuth();
            gDAO.clearGames();
            uDAO.clearUsers();
            return new EmptyResponse();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
