package dataaccess;

import exceptions.DataAccessException;
import model.UserData;
import org.junit.jupiter.api.*;

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

}
