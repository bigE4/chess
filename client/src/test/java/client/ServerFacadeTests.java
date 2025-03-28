package client;

import dataaccess.AuthSQLDAO;
import dataaccess.GameSQLDAO;
import dataaccess.UserSQLDAO;
import exceptions.DataAccessException;
import org.junit.jupiter.api.*;
import server.Server;

import java.net.HttpURLConnection;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade serverFacade;
    static UserSQLDAO uDAO = new UserSQLDAO();
    static AuthSQLDAO aDAO = new AuthSQLDAO();
    static GameSQLDAO gDAO = new GameSQLDAO();

    @BeforeAll
    public static void init() throws DataAccessException {
        server = new Server();
        var port = server.run(0);
        serverFacade = new ServerFacade("http://localhost:" + port);
        System.out.println("Started test HTTP server on " + port);
        uDAO.clearUsers();
        aDAO.clearAuth();
        gDAO.clearGames();
    }

    @AfterAll
    static void stopServer() throws DataAccessException {
        server.stop();
        uDAO.clearUsers();
        aDAO.clearAuth();
        gDAO.clearGames();
    }

    @Test
    @Order(1)
    void testRegisterSuccess() throws Exception {
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
    void testCreateGameFail() throws Exception {
        HttpURLConnection response = serverFacade.create("ChessGame", "Random Token");
        assertEquals(401, response.getResponseCode());
    }

    @Test
    @Order(7)
    void testJoinGameSuccess() throws Exception {
        HttpURLConnection loginResponse = serverFacade.login("UserA", "password123");
        ServerFacade.AuthResponse authResponse = ClientUtils.readBody(loginResponse, ServerFacade.AuthResponse.class);
        HttpURLConnection createResponse = serverFacade.create("ChessGame", authResponse.authToken());
        int gameID = ClientUtils.readBody(createResponse, ServerFacade.CreateResponse.class).gameID();
        HttpURLConnection response = serverFacade.join("WHITE", String.valueOf(gameID), authResponse.authToken());
        assertEquals(200, response.getResponseCode());
    }

    @Test
    @Order(8)
    void testJoinGameFail() throws Exception {
        HttpURLConnection loginResponse = serverFacade.login("UserA", "password123");
        ServerFacade.AuthResponse authResponse = ClientUtils.readBody(loginResponse, ServerFacade.AuthResponse.class);
        HttpURLConnection response = serverFacade.join("White", "999", authResponse.authToken());
        assertEquals(400, response.getResponseCode());
    }

    @Test
    @Order(9)
    void testLogoutSuccess() throws Exception {
        HttpURLConnection loginResponse = serverFacade.login("UserA", "password123");
        ServerFacade.AuthResponse authResponse = ClientUtils.readBody(loginResponse, ServerFacade.AuthResponse.class);
        HttpURLConnection response = serverFacade.logout(authResponse.authToken());
        assertEquals(200, response.getResponseCode());
    }

    @Test
    @Order(10)
    void testLogoutFailure() throws Exception {
        HttpURLConnection response = serverFacade.logout("Random Token");
        assertEquals(401, response.getResponseCode());
    }

    @Test
    @Order(11)
    void testListGamesSuccess() throws Exception {
        HttpURLConnection loginResponse = serverFacade.login("UserA", "password123");
        ServerFacade.AuthResponse authResponse = ClientUtils.readBody(loginResponse, ServerFacade.AuthResponse.class);
        serverFacade.create("ChessGame", authResponse.authToken());
        HttpURLConnection response = serverFacade.list(authResponse.authToken());
        assertEquals(200, response.getResponseCode());
    }

    @Test
    @Order(12)
    void testListGamesFailure() throws Exception {
        HttpURLConnection response = serverFacade.list("Random Token");
        assertEquals(401, response.getResponseCode());
    }

    @Test
    @Order(13)
    void testMakeRequestSuccess() throws Exception {
        assertDoesNotThrow(() -> {
            serverFacade.makeRequest("DELETE", "/db", null, null);
        });
    }

    @Test
    @Order(43)
    void testMakeRequestFail() {
        assertThrows(RuntimeException.class, () ->
                serverFacade.makeRequest("Bad", "Para", "meters", "& Token"));
    }

    @Test
    @Order(15)
    void testWriteBodySuccess() throws Exception {
        HttpURLConnection loginResponse = serverFacade.login("UserA", "password123");
        ServerFacade.AuthResponse authResponse = ClientUtils.readBody(loginResponse, ServerFacade.AuthResponse.class);
        HttpURLConnection response = serverFacade.create("TestGame", authResponse.authToken());
        assertEquals(200, response.getResponseCode());
    }

    @Test
    @Order(16)
    void testWriteBodyFail() {
        assertThrows(RuntimeException.class, () ->
                serverFacade.writeBody("Bad Parameters", null));
    }
}
