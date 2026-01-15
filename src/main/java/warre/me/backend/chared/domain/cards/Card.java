package warre.me.backend.chared.domain.cards;

import lombok.Getter;
import warre.me.backend.chared.domain.cards.cardTypes.CardSpecificType;
import warre.me.backend.chared.domain.cards.cardTypes.CardType;
import warre.me.backend.game.domain.game.Game;
import warre.me.backend.game.domain.gamePlayer.GamePlayer;

@Getter
public abstract class Card {
    private final String name;
    private final CardSpecificType cardSpecificType;
    private final DeckType deckType;

    protected Card(String name, CardSpecificType cardSpecificType, DeckType deckType) {
        this.name = name;
        this.cardSpecificType = cardSpecificType;
        this.deckType = deckType;
    }

    public abstract void doThingUseCard(Game game, GamePlayer gamePlayer);


    public boolean canSave() {
        return false;
    }

    public abstract CardType getCardType();

    public int getMoneyToPay() {
        return 0;
    }
}
