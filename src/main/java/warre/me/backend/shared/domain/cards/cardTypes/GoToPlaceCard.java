package warre.me.backend.shared.domain.cards.cardTypes;

import warre.me.backend.shared.domain.cards.Card;
import warre.me.backend.shared.domain.cards.DeckType;
import warre.me.backend.game.domain.game.Game;
import warre.me.backend.game.domain.gamePlayer.GamePlayer;

public class GoToPlaceCard extends Card {
    private final int place;
    private final boolean toPrison;
    public GoToPlaceCard(String name, CardSpecificType cardSpecificType, int place, boolean toPrison, DeckType deckType) {
        super(name, cardSpecificType, deckType);
        this.place = place;
        this.toPrison = toPrison;
    }

    @Override
    public void doThingUseCard(Game game, GamePlayer gamePlayer) {
        if (toPrison) {
            gamePlayer.goToPrison();
        } else {
            gamePlayer.goToPlaceCard(place);
        }
    }

    @Override
    public CardType getCardType() {
        return CardType.MOVER;
    }

    @Override
    public int calcPlaceEndsUpOn(GamePlayer gamePlayer) {
        return place;
    }
}
