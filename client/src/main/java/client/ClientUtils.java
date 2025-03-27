package client;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientUtils {
    public static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }

    static List<List<String>> initMenusOne() {
        List<String> helpMenu = List.of(
                "Options: ",
                "Help: (H, h, Help, help)",
                "Login: (L, l, Login, login)",
                "Register: (R, r, Register, register)",
                "Quit: (Q, q, Quit, quit)"
        );
        List<String> loginMenu = List.of(
                "Username: ",
                "Password: "
        );
        List<String> registerMenu = List.of(
                "Username: ",
                "Password: ",
                "Email: "
        );

        return List.of(helpMenu, loginMenu, registerMenu);
    }

    static List<List<String>> initMenusTwo() {
        List<String> helpMenu = List.of(
                "Options:",
                "Help: (H, h, Help, help)",
                "Create Game: (C, c, Create, create)",
                "List Games: (L, l, List, list)",
                "Play Game: (P, p, Play, play)",
                "Spectate Game: (S, s, Spectate, spectate)",
                "Logout: (Q, q, Quit, quit)"
        );
        List<String> createMenu = List.of(
                "Game Name:"
        );
        List<String> playMenu = List.of(
                "Select a Color: (White, white, W, w, Black, black, B, b)",
                "Select a Game: (Type the number of the game you want to join)"
        );
        List<String> spectateMenu = List.of(
                "Select a Game: (Type the number of the game you want to join)"
        );

        return List.of(helpMenu, createMenu, playMenu, spectateMenu);
    }

    public static void printMenu(List<String> menu) {
        for (String line : menu) {
            System.out.println(line);
        }
    }

    public static List<String> queryMenu(List<String> menu, Scanner scanner) {
        ArrayList<String> responses = new ArrayList<>();
        for (String line : menu) {
            System.out.println(line);
            responses.add(scanner.nextLine());
        }
        System.out.println("You input: " + responses);
        return responses;
    }
}
