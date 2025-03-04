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
        System.out.println("Auth Database Init: " + authDataList);
    }

    @Override
    public boolean AuthExists(String inToken) {
        for (AuthData data: authDataList) {
            if (Objects.equals(data.authToken(), inToken)) {
                return true;
            }
        }
        return false;
    }

     @Override
    public boolean StoreAuth(AuthData inData) {
        if (!AuthExists(inData.authToken())) {
            authDataList.add(inData);
            exDBReader.writeListToFile(authPath, authDataList);
            return true;
        }
        return false;
    }

    @Override
    public boolean AuthenticateAuth(String inToken) {
        for (AuthData data: authDataList) {
            if (Objects.equals(data.authToken(), inToken)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public AuthData RetrieveAuth(String inToken) {
        for (AuthData data: authDataList) {
            if (Objects.equals(data.authToken(), inToken)) {
                return data;
            }
        }
        return null;
    }

    @Override
    public boolean DeleteAuth(String inToken) {
        for (AuthData data: authDataList) {
            if (Objects.equals(data.authToken(), inToken)) {
                authDataList.remove(data);
                return true;
            }
        }
        return false;
    }
    @Override
    public void ClearAuth() {
        authDataList = new ArrayList<>();
    }
}
