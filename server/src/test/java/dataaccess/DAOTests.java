package dataaccess;

import dataaccess.dao.AuthSQLDAO;
import dataaccess.dao.GameSQLDAO;
import dataaccess.dao.UserSQLDAO;
import exceptions.DataAccessException;
import records.AuthData;
import records.GameData;
import records.UserData;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DAOTests {
    private UserSQLDAO userDAO;
    private GameSQLDAO gameDAO;
    private AuthSQLDAO authDAO;

    @BeforeEach
    void setUp() throws Exception {
        SQLDatabaseManager.createDatabase();
        SQLDatabaseManager.createTables();
        userDAO = new UserSQLDAO();
        gameDAO = new GameSQLDAO();
        authDAO = new AuthSQLDAO();
        userDAO.clearUsers();
        gameDAO.clearGames();
        authDAO.clearAuth();
    }

    @Test
    @Order(1)
    void testStoreUserSuccess() {
        assertDoesNotThrow(() -> userDAO.storeUser(new UserData("UserA", "password123", "userA@email.com")));
    }

    @Test
    @Order(2)
    void testStoreUserDuplicate() throws Exception {
        userDAO.storeUser(new UserData("UserA", "password123", "userA@email.com"));
        assertThrows(DataAccessException.class, () -> userDAO.storeUser(new UserData("UserA", "password123", "userA@email.com")));
    }

    @Test
    @Order(3)
    void testUserExistsTrue() throws Exception {
        userDAO.storeUser(new UserData("UserA", "password123", "userA@email.com"));
        assertTrue(userDAO.userExists("UserA"));
    }

    @Test
    @Order(4)
    void testUserExistsFalse() throws Exception {
        assertFalse(userDAO.userExists("NonExistentUser"));
    }

    @Test
    @Order(5)
    void testAuthenticateUserSuccess() throws Exception {
        userDAO.storeUser(new UserData("UserA", "password123", "userA@email.com"));
        assertTrue(userDAO.authenticateUser("UserA", "password123"));
    }

    @Test
    @Order(6)
    void testAuthenticateUserFail() throws Exception {
        userDAO.storeUser(new UserData("UserA", "password123", "userA@email.com"));
        assertFalse(userDAO.authenticateUser("UserA", "badPassword"));
    }

    @Test
    @Order(7)
    void testStoreGameSuccess() {
        GameData game = new GameData(1, "WhitePlayer", "BlackPlayer", "ChessGame", new chess.ChessGame());
        assertDoesNotThrow(() -> gameDAO.storeGame(game));
    }

    @Test
    @Order(8)
    void testStoreGameDuplicate() throws Exception {
        GameData game = new GameData(1, "WhitePlayer", "BlackPlayer", "ChessGame", new chess.ChessGame());
        gameDAO.storeGame(game);
        assertThrows(DataAccessException.class, () -> gameDAO.storeGame(game));
    }

    @Test
    @Order(9)
    void testGameExistsTrue() throws Exception {
        gameDAO.storeGame(new GameData(1, "WhitePlayer", "BlackPlayer", "ChessGame", new chess.ChessGame()));
        assertTrue(gameDAO.gameExists(1));
    }

    @Test
    @Order(10)
    void testGameExistsFalse() throws Exception {
        assertFalse(gameDAO.gameExists(999));
    }

    @Test
    @Order(11)
    void testRetrieveGameSuccess() throws Exception {
        GameData game = new GameData(1, "WhitePlayer", "BlackPlayer", "ChessGame", new chess.ChessGame());
        gameDAO.storeGame(game);
        assertNotNull(gameDAO.retrieveGame(1));
    }

    @Test
    @Order(12)
    void testRetrieveGameFail() throws Exception {
        assertNull(gameDAO.retrieveGame(999));
    }

    @Test
    @Order(13)
    void testRetrieveGamesEmpty() throws Exception {
        List<GameData> games = gameDAO.retrieveGames();
        assertTrue(games.isEmpty());
    }

    @Test
    @Order(14)
    void testStoreAuthSuccess() {
        assertDoesNotThrow(() -> authDAO.storeAuth(new AuthData("auth123", "UserA")));
    }

    @Test
    @Order(15)
    void testAuthenticateAuthSuccess() throws Exception {
        authDAO.storeAuth(new AuthData("auth123", "UserA"));
        assertTrue(authDAO.authenticateAuth("auth123"));
    }

    @Test
    @Order(16)
    void testAuthenticateAuthFail() throws Exception {
        assertFalse(authDAO.authenticateAuth("badToken"));
    }

    @Test
    @Order(17)
    void testRetrieveAuthSuccess() throws Exception {
        authDAO.storeAuth(new AuthData("auth123", "UserA"));
        assertNotNull(authDAO.retrieveAuth("auth123"));
    }

    @Test
    @Order(18)
    void testRetrieveAuthFail() throws Exception {
        assertNull(authDAO.retrieveAuth("badToken"));
    }

    @Test
    @Order(19)
    void testDeleteAuthSuccess() throws Exception {
        authDAO.storeAuth(new AuthData("auth123", "UserA"));
        assertDoesNotThrow(() -> authDAO.deleteAuth("auth123"));
    }

    @Test
    @Order(20)
    void testDeleteAuthFail() throws Exception {
        assertDoesNotThrow(() -> authDAO.deleteAuth("badToken"));
    }
}
