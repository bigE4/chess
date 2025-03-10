package dataaccess;

import model.AuthData;

public interface AuthDAO {
    void storeAuth(AuthData authData);
    boolean authenticateAuth(String authToken);
    AuthData retrieveAuth(String authToken);
    void deleteAuth(String authToken);
    void clearAuth();
}
