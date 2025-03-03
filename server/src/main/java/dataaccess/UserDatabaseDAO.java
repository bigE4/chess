package dataaccess;

import model.UserData;

public class UserDatabaseDAO implements UserDAO {
    @Override
    public boolean isUser(String username) {
        return false;
    }

    @Override
    public boolean storeUser(String username, String password, String email) {
        return false;
    }

    @Override
    public boolean updateUser(String username, String newPassword, String email) {
        return false;
    }

    @Override
    public boolean authenticateUser(String username, String password) {
        return false;
    }

    @Override
    public UserData retrieveUser(String username) {
        return null;
    }

    @Override
    public boolean deleteUser(String username) {
        return false;
    }

    @Override
    public void clearUsers() {

    }
}
