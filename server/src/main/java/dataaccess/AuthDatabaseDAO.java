package dataaccess;

import com.google.gson.reflect.TypeToken;
import model.AuthData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AuthDatabaseDAO implements AuthDAO {

    String authPath = "C:/Users/IanJE/Documents/byu_cs/cs240/chess/server/src/main/resources/dataaccessEx/exAuthDataBase.json";
    public List<AuthData> authDataList;

    public AuthDatabaseDAO(List<AuthData> authDataList) {
        this.authDataList = authDataList;
    }

    public AuthDatabaseDAO() {
        this.authDataList = exDBReader.readListFromFile(authPath, new TypeToken<List<AuthData>>() {});
    }

    @Override
    public boolean AuthExists(String authToken) {
        for (AuthData data: authDataList) {
            if (Objects.equals(data.authToken(), authToken)) {
                return true;
            }
        }
        return false;
    }

     @Override
    public void StoreAuth(AuthData authData) {
        if (RetrieveAuth(authData.authToken()) == null) {
            authDataList.add(authData);
            exDBReader.writeListToFile(authPath, authDataList);
        }
     }

    @Override
    public boolean AuthenticateAuth(String authToken) {
        for (AuthData data: authDataList) {
            if (Objects.equals(data.authToken(), authToken)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public AuthData RetrieveAuth(String authToken) {
        for (AuthData data: authDataList) {
            if (Objects.equals(data.authToken(), authToken)) {
                return data;
            }
        }
        return null;
    }

    @Override
    public void DeleteAuth(String authToken) {
        for (AuthData data: authDataList) {
            if (Objects.equals(data.authToken(), authToken)) {
                authDataList.remove(data);
                exDBReader.writeListToFile(authPath, authDataList);
                return;
            }
        }
    }
    @Override
    public void ClearAuth() {
        authDataList = new ArrayList<>();
        exDBReader.writeListToFile(authPath, authDataList);
    }
}
