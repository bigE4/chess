package dataaccess.exampledatabase;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class ExampleDatabaseReader {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static <T> void writeListToFile(String filePath, List<T> dataList) {
        String absoluteFilepath = System.getProperty("user.dir") + "\\" + filePath;
        try (FileWriter writer = new FileWriter(absoluteFilepath)) {
            GSON.toJson(dataList, writer);
        } catch (IOException e) {
            System.err.println("Error writing data list to file: " + e.getMessage());
        }
    }

    public static <T> List<T> readListFromFile(String filePath, TypeToken<List<T>> typeToken) {
        String absoluteFilepath = System.getProperty("user.dir") + "\\" + filePath;
        try (FileReader reader = new FileReader(absoluteFilepath)) {
            Type listType = typeToken.getType();
            return GSON.fromJson(reader, listType);
        } catch (IOException | JsonSyntaxException e) {
            System.err.println("Error reading data list from file: " + e.getMessage());
            return null;
        }
    }
}
