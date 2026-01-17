package warre.me.backend.game.domain.game;

import lombok.Getter;
import org.springframework.security.core.parameters.P;
import warre.me.backend.chared.domain.NotFoundException;
import warre.me.backend.chared.domain.cards.Card;
import warre.me.backend.chared.domain.cards.cardTypes.CardSpecificType;
import warre.me.backend.chared.domain.cards.chance.ChanceCards;
import warre.me.backend.chared.domain.cards.communityChest.CommunityChestCards;
import warre.me.backend.chared.domain.dice.Dices;
import warre.me.backend.game.domain.gamePlayer.DoAction;
import warre.me.backend.game.domain.gamePlayer.GamePlayer;
import warre.me.backend.lobby.domain.lobby.LobbyId;
import warre.me.backend.player.domain.PlayerId;

import java.util.LinkedList;
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
    private final LinkedList<Card> chanceCards;

    @Getter
    private final LinkedList<Card> communityChestCards;

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
        chanceCards= new LinkedList<>(ChanceCards.makeDeck());
        communityChestCards= new LinkedList<>(CommunityChestCards.makeDeck());
    }

    public Game(GameId gameId, LobbyId lobbyFromGame, Map<PlayerId, GamePlayer> players, List<Card> chanceCards,
                List<Card> communityChestCards, PlayerId currentPlayer, Dices dices) {
        this.gameId = gameId;
        this.lobbyFromGame = lobbyFromGame;
        this.players = players;
        this.chanceCards = new LinkedList<>(chanceCards);
        this.communityChestCards = new LinkedList<>(communityChestCards);
        this.currentPlayer = currentPlayer;
        this.dices = dices;
    }

    public void trowDicesByPlayer(PlayerId playerId, DoAction doAction) {
        var player=getCurrentPlayerAndCheckIsPlayer(playerId);
        player.throwDices(doAction);
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

    public List<GamePlayer> getPlayersNotBankrupt() {
        return getPlayers()
                .stream()
                .filter(gamePlayer -> !gamePlayer.isBankrupt())
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
        return getPlayersNotBankrupt().stream()
                .anyMatch(otherPlayer -> otherPlayer.ownsProperty(getPropertyFromPlace(player.getPlace())));
    }

    private GamePlayer whoOwnsProperty(GamePlayer gamePlayer) {
        return getPlayersNotBankrupt().stream()
                .filter(player -> player.ownsProperty(getPropertyFromPlace(gamePlayer.getPlace())))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("nobody owns property"));
    }

    public void move(PlayerId playerId) {
        var player= getCurrentPlayerAndCheckIsPlayer(playerId);

        player.move(dices);
    }

    public void endMove(PlayerId playerId) {
        var currentPlayer= getCurrentPlayerAndCheckIsPlayer(playerId);

        var playersSorted= getPlayersNotBankrupt();

        //als die niet bestaat dan betekent dat die de laatste is dus we zetten het terug op de eerste
        var newCurrentPlayer=playersSorted.stream()
                .filter(player -> player.getMovePlace() > currentPlayer.getMovePlace())
                .sorted()
                .findFirst()
                .orElse(playersSorted.get(0));

        currentPlayer.endMove();
        this.currentPlayer=newCurrentPlayer.getPlayerId();
    }

    public void payTax(PlayerId playerId) {
        var player= getCurrentPlayerAndCheckIsPlayer(playerId);

        player.payTax();
    }

    public void payEachPlayer(GamePlayer payer, int money) {
        getPlayersNotBankrupt().stream()
                .filter(gamePlayer -> !gamePlayer.equals(payer))
                .forEach(gamePlayer -> gamePlayer.giveMoney(money));

        payer.payMoney(money);
    }

    public int calcPayEachPlayer(int money) {
        return money * getPlayersNotBankrupt().size()-1;
    }

    public void pullCard(PlayerId playerId) {
        var player= getCurrentPlayerAndCheckIsPlayer(playerId);

        var tileType=player.getTileTypeStandingOn();

        var card= switch (tileType) {
            case COMMUNITY_CHEST -> pullCardOfCommunityChest();
            case CHANCE -> pullCardOfChance();
            default -> throw new GameException("gannot pull card when player stands on not pullable place");
        };

        player.pullCard(card);
    }

    private Card pullCardOfCommunityChest() {
        return communityChestCards.removeFirst();
    }

    public Card pullCardOfChance() {
        return chanceCards.removeFirst();
    }

    public void saveCard(PlayerId playerId) {
        var player= getCurrentPlayerAndCheckIsPlayer(playerId);

        player.saveCard();
    }

    public void useCard(PlayerId playerId) {
        var player= getCurrentPlayerAndCheckIsPlayer(playerId);

        var card=player.useCard();

        card.doThingUseCard(this, player);

        addCardToRightDeck(card);
    }

    private void addCardToRightDeck(Card card) {
        var listToAdd= switch (card.getDeckType()) {
            case COMMUNITY_CHEST -> communityChestCards;
            case CHANCE -> chanceCards;
        };

        listToAdd.add(card);
    }
    public void useSavedCard(PlayerId playerId, CardSpecificType cardSpecificType) {
        var player= getCurrentPlayerAndCheckIsPlayer(playerId);

        var card=player.useSavedCard(cardSpecificType);

        card.doThingUseCard(this, player);

        addCardToRightDeck(card);
    }
}
