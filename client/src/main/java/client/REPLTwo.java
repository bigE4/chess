package client;

import engine.ChessGameEngine;
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

        List<String> joinMenu2 = new ArrayList<>();
        joinMenu2.add("♕ Game List ♕");
        List<Integer> gameIDs = new ArrayList<>();

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
                case "L", "l", "List", "list" -> list(joinMenu2, gameIDs, flags, facade, authToken);
                case "J", "j", "Join", "join" -> join(joinMenu, joinMenu2, gameIDs, scanner, flags, facade, authToken);
                case "S", "s", "Spectate", "spectate" -> spectate(spectateMenu, scanner, flags, facade, authToken);
                case "Q", "q", "Quit", "quit" -> logout(flags, authToken, facade);
                default -> System.out.println("'" + response + "' is not a valid input. Try again.");
            }
            System.out.println("----------");
        }

    }

    private static void list(List<String> joinMenu2, List<Integer> gameIDs, REPLFlags flags, ServerFacade facade, REPLToken authToken) throws Exception {
        HttpURLConnection response = facade.list(authToken.authToken);
        switch (response.getResponseCode()) {
            case 200 -> {
                listHelper(joinMenu2, gameIDs, flags, response);
            }
            case 401 -> System.out.println("Unauthorized credentials. Please re-login.");
            default -> System.out.println("Server Error.");
        }
    }

    private static void listHelper(List<String> joinMenu2, List<Integer> gameIDs, REPLFlags flags, HttpURLConnection response) throws IOException {
        ServerFacade.ListResponse listResponse = ClientUtils.readBody(response, ServerFacade.ListResponse.class);
        joinMenu2 = new ArrayList<>();
        joinMenu2.add("♕ Game List ♕");
        int i = 0;
        for (ServerFacade.Game game : listResponse.games()) {
            i++;
            joinMenu2.add("Game " + i + ": " + game.gameName());
            gameIDs.add(game.gameID());
        }
        ClientUtils.printMenu(joinMenu2);
        if (joinMenu2.size() > 1) {
            flags.listQueried = true;
        }
    }

    private static void create(List<String> createMenu, Scanner scanner, ServerFacade facade, REPLToken authToken) throws Exception {
        List<String> responses = ClientUtils.queryMenu(createMenu, scanner);
        HttpURLConnection response = facade.create(responses.getFirst(), authToken.authToken);
        switch (response.getResponseCode()) {
            case 200 -> System.out.println("Game -" + responses.getFirst() + "- successfully created!");
            case 400 -> System.out.println("Name cannot be empty.");
            case 401 -> System.out.println("Unauthorized credentials. Please re-login.");
            default -> System.out.println("Server Error.");
        }
    }

    private static void join(List<String> joinMenu, List<String> joinMenu2, List<Integer> gameIDs, Scanner scanner, REPLFlags flags, ServerFacade facade, REPLToken authToken) throws Exception {
        if (flags.listQueried) {
            List<String> responses = ClientUtils.queryMenu(joinMenu, scanner);
            String selectionString = responses.get(1);
            int gameID = 0;
            try {
                int selectionIndex = Integer.parseInt(selectionString) - 1;
                gameID = gameIDs.get(selectionIndex);
            } catch (Exception ignored) {}
            HttpURLConnection response = facade.join(responses.getFirst(), String.valueOf(gameID), authToken.authToken);
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
                    System.out.println("Color must be WHITE or BLACK.");
                    System.out.println("Number must be on the list.");
                }
                case 401 -> System.out.println("Unauthorized credentials. Please re-login.");
                case 403 -> System.out.println("That color is already taken.");
                default -> System.out.println("Server Error.");
            }
        } else {
            System.out.println("List games to see a game you can join.");
            System.out.println("If there are no games, create one!");

        }
    }

    private static void spectate(List<String> spectateMenu, Scanner scanner, REPLFlags flags, ServerFacade facade, REPLToken authToken) {
        System.out.println("To Be Implemented in Phase 06");
    }

    private static void logout(REPLFlags flags, REPLToken authToken, ServerFacade facade) throws Exception {
        HttpURLConnection response = facade.logout(authToken.authToken);
        switch (response.getResponseCode()) {
            case 200 -> {
                flags.replOne = true;
                flags.replTwo = false;
                flags.listQueried = false;
                System.out.println("♕ Returning to Login ♕");
            }
            case 401 -> System.out.println("Fatal Client error. Please restart the Client.");
            default -> System.out.println("Server Error. Try again.");
        }
    }
}
