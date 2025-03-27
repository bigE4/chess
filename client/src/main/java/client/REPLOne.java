package client;

import records.REPLFlags;
import records.REPLToken;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class REPLOne {
    public static void replMain(Scanner scanner, REPLFlags flags, REPLToken token, ServerFacade facade) throws Exception {
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
                case "L", "l", "Login", "login" -> login(loginMenu, scanner, flags, token, facade);
                case "R", "r", "Register", "register" -> register(registerMenu, scanner, flags, token, facade);
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

    private static void login(List<String> loginMenu, Scanner scanner, REPLFlags flags, REPLToken token, ServerFacade facade)  throws Exception {
        List<String> responses = queryMenu(loginMenu, scanner);
        HttpURLConnection response = facade.login(responses.get(0), responses.get(1));
        int code = response.getResponseCode();
        if (code == 200) {
            ServerFacade.AuthResponse authResponse = ClientUtils.readBody(response, ServerFacade.AuthResponse.class);
            token.authToken = authResponse.authToken();
            flags.replTwo = true;
        } else {
            System.out.println("Unauthorized credentials.");
        }

    }


    private static void register(List<String> registerMenu, Scanner scanner, REPLFlags flags, REPLToken token, ServerFacade facade) throws Exception {
        List<String> responses = queryMenu(registerMenu, scanner);
        HttpURLConnection response = facade.register(responses.get(0), responses.get(1), responses.get(2));
        switch (response.getResponseCode()) {
            case 200: {
                ServerFacade.AuthResponse authResponse = ClientUtils.readBody(response, ServerFacade.AuthResponse.class);
                token.authToken = authResponse.authToken();
                flags.replTwo = true;
                break;
            }
            case 400: System.out.println("Username, Password, and Email cannot be empty."); break;
            case 403: System.out.println("Username already taken."); break;
            default: System.out.println("Server Error."); break;
        }
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

    private static class LoginResponse {

    }
}
