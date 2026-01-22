package warre.me.backend.game.api.dto;

import warre.me.backend.shared.domain.dice.Dices;
import warre.me.backend.game.domain.game.Game;

import java.util.List;
import java.util.UUID;

public record GameDataDto(
        UUID id,
        List<PlayerDataDto> users,
        PlayerDataDto currentUser,
        Dices dices
) {
    public static GameDataDto fromDomain(Game game) {
        return new GameDataDto(
                game.getGameId().id(),
                game.getPlayers().stream()
                        .map(gamePlayer -> PlayerDataDto.fromDomain(gamePlayer, game.getDices(), game))
                        .toList(),
                PlayerDataDto.fromDomain(game.getPlayerById(game.getCurrentPlayerId()), game.getDices(), game),
                game.getDices()
        );
    }
}
