package warre.me.backend.game.api.dto;

import warre.me.backend.chared.domain.dice.Dices;
import warre.me.backend.game.domain.gamePlayer.Action;
import warre.me.backend.game.domain.gamePlayer.GamePlayer;

import java.util.List;
import java.util.UUID;

public record PlayerDataDto(
        UUID id,
        Dices dices,
        int money,
        int place,
        List<OwnPropertyDto> ownsCards,
        int movePlace,
        boolean isBankrupt,
        Action action
) {
    public static PlayerDataDto fromDomain(GamePlayer gamePlayer) {
        return new PlayerDataDto(
                gamePlayer.getPlayerId().id(),
                gamePlayer.getDices(),
                gamePlayer.getMoney(),
                gamePlayer.getPlace(),
                gamePlayer.getListOfOwnProperty().stream()
                        .map((ownProperty -> OwnPropertyDto.fromDomain(ownProperty, gamePlayer)))
                        .toList(),
                gamePlayer.getMovePlace(),
                gamePlayer.isBankrupt(),
                gamePlayer.getAction()
        );
    }
}
