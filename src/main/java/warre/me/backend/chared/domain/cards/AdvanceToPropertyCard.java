package warre.me.backend.chared.domain.cards;

import warre.me.backend.chared.domain.board.Board;
import warre.me.backend.chared.domain.board.property.Property;
import warre.me.backend.game.domain.gamePlayer.GamePlayer;

public class AdvanceToPropertyCard extends Card {
    private final int placeToMove;
    protected AdvanceToPropertyCard(String name, CardType cardType, Property property) {
        super(name, cardType);
        placeToMove= Board.getPlaceOfProperty(property);
    }

    @Override
    void doThingLandOnCard(GamePlayer gamePlayer) {
        gamePlayer.goToPlace(placeToMove);
    }
}
