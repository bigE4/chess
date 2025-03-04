package dataaccess;

import model.AuthData;

public interface AuthDAO {
    boolean AuthExists(String authToken);
    boolean StoreAuth(AuthData authData);
    boolean AuthenticateAuth(String authToken);
    AuthData RetrieveAuth(String authToken);
    boolean DeleteAuth(String authToken);
    void ClearAuth();
}
