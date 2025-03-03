package dataaccess;

import model.AuthData;

public class AuthDatabaseDAO implements AuthDAO {
    @Override
    public boolean isAuth(String authToken, String username) {
        return false;
    }

    @Override
    public boolean storeAuth(String authToken, String username) {
        return false;
    }

    @Override
    public boolean authenticateAuth(String authToken, String username) {
        return false;
    }

    @Override
    public AuthData retrieveAuth(String authToken) {
        return null;
    }

    @Override
    public boolean deleteAuth(String authToken) {
        return false;
    }

    @Override
    public void clearAuth() {

    }
}
