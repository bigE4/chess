package dataaccess;

import model.UserData;

public class UserSQLDAO implements UserDAO {

    @Override
    public boolean userExists(String username) {
        return false;
    }

    @Override
    public void storeUser(UserData userData) {

    }

    @Override
    public boolean authenticateUser(String username, String password) {
        return false;
    }

    @Override
    public void clearUsers() {

    }
}
