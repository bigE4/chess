package server;

import dataaccess.SQLDatabaseManager;
import exceptions.DataAccessException;
import handler.*;
import spark.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register endpoints
        Spark.post("/user", new RegisterHandler());
        System.out.println("post/user registered");
        Spark.post("/session", new LoginHandler());
        System.out.println("post/session registered");
        Spark.delete("/session", new LogoutHandler());
        System.out.println("delete/session registered");
        Spark.get("/game", new ListGamesHandler());
        System.out.println("get/game registered");
        Spark.post("/game", new CreateGameHandler());
        System.out.println("post/game registered");
        Spark.put("/game", new JoinGameHandler());
        System.out.println("put/game registered");
        Spark.delete("/db", new ClearHandler());
        System.out.println("delete/db registered");
        System.out.println("Ensuring Database Exists With Correct Tables...");
        try {
            SQLDatabaseManager.createDatabase();
            SQLDatabaseManager.createTables();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Database Exists!");
        //This line initializes the server and can be removed once you have a functioning endpoint
        Spark.init();
        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
