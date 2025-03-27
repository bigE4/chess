package client;

import records.REPLFlags;
import records.REPLToken;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class REPLTwo {
    public static void replMain(Scanner scanner, REPLFlags flags, REPLToken authToken, ServerFacade facade) throws Exception {
        System.out.println("♕ Login Successful! ♕");

        var menus = ClientUtils.initMenusTwo();
        var helpMenu = menus.get(0);
        var createMenu = menus.get(1);
        var joinMenu  = menus.get(2);
        var spectateMenu = menus.get(3);

        ClientUtils.printMenu(helpMenu);

        while (flags.replTwo) {
            String response = scanner.nextLine();
            List<String> responses = new ArrayList<>();
            switch (response) {
                case "H", "h", "Help", "help" -> ClientUtils.printMenu(helpMenu);
                case "C", "c", "Create", "create" -> create(createMenu, scanner, facade, authToken);
                case "L", "l", "List", "list" -> list(facade, authToken);
                case "J", "j", "Join", "join" -> join(joinMenu, scanner, facade, authToken);
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

    private static void list(ServerFacade facade, REPLToken authToken) throws Exception {
        HttpURLConnection response = facade.list(facade, authToken.authToken);
        int code = response.getResponseCode();
        System.out.println(code);
    }

    private static void create(List<String> createMenu, Scanner scanner, ServerFacade facade, REPLToken authToken) throws Exception {
        HttpURLConnection response = facade.create();
        int code = response.getResponseCode();
        System.out.println(code);
    }

    private static void join(List<String> joinMenu, Scanner scanner, ServerFacade facade, REPLToken authToken) throws Exception {
        HttpURLConnection response = facade.join();
        int code = response.getResponseCode();
        System.out.println(code);
    }

    private static void spectate(List<String> spectateMenu, Scanner scanner, ServerFacade facade, REPLToken authToken) {
        System.out.println("To Be Implemented in Phase 06");
    }
}
