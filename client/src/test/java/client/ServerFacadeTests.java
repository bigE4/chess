package client;

import dataaccess.UserSQLDAO;
import org.junit.jupiter.api.*;
import server.Server;
import service.ClearService;

import java.net.HttpURLConnection;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade serverFacade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        serverFacade = new ServerFacade("http://localhost:" + port);
        System.out.println("Started test HTTP server on " + port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @Test
    @Order(1)
    void testRegisterSuccess() throws Exception {
        UserSQLDAO.clear();
        HttpURLConnection response = serverFacade.register("UserA", "password123", "userA@email.com");
        assertEquals(200, response.getResponseCode());
    }

    @Test
    @Order(2)
    void testRegisterDuplicate() throws Exception {
        serverFacade.register("UserA", "password123", "userA@email.com");
        HttpURLConnection response = serverFacade.register("UserA", "password123", "userA@email.com");
        assertEquals(403, response.getResponseCode());
    }

    @Test
    @Order(3)
    void testLoginSuccess() throws Exception {
        serverFacade.register("UserA", "password123", "userA@email.com");
        HttpURLConnection response = serverFacade.login("UserA", "password123");
        assertEquals(200, response.getResponseCode());
    }

    @Test
    @Order(4)
    void testLoginFail() throws Exception {
        serverFacade.register("UserA", "password123", "userA@email.com");
        HttpURLConnection response = serverFacade.login("UserA", "wrongPassword");
        assertEquals(401, response.getResponseCode());
    }

    @Test
    @Order(5)
    void testCreateGameSuccess() throws Exception {
        HttpURLConnection loginResponse = serverFacade.login("UserA", "password123");
        ServerFacade.AuthResponse authResponse = ClientUtils.readBody(loginResponse, ServerFacade.AuthResponse.class);
        HttpURLConnection response = serverFacade.create("ChessGame", authResponse.authToken());
        assertEquals(200, response.getResponseCode());
    }

    @Test
    @Order(6)
    void testJoinGameSuccess() throws Exception {
        HttpURLConnection loginResponse = serverFacade.login("UserA", "password123");
        ServerFacade.AuthResponse authResponse = ClientUtils.readBody(loginResponse, ServerFacade.AuthResponse.class);
        HttpURLConnection createResponse = serverFacade.create("ChessGame", authResponse.authToken());
        int gameID = ClientUtils.readBody(createResponse, ServerFacade.CreateResponse.class).gameID();
        HttpURLConnection response = serverFacade.join("WHITE", String.valueOf(gameID), authResponse.authToken());
        assertEquals(200, response.getResponseCode());
    }

    @Test
    @Order(7)
    void testJoinGameFail() throws Exception {
        HttpURLConnection loginResponse = serverFacade.login("UserA", "password123");
        ServerFacade.AuthResponse authResponse = ClientUtils.readBody(loginResponse, ServerFacade.AuthResponse.class);
        HttpURLConnection response = serverFacade.join("White", "999", authResponse.authToken());
        assertEquals(400, response.getResponseCode());
    }

    @Test
    @Order(8)
    void testLogoutSuccess() throws Exception {
        HttpURLConnection loginResponse = serverFacade.login("UserA", "password123");
        ServerFacade.AuthResponse authResponse = ClientUtils.readBody(loginResponse, ServerFacade.AuthResponse.class);
        HttpURLConnection response = serverFacade.logout(authResponse.authToken());
        assertEquals(200, response.getResponseCode());
    }

    @Test
    @Order(9)
    void testListGamesSuccess() throws Exception {
        HttpURLConnection loginResponse = serverFacade.login("UserA", "password123");
        ServerFacade.AuthResponse authResponse = ClientUtils.readBody(loginResponse, ServerFacade.AuthResponse.class);
        serverFacade.create("ChessGame", authResponse.authToken());
        HttpURLConnection response = serverFacade.list(authResponse.authToken());
        assertEquals(200, response.getResponseCode());
    }
}
