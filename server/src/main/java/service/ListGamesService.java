package service;

import dataaccess.AuthDatabaseDAO;
import dataaccess.GameDatabaseDAO;
import exceptions.UnauthorizedException;
import model.GameData;
import request.ListGamesRequest;
import model.GameDataDTO;
import response.ListGamesResponse;

import java.util.ArrayList;
import java.util.List;

public class ListGamesService {
    AuthDatabaseDAO aDAO = new AuthDatabaseDAO();
    GameDatabaseDAO gDAO = new GameDatabaseDAO();
    public ListGamesResponse ListGames(ListGamesRequest listGamesRequest) throws Exception {
        String authToken = listGamesRequest.authToken();
        if (!aDAO.AuthenticateAuth(authToken)) { throw new UnauthorizedException("Error: unauthorized"); }
        try {
            List<GameData> games = gDAO.RetrieveGames();
            List<GameDataDTO> gameDTOs = new ArrayList<>();
            for (GameData gameData: games) {
                gameDTOs.add(new GameDataDTO(gameData));
            }
            return new ListGamesResponse(gameDTOs);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
