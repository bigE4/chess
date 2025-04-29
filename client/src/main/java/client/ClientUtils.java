package client;

import chess.ChessMove;
import chess.ChessPosition;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientUtils {

    private static final Gson gson = new Gson();

    public static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = gson.fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }

    public static ChessMove moveParser(String startString, String endString, String promoString) {
        ChessPosition start = parsePosition(startString);
        ChessPosition end = parsePosition(endString);
        return new ChessMove(start, end, null);
    }

    private static ChessPosition parsePosition(String input) {
        int col = input.toLowerCase().charAt(0) - 'a' + 1;
        int row = Character.getNumericValue(input.charAt(1));
        return new ChessPosition(row, col);
    }

    public static boolean isNotAValidChessSquare(String input) {
        List<String> validColumns = List.of("a", "b", "c", "d", "e", "f", "g", "h");
        List<String> validRows = List.of("1", "2", "3", "4", "5", "6", "7", "8");

        if (input == null || input.length() != 2) {
            return true;
        }

        String col = String.valueOf(input.toLowerCase().charAt(0));
        String row = String.valueOf(input.charAt(1));

        return !validColumns.contains(col) || !validRows.contains(row);
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
                "Join Game: (J, j, Join, join)",
                "Spectate Game: (S, s, Spectate, spectate)",
                "Logout: (Q, q, Quit, quit)"
        );
        List<String> createMenu = List.of(
                "Game Name:"
        );
        List<String> joinMenu = List.of(
                "Select a Color: (WHITE, BLACK)",
                "Select a Game: (Type the number of the game you want to join)"
        );
        List<String> spectateMenu = List.of(
            "Select a Game: (Type the number of the game you want to join)"
        );

        return List.of(helpMenu, createMenu, joinMenu, spectateMenu);
    }

    static List<List<String>> initMenusThree() {
        List<String> helpMenu = List.of(
                "Options:",
                "Help: (H, h, Help, help)",
                "Draw Chessboard: (D, d, Draw, draw)",
                "Move: (M, m, Move, move)",
                "Resign: (R, r, Resign, resign)",
                "Highlight Legal Moves: (L, l, Legal, legal)",
                "Leave: (Q, q, Quit, quit)"
        );

        List<String> moveMenu = List.of(
                "Move Starting Position:",
                "Move Ending Position:"
        );

        List<String> promoMenu = List.of(
                "Select Promotion Type: (Q, R, B, K)"
        );


        return List.of(helpMenu, moveMenu, promoMenu);
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
        return responses;
    }
}
