package dataaccess;

import exceptions.DataAccessException;
import model.UserData;

public interface UserDAO {
    boolean userExists(String username) throws DataAccessException;
    void storeUser(UserData userData) throws DataAccessException;
    boolean authenticateUser(String username, String password) throws DataAccessException;
    void clearUsers() throws DataAccessException;
}
