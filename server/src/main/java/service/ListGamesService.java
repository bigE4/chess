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
    public ListGamesResponse listGames(ListGamesRequest listGamesRequest) throws Exception {
        AuthDatabaseDAO aDAO = new AuthDatabaseDAO();
        GameDatabaseDAO gDAO = new GameDatabaseDAO();
        if (ServiceUtils.isABadToken(aDAO, listGamesRequest)) { throw new UnauthorizedException("Error: unauthorized"); }
        try {
            List<GameDataDTO> gameDTOs = new ArrayList<>();
            for (GameData gameData: gDAO.retrieveGames()) {
                gameDTOs.add(new GameDataDTO(gameData));
            }
            return new ListGamesResponse(gameDTOs);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
