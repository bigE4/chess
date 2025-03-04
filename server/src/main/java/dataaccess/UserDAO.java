package dataaccess;

import model.UserData;

import java.util.List;

public interface UserDAO {
    boolean UserExists(String username);
    boolean StoreUser(UserData userData);
    boolean UpdateUser(UserData userData);
    boolean AuthenticateUser(String username, String password);
    UserData RetrieveUser(String username);
    List<UserData> RetrieveUsers();
    boolean DeleteUser(String username);
    void ClearUsers();
}
