package response;

import model.GameDataDTO;

import java.util.List;

public record ListGamesResponse(List<GameDataDTO> games) {
}
