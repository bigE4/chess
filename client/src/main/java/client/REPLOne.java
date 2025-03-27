package client;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class REPLOne {
    public static void replMain(Scanner scanner, REPLFlags flags, ServerFacade facade) throws Exception {
        System.out.println("♕ Welcome to Ian's cs240 Chess Client. Sign in or register to begin. ♕");

        var menus = initMenus();
        var helpMenu = menus.get(0);
        var loginMenu = menus.get(1);
        var registerMenu = menus.get(2);

        printMenu(helpMenu);

        while (flags.replOne && !flags.replTwo) {
            String response = scanner.nextLine();
            switch (response) {
                case "H", "h", "Help", "help" -> printMenu(helpMenu);
                case "L", "l", "Login", "login" -> login(loginMenu, scanner, facade);
                case "R", "r", "Register", "register" -> register(registerMenu, scanner, facade);
                case "V" -> flags.replTwo = true;
                case "Q", "q", "Quit", "quit" -> {
                    flags.replOne = false;
                    System.out.println("♕ See ya! ♕");
                }
                default -> System.out.println("'" + response + "' is not a valid input. Try again.");
            }
            System.out.println("----------");
        }
    }

    public static void login(List<String> loginMenu, Scanner scanner, ServerFacade facade) throws Exception {
        List<String> responses = new ArrayList<>();
        responses = queryMenu(loginMenu, scanner);
        int serverResponse = facade.login(responses.get(0), responses.get(1));
    }

    private static void login200() {

    }

    private static void register(List<String> registerMenu, Scanner scanner, ServerFacade facade) throws Exception {
        List<String> responses = new ArrayList<>();
        responses = queryMenu(registerMenu, scanner);
        int serverResponse = facade.register(responses.get(0), responses.get(1), responses.get(2));
    }

    private static void register200() {

    }

    private static List<List<String>> initMenus() {
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

    private static void printMenu(List<String> menu) {
        for (String line : menu) {
            System.out.println(line);
        }
    }

    private static List<String> queryMenu(List<String> menu, Scanner scanner) {
        ArrayList<String> responses = new ArrayList<>();
        for (String line : menu) {
            System.out.println(line);
            responses.add(scanner.nextLine());
        }
        System.out.println("You input: " + responses);
        return responses;
    }
}
