package warre.me.backend.chared.domain.cards.cardTypes;

import warre.me.backend.chared.domain.cards.Card;
import warre.me.backend.chared.domain.cards.DeckType;
import warre.me.backend.game.domain.game.Game;
import warre.me.backend.game.domain.gamePlayer.GamePlayer;

public class GoToPlaceCard extends Card {
    private final int place;
    private final boolean toPrison;
    public GoToPlaceCard(String name, CardType cardType, int place, boolean toPrison, DeckType deckType) {
        super(name, cardType, deckType);
        this.place = place;
        this.toPrison = toPrison;
    }

    @Override
    public void doThingUseCard(Game game, GamePlayer gamePlayer) {
        if (toPrison) {
            gamePlayer.goToPrison();
        } else {
            gamePlayer.goToPlace(place);
        }
    }
}
