package client;

import engine.ChessGameEngine;
import records.REPLFlags;
import records.REPLToken;

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
                case "Q", "q", "Quit", "quit" -> logout(flags, authToken, facade);
                default -> System.out.println("'" + response + "' is not a valid input. Try again.");
            }
            System.out.println("----------");
        }

    }

    private static void list(ServerFacade facade, REPLToken authToken) throws Exception {
        HttpURLConnection response = facade.list(authToken.authToken);
        switch (response.getResponseCode()) {
            case 200 -> {
                ServerFacade.ListResponse listResponse = ClientUtils.readBody(response, ServerFacade.ListResponse.class);
                for (ServerFacade.Game game : listResponse.games()) {
                    System.out.println(game);
                }
            }
            case 401 -> System.out.println("Unauthorized credentials. Please re-login.");
            default -> System.out.println("Server Error.");
        }
    }

    private static void create(List<String> createMenu, Scanner scanner, ServerFacade facade, REPLToken authToken) throws Exception {
        List<String> responses = ClientUtils.queryMenu(createMenu, scanner);
        HttpURLConnection response = facade.create(responses.getFirst(), authToken.authToken);
        switch (response.getResponseCode()) {
            case 200 -> {
                System.out.println("Game '" + responses.getFirst() + "' successfully created!");
            }
            case 400 -> System.out.println("Game Name cannot be empty.");
            case 401 -> System.out.println("Unauthorized credentials. Please re-login.");
            default -> System.out.println("Server Error.");
        }
    }

    private static void join(List<String> joinMenu, Scanner scanner, ServerFacade facade, REPLToken authToken) throws Exception {
        List<String> responses = ClientUtils.queryMenu(joinMenu, scanner);
        HttpURLConnection response = facade.join(responses.get(0), responses.get(1), authToken.authToken);
        switch (response.getResponseCode()) {
            case 200 -> {
                switch (responses.getFirst()) {
                    // Implement Get ChessGame Logic Here,
                    // Modify printChessboard to take ChessGame chessGame and use chessGame.getBoard().getBoard()
                    case "WHITE" -> {
                        System.out.println("Game Successfully Joined!");
                        ChessGameEngine.printChessboard("WHITE");
                    }
                    case "BLACK" -> {
                        System.out.println("Game Successfully Joined!");
                        ChessGameEngine.printChessboard("BLACK");
                    }
                }
            }
            case 400 -> {
                System.out.println("Player Color or Game ID cannot be empty.");
                System.out.println("Game ID must be numerical.");
            }
            case 401 -> System.out.println("Unauthorized credentials. Please re-login.");
            case 403 -> System.out.println("Player Color already taken.");
            default -> System.out.println("Server Error.");
        }
    }

    private static void spectate(List<String> spectateMenu, Scanner scanner, ServerFacade facade, REPLToken authToken) {
        System.out.println("To Be Implemented in Phase 06");
    }

    private static void logout(REPLFlags flags, REPLToken authToken, ServerFacade facade) throws Exception {
        HttpURLConnection response = facade.logout(authToken.authToken);
        switch (response.getResponseCode()) {
            case 200 -> {
                flags.replOne = true;
                flags.replTwo = false;
                System.out.println("♕ Returning to Login ♕");
            }
            case 401 -> System.out.println("Fatal Client error. Please restart the Client.");
            default -> System.out.println("Server Error. Try again.");
        }
    }
}
