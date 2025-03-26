import chess.*;

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

        Scanner scanner = new Scanner(System.in);
        boolean cont = true;
        printMenu(helpMenu);

        while (cont) {
            String response = scanner.nextLine();
            switch (response) {
                case "H", "h", "Help", "help" -> printMenu(helpMenu);
                case "L", "l", "Login", "login" -> printMenu(loginMenu);
                case "R", "r", "Register", "register" -> printMenu(registerMenu);
                case "Q", "q", "Quit", "quit" -> {
                    cont = false;
                    System.out.println("See ya!");
                }
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