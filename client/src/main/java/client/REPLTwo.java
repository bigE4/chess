package client;

import records.REPLFlags;
import records.REPLToken;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class REPLTwo {
    public static void replMain(Scanner scanner, REPLFlags flags, ServerFacade facade, REPLToken authToken) throws Exception {
        System.out.println("♕ Login Successful! ♕");

        var menus = ClientUtils.initMenusTwo();
        var helpMenu = menus.get(0);
        var createMenu = menus.get(1);
        var playMenu  = menus.get(2);
        var spectateMenu = menus.get(3);

        ClientUtils.printMenu(helpMenu);

        while (flags.replTwo) {
            String response = scanner.nextLine();
            List<String> responses = new ArrayList<>();
            switch (response) {
                case "H", "h", "Help", "help" -> ClientUtils.printMenu(helpMenu);
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
        List<String> responses = ClientUtils.queryMenu(createMenu, scanner);
        HttpURLConnection response = facade.create(responses.getFirst(), authToken.authToken);
        int code = response.getResponseCode();
        switch (code) {
            case 200 -> System.out.println("Game Successfully Created!");
            case 400 -> System.out.println("Game Name cannot be empty");
            case 401 -> System.out.println("Unauthorized credentials. Please re-login.");
            default -> System.out.println("Server Error.");
        }
    }

    private static void list(Scanner scanner, ServerFacade facade, REPLToken authToken) throws Exception {
        System.out.println("You want me to list games");
        System.out.println(authToken.authToken);
        HttpURLConnection response = facade.list(authToken.authToken);
        int code = response.getResponseCode();

        System.out.println(code);
    }

    private static void play(List<String> playMenu, Scanner scanner, ServerFacade facade, REPLToken authToken) throws Exception {
        List<String> responses = ClientUtils.queryMenu(playMenu, scanner);
        HttpURLConnection response = facade.play(responses.get(0), Integer.parseInt(responses.get(1)), authToken.authToken);
        int code = response.getResponseCode();
    }

    private static void spectate(List<String> spectateMenu, Scanner scanner, ServerFacade facade, REPLToken authToken) {
        System.out.println("To Be Implemented in Phase 06");
    }
}
