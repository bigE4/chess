package dataaccess;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import model.UserData;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class exDBReader {

    // Create a Gson instance with pretty printing enabled
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Method to write a single UserData object to a JSON file with pretty print
    public static void writeUserDataToFile(String filePath, UserData userData) {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(userData, writer);
        } catch (IOException e) {
            System.err.println("Error writing UserData to file: " + e.getMessage());
        }
    }

    // Method to read a single UserData object from a JSON file
    public static UserData readUserDataFromFile(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, UserData.class);
        } catch (IOException | JsonSyntaxException e) {
            System.err.println("Error reading UserData from file: " + e.getMessage());
            return null;
        }
    }

    // Method to write a list of UserData objects to a JSON file with pretty print
    public static void writeUserDataListToFile(String filePath, List<UserData> userDataList) {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(userDataList, writer);
        } catch (IOException e) {
            System.err.println("Error writing UserData list to file: " + e.getMessage());
        }
    }

    // Method to read a list of UserData objects from a JSON file (with type safety)
    public static List<UserData> readUserDataListFromFile(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            Type listType = new TypeToken<List<UserData>>(){}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException | JsonSyntaxException e) {
            System.err.println("Error reading UserData list from file: " + e.getMessage());
            return null;
        }
    }

    // Method to convert a single UserData object to a pretty-printed JSON string
    public static String writeUserDataToString(UserData userData) {
        return gson.toJson(userData);
    }

    // Method to convert a JSON string to a single UserData object
    public static UserData readUserDataFromString(String json) {
        return gson.fromJson(json, UserData.class);
    }

    // Method to convert a list of UserData objects to a pretty-printed JSON string
    public static String writeUserDataListToString(List<UserData> userDataList) {
        return gson.toJson(userDataList);
    }

    // Method to convert a JSON string to a list of UserData objects
    public static List<UserData> readUserDataListFromString(String json) {
        Type listType = new TypeToken<List<UserData>>(){}.getType();
        return gson.fromJson(json, listType);
    }
}
