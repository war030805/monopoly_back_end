package warre.me.backend.game.infrastructure.gamePlayer.jpa;

import jakarta.persistence.*;
import warre.me.backend.chared.domain.cards.Card;
import warre.me.backend.chared.domain.cards.cardTypes.CardType;
import warre.me.backend.chared.domain.cards.chance.ChanceCards;
import warre.me.backend.chared.domain.cards.communityChest.CommunityChestCards;
import warre.me.backend.game.domain.game.GameId;
import warre.me.backend.game.domain.gamePlayer.Action;
import warre.me.backend.game.domain.gamePlayer.GamePlayer;
import warre.me.backend.player.domain.PlayerId;
import warre.me.backend.game.domain.property.OwnProperty;
import warre.me.backend.game.infrastructure.ownProperty.jpa.JpaOwnPropertyEntity;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Entity
@Table(name = "game_players")
public class JpaGamePlayerEntity {

    @EmbeddedId
    private JpaGamePlayerId gamePlayerId;

    @Column(nullable = false)
    private int money= 1500;

    @Column(nullable = false)
    private int place;

    @MapsId("player_id")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JpaOwnPropertyEntity> ownsProperties;

    @Column(nullable = false)
    private int movePlace;

    @Column(nullable = false)
    private boolean isBankrupt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Action action;

    @Column(nullable = false)
    private String color;

    @ElementCollection
    private List<CardType> chanceCardsOwned;

    @ElementCollection
    private List<CardType> communityChestCardsOwned;

    protected JpaGamePlayerEntity() {
    } // for JPA

    public JpaGamePlayerEntity(JpaGamePlayerId gamePlayerId, int money, int place,
                               List<JpaOwnPropertyEntity> ownsProperties, int movePlace, boolean isBankrupt,
                               Action action, String color, List<CardType> chanceCardsOwned, List<CardType> communityChestCardsOwned) {
        this.gamePlayerId = gamePlayerId;
        this.money = money;
        this.place = place;
        this.ownsProperties = ownsProperties;
        this.movePlace = movePlace;
        this.isBankrupt = isBankrupt;
        this.action = action;
        this.color= color;
        this.chanceCardsOwned = chanceCardsOwned;
        this.communityChestCardsOwned = communityChestCardsOwned;
    }

    public static JpaGamePlayerEntity fromDomain(GamePlayer gamePlayer, GameId gameId) {
        return new JpaGamePlayerEntity(
                new JpaGamePlayerId(gamePlayer.getPlayerId(), gameId),
                gamePlayer.getMoney(),
                gamePlayer.getPlace(),
                gamePlayer.getListOfOwnProperty().stream()
                        .map(JpaOwnPropertyEntity::fromDomain)
                        .toList(),
                gamePlayer.getMovePlace(),
                gamePlayer.isBankrupt(),
                gamePlayer.getAction(),
                gamePlayer.getColor(),
                gamePlayer.getChanceCardsOwned().stream()
                        .map(Card::getCardType)
                        .toList(),
                gamePlayer.getCommunityChestCardsOwned().stream()
                        .map(Card::getCardType)
                        .toList()
        );
    }


    public GamePlayer toDomain() {
        return new GamePlayer(
                new PlayerId(gamePlayerId.getUserId()),
                ownsProperties.stream()
                        .map(JpaOwnPropertyEntity::toDomain)
                        .collect(Collectors.toMap(OwnProperty::getProperty, Function.identity())),
                movePlace,
                money,
                place,
                isBankrupt,
                action,
                color,
                chanceCardsOwned.stream()
                        .map(ChanceCards::getCard)
                        .toList(),
                communityChestCardsOwned.stream()
                        .map(CommunityChestCards::getCard)
                        .toList()
        );
    }
}
