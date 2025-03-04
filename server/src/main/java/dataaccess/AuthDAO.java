package dataaccess;

import model.AuthData;

public interface AuthDAO {
    boolean AuthExists(String inToken);
    boolean StoreAuth(AuthData inData);
    boolean AuthenticateAuth(String inToken);
    AuthData RetrieveAuth(String inToken);
    boolean DeleteAuth(String inToken);
    void ClearAuth();
}
