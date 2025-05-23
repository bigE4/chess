package dataaccess.interfaces;

import exceptions.DataAccessException;
import records.UserData;

public interface UserDAO {
    boolean userExists(String username) throws DataAccessException;
    void storeUser(UserData userData) throws DataAccessException;
    boolean authenticateUser(String username, String password) throws DataAccessException;
    void clearUsers() throws DataAccessException;
}
