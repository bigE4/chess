package service;

import dataaccess.AuthDatabaseDAO;
import dataaccess.GameDatabaseDAO;
import exceptions.UnauthorizedException;
import model.GameData;
import request.ListGamesRequest;
import model.GameDataDTO;
import response.ListGamesResponse;

import java.util.List;

public class ListGamesService {

    AuthDatabaseDAO aDAO = new AuthDatabaseDAO();
    GameDatabaseDAO gDAO = new GameDatabaseDAO();

    public ListGamesResponse listGames(ListGamesRequest listGamesRequest) throws Exception {
        String authToken = listGamesRequest.authToken();

        if (!aDAO.AuthenticateAuth(authToken)) {
            throw new UnauthorizedException("Error: unauthorized");
        }

        try {
            // Retrieve games and map them to DTOs
            List<GameData> games = gDAO.RetrieveGames();
            List<GameDataDTO> gameDTOs = games.stream().map(GameDataDTO::new).toList();
            return new ListGamesResponse(gameDTOs);
        } catch (UnauthorizedException e) {
            throw new UnauthorizedException("Error: unauthorized");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
