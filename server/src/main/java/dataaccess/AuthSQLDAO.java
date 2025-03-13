package dataaccess;

import model.AuthData;

public class AuthSQLDAO implements AuthDAO {
    @Override
    public void storeAuth(AuthData authData) {

    }

    @Override
    public boolean authenticateAuth(String authToken) {
        return false;
    }

    @Override
    public AuthData retrieveAuth(String authToken) {
        return null;
    }

    @Override
    public void deleteAuth(String authToken) {

    }

    @Override
    public void clearAuth() {

    }
}
