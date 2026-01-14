package warre.me.backend.chared.domain.cards;

import warre.me.backend.chared.domain.board.Board;
import warre.me.backend.chared.domain.board.property.StreetType;
import warre.me.backend.game.domain.game.Game;
import warre.me.backend.game.domain.gamePlayer.GamePlayer;
import warre.me.backend.player.domain.PlayerId;

public class AdvanceToNearestStreetTypeCard extends Card {
    private final StreetType streetType;
    public AdvanceToNearestStreetTypeCard(String name, CardType cardType, StreetType streetType) {
        super(name, cardType);
        this.streetType= streetType;
    }

    @Override
    void doThingUseCard(Game game, GamePlayer gamePlayer) {
        var placeToMove= Board.getNearestStreetTypePlace(gamePlayer.getPlace(), streetType);
        gamePlayer.goToPlace(placeToMove);
    }
}
