package dataaccess;

import com.google.gson.reflect.TypeToken;
import model.UserData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDatabaseDAO implements UserDAO {

    String userPath = "src\\main\\java\\dataaccess\\exampledatabase\\exUserDataBase.json";
    public List<UserData> userDataList;

    public UserDatabaseDAO() {
        this.userDataList = ExampleDatabaseReader.readListFromFile(userPath, new TypeToken<List<UserData>>() {});
    }

    @Override
    public boolean userExists(String username) {
        for (UserData data: userDataList) {
            if (Objects.equals(data.username(), username)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void storeUser(UserData userData) {
        if (!userExists(userData.username())) {
            userDataList.add(userData);
            ExampleDatabaseReader.writeListToFile(userPath, userDataList);
        }
    }

    @Override
    public boolean authenticateUser(String username, String password) {
        for (UserData data: userDataList) {
            if (Objects.equals(data.username(), username) && Objects.equals(data.password(), password)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void clearUsers() {
        userDataList = new ArrayList<>();
        ExampleDatabaseReader.writeListToFile(userPath, userDataList);
    }
}
