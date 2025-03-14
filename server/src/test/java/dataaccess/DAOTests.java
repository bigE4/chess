package dataaccess;

import exceptions.DataAccessException;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DAOTests {
    private UserSQLDAO userDAO;
    private GameSQLDAO gameDAO;
    private AuthSQLDAO authDAO;

    @BeforeEach
    void setUp() throws Exception {
        DatabaseManager.createDatabase();
        DatabaseManager.createTables();
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
}
