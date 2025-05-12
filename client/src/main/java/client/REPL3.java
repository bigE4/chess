package client;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import engine.ChessGameEngine;
import facade.WebsocketFacade;
import records.REPLData;
import websocket.messages.ServerMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class REPL3 implements NotificationHandler {

    private List<ChessMove> moves;
    private WebsocketFacade facade;
    private ChessGameEngine engine;

    public void replMain(Scanner scanner, REPLData flags, WebsocketFacade facade) throws Exception {
        System.out.println("♕ Game Menu ♕");

        this.moves = new ArrayList<>();
        this.facade = facade;
        this.engine = new ChessGameEngine(flags.color, this);
        engine.render();

        var menus = ClientUtils.initMenusThree();
        var helpMenu = menus.get(0);
        var moveMenu = menus.get(1);
        var legalMenu = menus.get(2);

        ClientUtils.printMenu(helpMenu);

        // REPL3 stores a list of moves since it is talking to WebsocketFacade.
        // on a LOAD_GAME notification the list is updated with the move,
        // and the last move in the list is applied to the game.

        while (flags.replThree) {
            String response = scanner.nextLine();
            switch (response) {
                case "H", "h", "Help", "help" -> ClientUtils.printMenu(helpMenu);
                case "D", "d", "Draw", "draw" -> draw();
                case "M", "m", "Move", "move" -> move(moveMenu, scanner, flags);
                case "R", "r", "Resign", "resign" -> resign();
                case "L", "l", "Legal", "legal" -> legal(legalMenu, scanner);
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
        System.out.println(getMoves());
        System.out.println(getMoves().size());
        System.out.println(flags.color);
        // DEBUGGING CODES
        // USEFUL FOR NOW REMOVE LATER


        if ((getMoves().size() % 2 == 1 && flags.color == ChessGame.TeamColor.WHITE) || (getMoves().size() % 2 == 0 && flags.color == ChessGame.TeamColor.BLACK)) {
            System.out.println("Wait your turn.");
            return;
        }

        if (ClientUtils.isNotAValidChessSquare(start) || ClientUtils.isNotAValidChessSquare(end)) {
            System.out.println("Invalid chess notation.");
            System.out.println("Select the starting and ending squares with (a1 - h8)");
            return;
        }
        ChessPosition startPosition = ClientUtils.parsePosition(start);

        if (engine.game.getBoard().getPiece(startPosition).getTeamColor() != flags.color) {
            System.out.println("That's not your piece.");
            return;
        }

        ChessMove move = ClientUtils.parseMove(start, end, null);

        if (!engine.isValidMove(move)) {
            System.out.println("Illegal move. To see legal moves use the legal command.");
            return;
        }

        try {
            System.out.println(move);
            moves.add(move);
            engine.applyMove(move);
            facade.sendMove(move, flags.gameID);
        } catch (Exception e) {
            System.out.println("Server Error. Could not send move.");
        }
    }

    private void resign() {
    }

    private void legal(List<String> legalMenu, Scanner scanner) {
        List<String> responses = ClientUtils.queryMenu(legalMenu, scanner);
        String start = responses.getFirst();

        if (ClientUtils.isNotAValidChessSquare(start)) {
            System.out.println("Invalid chess notation!");
            System.out.println("Select the starting position with (a1 - h8)");
            return;
        }

        ChessPosition pos = ClientUtils.parsePosition(start);
        engine.legalRender(pos);
    }

    public void switchToREPL2(REPLData flags) {
        flags.replTwo = true;
        flags.replThree = false;
    }

    public List<ChessMove> getMoves() {
        return moves;
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
