package client;

import chess.ChessGame;
import facade.ServerFacade;
import records.REPLData;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class REPL2 {
    public static void replMain(Scanner scanner, REPLData flags, ServerFacade facade) throws Exception {
        System.out.println("♕ Main Menu ♕");

        List<String> joinMenu2 = new ArrayList<>();
        joinMenu2.add("♕ Game List ♕");
        List<Integer> gameIDs = new ArrayList<>();

        var menus = ClientUtils.initMenusTwo();
        var helpMenu = menus.get(0);
        var createMenu = menus.get(1);
        var joinMenu  = menus.get(2);
        var spectateMenu = menus.get(3);

        ClientUtils.printMenu(helpMenu);

        while (flags.replTwo && !flags.replThree) {
            String response = scanner.nextLine();
            switch (response) {
                case "H", "h", "Help", "help" -> ClientUtils.printMenu(helpMenu);
                case "C", "c", "Create", "create" -> create(createMenu, scanner, flags, facade);
                case "L", "l", "List", "list" -> list(joinMenu2, gameIDs, flags, facade);
                case "J", "j", "Join", "join" -> join(joinMenu, gameIDs, scanner, flags, facade);
                case "S", "s", "Spectate", "spectate" -> spectate(spectateMenu, scanner, flags, facade);
                case "Q", "q", "Quit", "quit" -> logout(flags, facade);
                default -> System.out.println("'" + response + "' is not a valid input. Try again.");
            }
            System.out.println("----------");
        }
    }

    private static void create(List<String> createMenu, Scanner scanner, REPLData flags, ServerFacade facade) throws Exception {
        List<String> responses = ClientUtils.queryMenu(createMenu, scanner);
        HttpURLConnection response = facade.create(responses.getFirst(), flags.authToken);
        switch (response.getResponseCode()) {
            case 200 -> System.out.println("Game -" + responses.getFirst() + "- successfully created!");
            case 400 -> System.out.println("Name cannot be empty.");
            case 401 -> System.out.println("Unauthorized credentials. Please re-login.");
            default -> System.out.println("Server Error.");
        }
    }

    private static void list(List<String> joinMenu2, List<Integer> gameIDs, REPLData flags, ServerFacade facade) throws Exception {
        HttpURLConnection response = facade.list(flags.authToken);
        switch (response.getResponseCode()) {
            case 200 -> {
                listHelper(joinMenu2, gameIDs, flags, response);
            }
            case 401 -> System.out.println("Unauthorized credentials. Please re-login.");
            default -> System.out.println("Server Error.");
        }
    }

    private static void listHelper(List<String> joinMenu2, List<Integer> gameIDs, REPLData flags, HttpURLConnection response) throws IOException {
        ServerFacade.ListResponse listResponse = ClientUtils.readBody(response, ServerFacade.ListResponse.class);
        joinMenu2 = new ArrayList<>();
        joinMenu2.add("♕ Game List ♕");
        int i = 0;
        for (ServerFacade.Game game : listResponse.games()) {
            String white = game.whiteUsername();
            String black = game.blackUsername();
            if (game.whiteUsername() == null) { white = "Empty"; }
            if (game.blackUsername() == null) { black = "Empty"; }
            i++;
            joinMenu2.add("Game " + i + ") " + game.gameName() + " | Users: White: " + white + " | Black: " + black);
            gameIDs.add(game.gameID());
        }
        ClientUtils.printMenu(joinMenu2);
        if (joinMenu2.size() > 1) {
            flags.listQueried = true;
        }
    }

    private static void join(List<String> joinMenu, List<Integer> gameIDs, Scanner scanner, REPLData flags, ServerFacade facade
    ) throws Exception {
        if (flags.listQueried) {
            List<String> responses = ClientUtils.queryMenu(joinMenu, scanner);
            String selectionString = responses.get(1);
            int gameID = 0;
            try {
                int selectionIndex = Integer.parseInt(selectionString) - 1;
                gameID = gameIDs.get(selectionIndex);
            } catch (Exception ignored) {}
            HttpURLConnection response = facade.join(responses.getFirst(), String.valueOf(gameID), flags.authToken);
            joinSwitch1(response, responses, flags);
        } else {
            System.out.println("List games to see a game you can join.");
            System.out.println("If there are no games, create one!");

        }
    }

    private static void joinSwitch1(HttpURLConnection response, List<String> responses, REPLData flags) throws IOException {
        switch (response.getResponseCode()) {
            case 200 -> {
                joinSwitch2(responses, flags);
            }
            case 400 -> {
                System.out.println("Color must be WHITE or BLACK.");
                System.out.println("Number must be on the list.");
            }
            case 401 -> System.out.println("Unauthorized credentials. Please re-login.");
            case 403 -> System.out.println("That color is already taken.");
            default -> System.out.println("Server Error.");
        }
    }

    private static void joinSwitch2(List<String> responses, REPLData flags) {
        switch (responses.getFirst()) {

            // Implement Get ChessGame Logic Here,
            // Modify printChessboard to take ChessGame chessGame and use chessGame.getBoard().getBoard()

            case "WHITE" -> {
                System.out.println("Game Successfully Joined!");
                flags.color = ChessGame.TeamColor.WHITE;
            }
            case "BLACK" -> {
                System.out.println("Game Successfully Joined!");
                flags.color = ChessGame.TeamColor.BLACK;
            }
        }
        switchToREPL3(flags);
    }

    private static void spectate(List<String> spectateMenu, Scanner scanner, REPLData flags, ServerFacade facade) {
        System.out.println("To Be Implemented in Phase 06");

        // There's going to be a lot more work here.

        switchToREPL3(flags);
    }

    private static void logout(REPLData flags, ServerFacade facade) throws Exception {
        HttpURLConnection response = facade.logout(flags.authToken);
        switch (response.getResponseCode()) {
            case 200 -> {
                switchToREPL1(flags);
                System.out.println("♕ Returning to Login ♕");
            }
            case 401 -> System.out.println("Fatal Client error. Please restart the Client.");
            default -> System.out.println("Server Error. Try again.");
        }
    }

    public static void switchToREPL1(REPLData flags) {
        flags.replOne = true;
        flags.replTwo = false;
        flags.listQueried = false;
    }

    public static void switchToREPL3(REPLData flags) {
        flags.replTwo = false;
        flags.replThree = true;
    }

}
