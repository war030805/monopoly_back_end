package warre.me.backend.game.domain.game;

import lombok.Getter;
import warre.me.backend.chared.domain.NotFoundException;
import warre.me.backend.chared.domain.dice.Dices;
import warre.me.backend.game.domain.gamePlayer.GamePlayer;
import warre.me.backend.player.domain.PlayerId;
import warre.me.backend.lobby.domain.lobby.LobbyId;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static warre.me.backend.chared.domain.board.Board.getPropertyFromPlace;


public class Game {
    @Getter
    private final GameId gameId;

    @Getter
    private final LobbyId lobbyFromGame;

    private final Map<PlayerId, GamePlayer> players;

    @Getter
    private PlayerId currentPlayer;

    @Getter
    private Dices dices;


    public Game(GameId gameId, LobbyId lobbyFromGame, Map<PlayerId, GamePlayer> players, PlayerId currentPlayer) {
        this.gameId = gameId;
        this.lobbyFromGame = lobbyFromGame;
        this.players = players;
        this.currentPlayer = currentPlayer;
        dices=Dices.trowDices();
    }


    public void trowDicesByPlayer(PlayerId playerId) {
        getCurrentPlayerAndCheckIsPlayer(playerId);
        dices=Dices.trowDices();
    }

    private GamePlayer getCurrentPlayerAndCheckIsPlayer(PlayerId playerId) {
        checkIsMover(playerId);
        return getPlayerById(playerId);
    }

    public void isInGame(PlayerId playerId) {
        Optional.ofNullable(players.get(playerId))
                .orElseThrow(playerId::notFound);
    }


    public void checkIsMover(PlayerId playerId) {
        if (!playerId.equals(currentPlayer)) {
            throw new GameException("player is not current player");
        }
    }

    public List<GamePlayer> getPlayers() {
        return players.values()
                .stream()
                .sorted()
                .toList();
    }

    public GamePlayer getPlayerById(PlayerId playerId) {
        return Optional.ofNullable(players.get(playerId))
                .orElseThrow(playerId::notFound);
    }

    public void buyProperty(PlayerId playerId) {
        var player= getCurrentPlayerAndCheckIsPlayer(playerId);

        var ownsProperty=ownsProperty(player);

        if (ownsProperty) {
            throw new BuyException("you cannot buy property that is owned");
        }

        player.buyProperty();
    }

    /**
     *
     * @param playerId
     */
    public void payLandOnOtherProperty(PlayerId playerId) {
        var player= getCurrentPlayerAndCheckIsPlayer(playerId);

        var owner= whoOwnsProperty(player);

        player.payLandOnOtherProperty(owner, dices);

    }

    private boolean ownsProperty(GamePlayer player) {
        return getPlayers().stream()
                .anyMatch(otherPlayer -> otherPlayer.ownsProperty(getPropertyFromPlace(player.getPlace())));
    }

    private GamePlayer whoOwnsProperty(GamePlayer gamePlayer) {
        return getPlayers().stream()
                .filter(player -> player.ownsProperty(getPropertyFromPlace(gamePlayer.getPlace())))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("nobody owns property"));
    }

    public void move(PlayerId playerId) {
        var player= getCurrentPlayerAndCheckIsPlayer(playerId);

        player.move(dices);
    }
}
