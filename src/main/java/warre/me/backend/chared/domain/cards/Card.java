package warre.me.backend.chared.domain.cards;

import lombok.Getter;
import warre.me.backend.game.domain.game.Game;
import warre.me.backend.game.domain.gamePlayer.GamePlayer;

@Getter
public abstract class Card {
    private final String name;
    private final CardType cardType;

    protected Card(String name, CardType cardType) {
        this.name = name;
        this.cardType = cardType;
    }

    abstract void doThingUseCard(Game game, GamePlayer gamePlayer);


}
