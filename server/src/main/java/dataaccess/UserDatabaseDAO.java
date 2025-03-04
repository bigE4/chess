package dataaccess;

import model.UserData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDatabaseDAO implements UserDAO {

    public List<UserData> userDataList = new ArrayList<>();

    public UserDatabaseDAO(List<UserData> userDataList) {
        this.userDataList = userDataList;
    }

    public UserDatabaseDAO() {

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
    public boolean storeUser(String username, String password, String email) {
        if (!userExists(username)) {
            userDataList.add(new UserData(username, password, email));
            return true;
        }
        return false;
    }

    @Override
    public boolean updateUser(String username, String newPassword, String newEmail) {
        for (UserData data: userDataList) {
            if (Objects.equals(data.username(), username)) {
                deleteUser(username);
                storeUser(username, newPassword, newEmail);
                return true;
            }
        }
        return false;
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
    public UserData retrieveUser(String username) {
        for (UserData data: userDataList) {
            if (Objects.equals(data.username(), username)) {
                return data;
            }
        }
        return null;
    }

    @Override
    public List<UserData> retrieveUsers() {
        return userDataList;
    }

    @Override
    public boolean deleteUser(String username) {
        for (UserData data: userDataList) {
            if (Objects.equals(data.username(), username)) {
                userDataList.remove(data);
                return true;
            }
        }
        return false;
    }

    @Override
    public void clearUsers() {
        userDataList = new ArrayList<>();
    }
}
