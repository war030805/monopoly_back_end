package warre.me.backend.chared.domain.cards.cardTypes;

import warre.me.backend.chared.domain.cards.Card;
import warre.me.backend.chared.domain.cards.DeckType;
import warre.me.backend.game.domain.game.Game;
import warre.me.backend.game.domain.gamePlayer.GamePlayer;

public class GetOutOfJailCard extends Card {

    public GetOutOfJailCard(String name, DeckType deckType) {
        super(name, CardSpecificType.GET_OUT_OF_JAIL_FREE, deckType);
    }

    @Override
    public void doThingUseCard(Game game, GamePlayer gamePlayer) {
        gamePlayer.goOutOfJail();
    }

    @Override
    public boolean canSave() {
        return true;
    }

    @Override
    public CardType getCardType() {
        return CardType.GET_OUT_OF_JAIL_FREE;
    }
}
