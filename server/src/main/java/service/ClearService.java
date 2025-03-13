package service;

import dataaccess.*;
import response.EmptyResponse;

public class ClearService {
    public EmptyResponse clear() throws Exception {
        AuthDAO aDAO = new AuthSQLDAO();
        GameDAO gDAO = new GameSQLDAO();
        UserDAO uDAO = new UserSQLDAO();
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
