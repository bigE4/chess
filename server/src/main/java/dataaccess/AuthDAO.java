package dataaccess;

import model.AuthData;

public interface AuthDAO {
    boolean AuthExists(String authToken);
    void StoreAuth(AuthData authData);
    boolean AuthenticateAuth(String authToken);
    AuthData RetrieveAuth(String authToken);
    void DeleteAuth(String authToken);
    void ClearAuth();
}
