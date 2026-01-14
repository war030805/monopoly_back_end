package warre.me.backend.game.infrastructure.game.jpa;

import jakarta.persistence.*;
import warre.me.backend.chared.domain.cards.Card;
import warre.me.backend.chared.domain.cards.CardType;
import warre.me.backend.chared.domain.cards.chance.ChanceCards;
import warre.me.backend.chared.domain.cards.communityChest.CommunityChestCards;
import warre.me.backend.chared.domain.dice.Dices;
import warre.me.backend.game.domain.game.Game;
import warre.me.backend.game.domain.game.GameId;
import warre.me.backend.game.domain.gamePlayer.GamePlayer;
import warre.me.backend.game.infrastructure.gamePlayer.jpa.JpaGamePlayerEntity;
import warre.me.backend.lobby.domain.lobby.LobbyId;
import warre.me.backend.player.domain.PlayerId;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Entity
@Table(name = "games")
public class JpaGameEntity {

    @Id
    private UUID id;

    private UUID lobbyFromGame;

    @MapsId("gameId") // gebruikt gameId uit embedded id
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "game_id")
    private List<JpaGamePlayerEntity> players;

    @ElementCollection
    private List<CardType> chanceCards;

    @ElementCollection
    private List<CardType> communityChestCards;

    @Column(nullable = false)
    private UUID currentPlayer;

    @Column(nullable = false)
    private int dice1, dice2;

    protected JpaGameEntity() {
    } // for JPA

    public JpaGameEntity(UUID id, UUID lobbyFromGame, List<JpaGamePlayerEntity> players, List<CardType> chanceCards, List<CardType> communityChestCards, UUID currentPlayer, Dices dices) {
        this.id = id;
        this.players = players;
        this.currentPlayer = currentPlayer;
        this.chanceCards = chanceCards;
        this.communityChestCards = communityChestCards;
        this.lobbyFromGame = lobbyFromGame;
        this.dice1 = dices.dice1();
        this.dice2 = dices.dice2();
    }


    public static JpaGameEntity fromDomain(Game game) {
        return new JpaGameEntity(
                game.getGameId().id(),
                game.getLobbyFromGame().id(),
                game.getPlayers().stream()
                        .map(gamePlayer -> JpaGamePlayerEntity.fromDomain(gamePlayer, game.getGameId()))
                        .toList(),
                game.getChanceCards().stream()
                        .map(Card::getCardType)
                        .toList(),
                game.getCommunityChestCards().stream()
                        .map(Card::getCardType)
                        .toList(),
                game.getCurrentPlayer().id(),
                game.getDices()
        );
    }


    public Game toDomain() {
        return new Game(
                new GameId(id),
                new LobbyId(lobbyFromGame),
                players.stream()
                        .map(JpaGamePlayerEntity::toDomain)
                        .collect(Collectors.toMap(GamePlayer::getPlayerId, Function.identity())),
                chanceCards.stream()
                        .map(ChanceCards::getCard)
                        .toList(),
                communityChestCards.stream()
                        .map(CommunityChestCards::getCard)
                        .toList(),
                new PlayerId(currentPlayer),
                new Dices(dice1, dice2)
        );
    }
}
