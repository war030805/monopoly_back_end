package warre.me.backend.chared.domain.cards.cardTypes;

import warre.me.backend.chared.domain.cards.Card;
import warre.me.backend.chared.domain.cards.DeckType;
import warre.me.backend.game.domain.game.Game;
import warre.me.backend.game.domain.gamePlayer.GamePlayer;

public class MoveRelativeCard extends Card {
    private final int places;
    public MoveRelativeCard(String name, CardType cardType, int places, DeckType deckType) {
        super(name, cardType, deckType);
        this.places=places;
    }

    @Override
    public void doThingUseCard(Game game, GamePlayer gamePlayer) {
        gamePlayer.addToPlace(places);
    }
}
