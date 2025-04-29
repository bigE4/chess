package client;

import chess.ChessGame;
import chess.ChessMove;
import chess.InvalidMoveException;
import engine.ChessGameEngine;
import facade.WebsocketFacade;
import records.REPLData;
import websocket.messages.ServerMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class REPL3 implements NotificationHandler {

    private ChessGameEngine engine;
    private WebsocketFacade facade;

    public void replMain(Scanner scanner, REPLData flags, WebsocketFacade facade) throws Exception {
        System.out.println("♕ Game Menu ♕");

        this.engine = new ChessGameEngine(flags.color);
        this.facade = facade;

        var menus = ClientUtils.initMenusThree();
        var helpMenu = menus.get(0);
        var moveMenu = menus.get(1);

        engine.render();
        ClientUtils.printMenu(helpMenu);

        // Initialize the game engine.
        // game engine stores mutable list of moves.
        // on LOAD_GAME, that list of moves is appended to
        // Localize runtime game logic to game engine.
        // When a client tries to make a move, the game engine

        while (flags.replThree) {
            String response = scanner.nextLine();
            switch (response) {
                case "H", "h", "Help", "help" -> ClientUtils.printMenu(helpMenu);
                case "D", "d", "Draw", "draw" -> draw();
                case "M", "m", "Move", "move" -> move(moveMenu, scanner, flags);
                case "R", "r", "Resign", "resign" -> resign();
                case "L", "l", "Legal", "legal" -> legal();
                case "Q", "q", "Quit", "quit" -> switchToREPL2(flags);
                default -> System.out.println("'" + response + "' is not a valid input. Try again.");
            }
            System.out.println("----------");
        }
    }

    private void draw() {
        engine.render();
    }

    private void move(List<String> moveMenu, Scanner scanner, REPLData flags) {
        List<String> responses = ClientUtils.queryMenu(moveMenu, scanner);
        String start = responses.get(0);
        String end = responses.get(1);
        // DEBUGGING CODES
        // USEFUL FOR NOW REMOVE LATER
        System.out.println(responses);
        System.out.println(engine.getMoves());
        System.out.println(engine.getMoves().size());
        System.out.println(flags.color);

        if ( engine.getMoves().size() % 2 == 1 && flags.color == ChessGame.TeamColor.WHITE || engine.getMoves().size() % 2 == 0 && flags.color == ChessGame.TeamColor.BLACK) {
            System.out.println("Wait your turn!");
            return;
        }

        if (ClientUtils.isNotAValidChessSquare(start) || ClientUtils.isNotAValidChessSquare(end)) {
            System.out.println("Invalid position. Please try again.");
            return;
        }

        ChessMove move = ClientUtils.moveParser(start, end, null);

        if (!engine.isValidMove(move)) {
            System.out.println("Illegal move. Please try again.");
            return;
        }

        try {
            System.out.println(move);
            engine.applyMove(move);
            facade.sendMove();
        } catch (Exception e) {
            System.out.println("Server Error. Could not send move.");
        }
    }

    private void resign() {
    }

    private void legal() {
    }

    public void switchToREPL2(REPLData flags) {
        flags.replTwo = true;
        flags.replThree = false;
    }

    @Override
    public void notify(ServerMessage notification) {
        switch (notification.getServerMessageType()) {
            case ERROR: {
                System.out.println("Server Error");
            }
            case LOAD_GAME: {
                // write fetchMoves in WebsocketFacade
            }
            case NOTIFICATION: {
                System.out.println("Server Notification");
            }
        }
    }
}
