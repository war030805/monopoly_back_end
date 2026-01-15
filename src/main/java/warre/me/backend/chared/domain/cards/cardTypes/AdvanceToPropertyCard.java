package warre.me.backend.chared.domain.cards.cardTypes;

import warre.me.backend.chared.domain.board.Board;
import warre.me.backend.chared.domain.board.property.Property;
import warre.me.backend.chared.domain.cards.Card;
import warre.me.backend.chared.domain.cards.DeckType;
import warre.me.backend.game.domain.game.Game;
import warre.me.backend.game.domain.gamePlayer.GamePlayer;

public class AdvanceToPropertyCard extends Card {
    private final int placeToMove;
    private final Property property;
    public AdvanceToPropertyCard(CardSpecificType cardSpecificType, Property property, DeckType deckType) {
        super("", cardSpecificType, deckType);
        placeToMove= Board.getPlaceOfProperty(property);
        this.property=property;
    }

    @Override
    public void doThingUseCard(Game game, GamePlayer gamePlayer) {
        gamePlayer.goToPlace(placeToMove);
    }

    @Override
    public CardType getCardType() {
        return CardType.MOVER;
    }

    @Override
    public String getName() {
        return String.format("Advance to %s", property.name().toLowerCase().replace("_", " "));
    }
}
