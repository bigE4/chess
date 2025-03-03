package dataaccess;

import model.UserData;

public interface UserDAO {
    boolean isUser(String username);
    boolean storeUser(String username, String password, String email);
    boolean updateUser(String username, String newPassword, String email);
    boolean authenticateUser(String username, String password);
    UserData retrieveUser(String username);
    boolean deleteUser(String username);
    void clearUsers();
}
