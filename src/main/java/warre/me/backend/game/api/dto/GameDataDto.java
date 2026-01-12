package warre.me.backend.game.api.dto;

import warre.me.backend.game.domain.game.Game;

import java.util.List;
import java.util.UUID;

public record GameDataDto(
        UUID id,
        List<PlayerDataDto> users,
        PlayerDataDto currentUser
) {
    public static GameDataDto fromDomain(Game game) {
        return new GameDataDto(
                game.getGameId().id(),
                game.getPlayers().stream()
                        .map(PlayerDataDto::fromDomain)
                        .toList(),
                PlayerDataDto.fromDomain(game.getPlayerById(game.getCurrentPlayer()))
        );
    }
}
