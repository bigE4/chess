package dataaccess;

import com.google.gson.reflect.TypeToken;
import model.UserData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDatabaseDAO implements UserDAO {
    String userPath = "C:/Users/IanJE/Documents/byu_cs/cs240/chess/server/src/main/resources/dataaccessEx/exUserDataBase.json";
    public List<UserData> userDataList;

    public UserDatabaseDAO(List<UserData> userDataList) {
        this.userDataList = userDataList;
    }

    public UserDatabaseDAO() {
        this.userDataList = exDBReader.readListFromFile(userPath, new TypeToken<List<UserData>>() {});
        System.out.println("User Database Init: " + userDataList);
    }

    @Override
    public boolean UserExists(String username) {
        for (UserData data: userDataList) {
            if (Objects.equals(data.username(), username)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean StoreUser(UserData userData) {
        if (!UserExists(userData.username())) {
            userDataList.add(userData);
            exDBReader.writeListToFile(userPath, userDataList);
            return true;
        }
        return false;
    }

    @Override
    public boolean UpdateUser(UserData userData) {
        for (UserData data: userDataList) {
            if (Objects.equals(data.username(), userData.username())) {
                DeleteUser(userData.username());
                StoreUser(userData);
                exDBReader.writeListToFile(userPath, userDataList);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean AuthenticateUser(String username, String password) {
        for (UserData data: userDataList) {
            if (Objects.equals(data.username(), username) && Objects.equals(data.password(), password)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public UserData RetrieveUser(String username) {
        for (UserData data: userDataList) {
            if (Objects.equals(data.username(), username)) {
                return data;
            }
        }
        return null;
    }

    @Override
    public List<UserData> RetrieveUsers() {
        return userDataList;
    }

    @Override
    public boolean DeleteUser(String username) {
        for (UserData data: userDataList) {
            if (Objects.equals(data.username(), username)) {
                userDataList.remove(data);
                exDBReader.writeListToFile(userPath, userDataList);
                return true;
            }
        }
        return false;
    }

    @Override
    public void ClearUsers() {
        userDataList = new ArrayList<>();
        exDBReader.writeListToFile(userPath, userDataList);
    }
}
