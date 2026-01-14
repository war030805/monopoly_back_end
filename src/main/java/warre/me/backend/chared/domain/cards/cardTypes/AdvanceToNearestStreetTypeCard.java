package warre.me.backend.chared.domain.cards.cardTypes;

import warre.me.backend.chared.domain.board.Board;
import warre.me.backend.chared.domain.board.property.StreetType;
import warre.me.backend.chared.domain.cards.Card;
import warre.me.backend.chared.domain.cards.DeckType;
import warre.me.backend.game.domain.game.Game;
import warre.me.backend.game.domain.gamePlayer.GamePlayer;

public class AdvanceToNearestStreetTypeCard extends Card {
    private final StreetType streetType;
    public AdvanceToNearestStreetTypeCard(String name, CardType cardType, StreetType streetType, DeckType deckType) {
        super(name, cardType, deckType);
        this.streetType= streetType;
    }

    @Override
    public void doThingUseCard(Game game, GamePlayer gamePlayer) {
        var placeToMove= Board.getNearestStreetTypePlace(gamePlayer.getPlace(), streetType);
        gamePlayer.goToPlace(placeToMove);
    }
}
