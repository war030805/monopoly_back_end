package warre.me.backend.chared.domain.cards;

import warre.me.backend.chared.domain.board.Board;
import warre.me.backend.chared.domain.board.property.StreetType;
import warre.me.backend.game.domain.gamePlayer.GamePlayer;

public class AdvanceToNearestStreetTypeCard extends Card {
    private final StreetType streetType;
    protected AdvanceToNearestStreetTypeCard(String name, CardType cardType, StreetType streetType) {
        super(name, cardType);
        this.streetType= streetType;
    }

    @Override
    void doThingLandOnCard(GamePlayer gamePlayer) {
        var placeToMove= Board.getNearestStreetTypePlace(gamePlayer.getPlace(), streetType);
        gamePlayer.goToPlace(placeToMove);
    }
}
