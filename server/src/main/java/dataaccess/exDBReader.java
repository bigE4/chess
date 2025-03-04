package dataaccess;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class exDBReader {

    // Create a Gson instance with pretty printing enabled
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Generic method to write a single object to a JSON file with pretty print
    public static <T> void writeToFile(String filePath, T data) {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            System.err.println("Error writing data to file: " + e.getMessage());
        }
    }

    // Generic method to read a single object from a JSON file
    public static <T> T readFromFile(String filePath, Class<T> clazz) {
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, clazz);
        } catch (IOException | JsonSyntaxException e) {
            System.err.println("Error reading data from file: " + e.getMessage());
            return null;
        }
    }

    // Generic method to write a list of objects to a JSON file with pretty print
    public static <T> void writeListToFile(String filePath, List<T> dataList) {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(dataList, writer);
        } catch (IOException e) {
            System.err.println("Error writing data list to file: " + e.getMessage());
        }
    }

    // Generic method to read a list of objects from a JSON file (with type safety)
    public static <T> List<T> readListFromFile(String filePath, TypeToken<List<T>> typeToken) {
        try (FileReader reader = new FileReader(filePath)) {
            Type listType = typeToken.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException | JsonSyntaxException e) {
            System.err.println("Error reading data list from file: " + e.getMessage());
            return null;
        }
    }

    // Generic method to convert a single object to a pretty-printed JSON string
    public static <T> String writeToString(T data) {
        return gson.toJson(data);
    }

    // Generic method to convert a JSON string to a single object
    public static <T> T readFromString(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    // Generic method to convert a list of objects to a pretty-printed JSON string
    public static <T> String writeListToString(List<T> dataList) {
        return gson.toJson(dataList);
    }

    // Generic method to convert a JSON string to a list of objects
    public static <T> List<T> readListFromString(String json, TypeToken<List<T>> typeToken) {
        Type listType = typeToken.getType();
        return gson.fromJson(json, listType);
    }
}
