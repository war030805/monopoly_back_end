package warre.me.backend.game.api.dto;

import warre.me.backend.chared.domain.board.Board;
import warre.me.backend.chared.domain.board.tile.TileType;
import warre.me.backend.chared.domain.dice.Dices;
import warre.me.backend.game.domain.gamePlayer.Action;
import warre.me.backend.game.domain.gamePlayer.GamePlayer;

import java.util.List;
import java.util.UUID;

public record PlayerDataDto(
        UUID id,
        int money,
        int place,
        List<OwnPropertyDto> ownsCards,
        int movePlace,
        boolean isBankrupt,
        Action action,
        String color,
        TileType onTileTileType
) {
    public static PlayerDataDto fromDomain(GamePlayer gamePlayer, Dices dices) {
        return new PlayerDataDto(
                gamePlayer.getPlayerId().id(),
                gamePlayer.getMoney(),
                gamePlayer.getPlace(),
                gamePlayer.getListOfOwnProperty().stream()
                        .map((ownProperty -> OwnPropertyDto.fromDomain(ownProperty, gamePlayer, dices)))
                        .toList(),
                gamePlayer.getMovePlace(),
                gamePlayer.isBankrupt(),
                gamePlayer.getAction(),
                gamePlayer.getColor(),
                Board.getTileTypeFromPlace(gamePlayer.getPlace())
        );
    }
}
