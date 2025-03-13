package dataaccess;

import exceptions.DataAccessException;
import model.AuthData;

public interface AuthDAO {
    void storeAuth(AuthData authData) throws DataAccessException;
    boolean authenticateAuth(String authToken) throws DataAccessException;
    AuthData retrieveAuth(String authToken) throws DataAccessException;
    void deleteAuth(String authToken) throws DataAccessException;
    void clearAuth() throws DataAccessException;
}
