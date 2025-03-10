package dataaccess;

import com.google.gson.reflect.TypeToken;
import model.AuthData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AuthDatabaseDAO implements AuthDAO {

    String authPath = "src\\main\\java\\dataaccess\\exampledatabase\\exAuthDataBase.json";
    public List<AuthData> authDataList;

    public AuthDatabaseDAO() {
        this.authDataList = ExampleDatabaseReader.readListFromFile(authPath, new TypeToken<List<AuthData>>() {});
    }

     @Override
    public void storeAuth(AuthData authData) {
        if (retrieveAuth(authData.authToken()) == null) {
            authDataList.add(authData);
            ExampleDatabaseReader.writeListToFile(authPath, authDataList);
        }
     }

    @Override
    public boolean authenticateAuth(String authToken) {
        for (AuthData data: authDataList) {
            if (Objects.equals(data.authToken(), authToken)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public AuthData retrieveAuth(String authToken) {
        for (AuthData data: authDataList) {
            if (Objects.equals(data.authToken(), authToken)) {
                return data;
            }
        }
        return null;
    }

    @Override
    public void deleteAuth(String authToken) {
        for (AuthData data: authDataList) {
            if (Objects.equals(data.authToken(), authToken)) {
                authDataList.remove(data);
                ExampleDatabaseReader.writeListToFile(authPath, authDataList);
                return;
            }
        }
    }
    @Override
    public void clearAuth() {
        authDataList = new ArrayList<>();
        ExampleDatabaseReader.writeListToFile(authPath, authDataList);
    }
}
