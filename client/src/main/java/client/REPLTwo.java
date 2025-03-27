package client;

import records.REPLFlags;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class REPLTwo {
    public static void replMain(Scanner scanner, REPLFlags flags, ServerFacade facade, String authToken) {
        System.out.println("♕ Login Successful! ♕");

        var menus = initMenus();
        var helpMenu = menus.get(0);
        var createMenu = menus.get(1);
        var listMenu = menus.get(2);
        var playMenu  = menus.get(3);
        var spectateMenu = menus.get(4);

        printMenu(helpMenu);

        while (flags.replTwo) {
            String response = scanner.nextLine();
            List<String> responses = new ArrayList<>();
            switch (response) {
                case "H", "h", "Help", "help" -> printMenu(helpMenu);
                case "C", "c", "Create", "create" -> create(createMenu, scanner, facade, authToken);
                case "L", "l", "List", "list" -> printMenu(listMenu);
                case "P", "p", "Play", "play" -> responses = queryMenu(playMenu, scanner);
                case "S", "s", "Spectate", "spectate" -> responses = queryMenu(spectateMenu, scanner);
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

    private static void create(List<String> createMenu, Scanner scanner, ServerFacade facade, String authToken) {

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
        List<String> listMenu = List.of(
                "Game List:"
        );
        List<String> playMenu = List.of(
                "Select a Game: (1 - Length of listMenu)",
                "Select a Color: (White, white, W, w, Black, black, B, b)"
        );
        List<String> spectateMenu = List.of(
                "Select a Game: (1 - Length of listMenu)"
        );

        return List.of(helpMenu, createMenu, listMenu, playMenu, spectateMenu);
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
