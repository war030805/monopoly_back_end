package warre.me.backend.chared.domain.cards;

import warre.me.backend.game.domain.gamePlayer.GamePlayer;

public abstract class Card {
    private final String name;
    private final CardType cardType;

    protected Card(String name, CardType cardType) {
        this.name = name;
        this.cardType = cardType;
    }

    abstract void doThingLandOnCard(GamePlayer gamePlayer);


}
