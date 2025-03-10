package dataaccess;

import model.UserData;

public interface UserDAO {
    boolean userExists(String username);
    void storeUser(UserData userData);
    boolean authenticateUser(String username, String password);
    void clearUsers();
}
