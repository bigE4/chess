package client;

import records.REPLFlags;
import records.REPLToken;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class REPLTwo {
    public static void replMain(Scanner scanner, REPLFlags flags, ServerFacade facade, REPLToken authToken) throws Exception {
        System.out.println("♕ Login Successful! ♕");

        var menus = initMenus();
        var helpMenu = menus.get(0);
        var createMenu = menus.get(1);
        var playMenu  = menus.get(2);
        var spectateMenu = menus.get(3);

        printMenu(helpMenu);

        while (flags.replTwo) {
            String response = scanner.nextLine();
            List<String> responses = new ArrayList<>();
            switch (response) {
                case "H", "h", "Help", "help" -> printMenu(helpMenu);
                case "C", "c", "Create", "create" -> create(createMenu, scanner, facade, authToken);
                case "L", "l", "List", "list" -> list(scanner, facade, authToken);
                case "P", "p", "Play", "play" -> play(playMenu, scanner, facade, authToken);
                case "S", "s", "Spectate", "spectate" -> spectate(spectateMenu, scanner, facade, authToken);
                case "Q", "q", "Quit", "quit" -> {
                    flags.replOne = true;
                    flags.replTwo = false;
                    System.out.println("♕ Returning to Login ♕");
                }
                default -> System.out.println("'" + response + "' is not a valid input. Try again.");
            }
            System.out.println("----------");
        }

    }

    private static void create(List<String> createMenu, Scanner scanner, ServerFacade facade, REPLToken authToken) throws Exception {
        List<String> responses = queryMenu(createMenu, scanner);
        HttpURLConnection response = facade.create(responses.getFirst(), authToken);
        int code = response.getResponseCode();
        switch (code) {
            case 200 -> System.out.println("Game Successfully Created!");
            case 400 -> System.out.println("Game Name cannot be empty");
            case 401 -> System.out.println("Unauthorized credentials. Please re-login.");
            default -> System.out.println("Server Error.");
        }
    }

    private static void list(Scanner scanner, ServerFacade facade, REPLToken authToken) throws Exception {
        HttpURLConnection response = facade.list(authToken);
        int code = response.getResponseCode();
    }

    private static void play(List<String> playMenu, Scanner scanner, ServerFacade facade, REPLToken authToken) throws Exception {
        List<String> responses = queryMenu(playMenu, scanner);
        HttpURLConnection response = facade.play(responses.get(0), Integer.parseInt(responses.get(1)), authToken);
        int code = response.getResponseCode();
    }

    private static void spectate(List<String> spectateMenu, Scanner scanner, ServerFacade facade, REPLToken authToken) {
        System.out.println("To Be Implemented in Phase 06");
    }


    private static List<List<String>> initMenus() {
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
