package service;

import exceptions.AlreadyTakenException;
import exceptions.BadRequestException;
import exceptions.UnauthorizedException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import request.*;

import static org.junit.jupiter.api.Assertions.*;

public class ServerTests {
    private RegisterService registerService;
    private LoginService loginService;
    private LogoutService logoutService;
    private ListGamesService listGamesService;
    private CreateGameService createGameService;
    private JoinGameService joinGameService;
    private static ClearService clearService;

    @BeforeEach
    void setUp() throws Exception {
        registerService = new RegisterService();
        loginService = new LoginService();
        logoutService = new LogoutService();
        listGamesService = new ListGamesService();
        createGameService = new CreateGameService();
        joinGameService = new JoinGameService();
        clearService = new ClearService();
        clearService.Clear();
    }

    @AfterAll
    static void shutDown() throws Exception {
        clearService.Clear();
    }

    @Test
    @Order(1)
    void testRegistration() {
        RegisterRequest request = new RegisterRequest("UserA", "UserA.Password", "UserA@email.com");
        assertDoesNotThrow(() -> registerService.Register(request));
    }

    @Test
    @Order(2)
    void testUsernameAlreadyTaken() throws Exception {
        RegisterRequest request = new RegisterRequest("UserA", "UserA.Password", "UserA@email.com");
        registerService.Register(request);
        Exception exception = assertThrows(AlreadyTakenException.class, () -> registerService.Register(request));
        assertEquals("Error: already taken", exception.getMessage());
    }

    @Test
    @Order(3)
    void testBadRegistrationRequest() {
        RegisterRequest request = new RegisterRequest("", "UserB.Password", "UserB@email.com");
        Exception exception = assertThrows(BadRequestException.class, () -> registerService.Register(request));
        assertEquals("Error: bad request", exception.getMessage());
    }

    @Test
    @Order(4)
    void testLogin() throws Exception {
        registerService.Register(new RegisterRequest("UserA", "UserA.Password", "UserA@email.com"));
        assertDoesNotThrow(() -> loginService.Login(new LoginRequest("UserA", "UserA.Password")));
    }

    @Test
    @Order(5)
    void testUnauthorizedLogin() throws Exception {
        registerService.Register(new RegisterRequest("UserA", "UserA.Password", "UserA@email.com"));
        Exception exception = assertThrows(UnauthorizedException.class, () -> loginService.Login(new LoginRequest("UserA", "WrongPassword")));
        assertEquals("Error: unauthorized", exception.getMessage());
    }

    @Test
    @Order(6)
    void testLogout() throws Exception {
        String validToken = registerService.Register(new RegisterRequest("UserA", "UserA.Password", "UserA@email.com")).authToken();
        assertDoesNotThrow(() -> logoutService.Logout(new LogoutRequest(validToken)));
    }

    @Test
    @Order(7)
    void testUnauthorizedLogout() throws Exception {
        registerService.Register(new RegisterRequest("UserA", "UserA.Password", "UserA@email.com"));
        Exception exception = assertThrows(UnauthorizedException.class, () -> logoutService.Logout(new LogoutRequest("badToken")));
        assertEquals("Error: unauthorized", exception.getMessage());
    }

    @Test
    @Order(8)
    void testListGames() throws Exception {
        String validToken = registerService.Register(new RegisterRequest("UserA", "UserA.Password", "UserA@email.com")).authToken();
        assertDoesNotThrow(() -> listGamesService.ListGames(new ListGamesRequest(validToken)));
    }

    @Test
    @Order(9)
    void testUnauthorizedListGames() throws Exception {
        registerService.Register(new RegisterRequest("UserA", "UserA.Password", "UserA@email.com"));
        Exception exception = assertThrows(UnauthorizedException.class, () -> listGamesService.ListGames(new ListGamesRequest("badToken")));
        assertEquals("Error: unauthorized", exception.getMessage());
    }

    @Test
    @Order(10)
    void testCreateGame() throws Exception {
        String validToken = registerService.Register(new RegisterRequest("UserA", "UserA.Password", "UserA@email.com")).authToken();
        assertDoesNotThrow(() -> createGameService.CreateGame(new CreateGameRequest(validToken, "exampleGame")));
    }

    @Test
    @Order(11)
    void testUnauthorizedGameCreation() throws Exception {
        registerService.Register(new RegisterRequest("UserA", "UserA.Password", "UserA@email.com"));
        Exception exception = assertThrows(UnauthorizedException.class, () -> createGameService.CreateGame(new CreateGameRequest("badToken", "exampleGame")));
        assertEquals("Error: unauthorized", exception.getMessage());
    }

    @Test
    @Order(12)
    void testJoinGame() throws Exception {
        String validToken = registerService.Register(new RegisterRequest("UserA", "UserA.Password", "UserA@email.com")).authToken();
        int goodID = createGameService.CreateGame(new CreateGameRequest(validToken, "exampleGame")).gameID();
        assertDoesNotThrow(() -> joinGameService.JoinGame(new JoinGameRequest(validToken, "WHITE", goodID)));
    }

    @Test
    @Order(13)
    void testUnauthorizedGameJoin() throws Exception {
        String validToken = registerService.Register(new RegisterRequest("UserA", "UserA.Password", "UserA@email.com")).authToken();
        int goodID = createGameService.CreateGame(new CreateGameRequest(validToken, "exampleGame")).gameID();
        Exception exception = assertThrows(UnauthorizedException.class, () -> joinGameService.JoinGame(new JoinGameRequest("badToken", "WHITE", goodID)));
        assertEquals("Error: unauthorized", exception.getMessage());
    }

    @Test
    @Order(14)
    void testBadJoinGameRequest() throws Exception {
        String validToken = registerService.Register(new RegisterRequest("UserA", "UserA.Password", "UserA@email.com")).authToken();
        createGameService.CreateGame(new CreateGameRequest(validToken, "exampleGame"));
        Exception exception = assertThrows(BadRequestException.class, () -> joinGameService.JoinGame(new JoinGameRequest(validToken, "BLACK", 999)));
        assertEquals("Error: bad request", exception.getMessage());
    }

    @Test
    @Order(15)
    void testClear() {
        assertDoesNotThrow(() -> clearService.Clear());
    }
}
