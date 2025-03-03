package dataaccess;

import model.AuthData;

public interface AuthDAO {
    boolean isAuth(String authToken, String username);
    boolean storeAuth(String authToken, String username);
    boolean authenticateAuth(String authToken, String username);
    AuthData retrieveAuth(String authToken);
    boolean deleteAuth(String authToken);
    void clearAuth();
}
