package dataaccess;

import model.AuthData;

public interface AuthDAO {
    boolean AuthExists(String inToken);
    boolean StoreAuth(AuthData inData);
    boolean AuthenticateAuth(AuthData inData);
    AuthData RetrieveAuth(String inToken);
    boolean DeleteAuth(String inToken);
    void ClearAuth();
}
