package warre.me.backend.game.api.dto;

import warre.me.backend.shared.domain.board.Board;
import warre.me.backend.shared.domain.board.tile.TileType;
import warre.me.backend.shared.domain.dice.Dices;
import warre.me.backend.game.domain.game.Game;
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
        TileType onTileTileType,
        List<CardPlayerInfoDto> ownedCards,
        CardPlayerInfoDto cardGot,
        boolean canPay,
        List<Action> actionsDone,
        boolean inPrison,
        int movesInPrison
) {
    public static PlayerDataDto fromDomain(GamePlayer gamePlayer, Dices dices, Game game) {
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
                Board.getTileTypeFromPlace(gamePlayer.getPlace()),
                gamePlayer.getOwnedCards().stream()
                        .map(card -> CardPlayerInfoDto.fromDomain(card, gamePlayer, game))
                        .toList(),
                gamePlayer.getCardGotOptional()
                        .map(card -> CardPlayerInfoDto.fromDomain(card, gamePlayer, game))
                        .orElse(null),
                gamePlayer.canPlayCurrentAction(game),
                gamePlayer.getActionsDone(),
                gamePlayer.isInPrison(),
                gamePlayer.getMovesInPrison()
        );
    }
}
