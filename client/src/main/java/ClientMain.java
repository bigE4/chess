import chess.*;

import com.google.gson.Gson;
import java.util.List;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) {
        System.out.println("♕ Welcome to Ian's cs240 Chess Client. Sign in or register to begin. ♕");

        // menus init
        List<String> helpMenu = List.of(
                "Options: ",
                "Login: (L, l, Login, login)",
                "Register: (R, r, Register, register)",
                "Help: (H, h, Help, help)",
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

        List<String> quitList = List.of(
                "Quit", "quit", "Q", "q");

        Scanner scanner = new Scanner(System.in);
        String response = "";
        printMenu(helpMenu);

        while (!quitList.contains(response)) {
            response = scanner.nextLine();
            switch (response) {
                case "H", "h", "Help", "help" -> printMenu(helpMenu);
                case "L", "l", "Login", "login" -> printMenu(loginMenu);
                case "R", "r", "Register", "register" -> printMenu(registerMenu);
                case "Q", "q", "Quit", "quit" -> System.out.println("Thanks!");
                default -> System.out.println("'" + response + "' is not a valid input. Try again.");
            }
            System.out.println("----------");

        }
    }

    private static void printMenu(List<String> menu) {
        for (String line : menu) {
            System.out.println(line);
        }
    }

}