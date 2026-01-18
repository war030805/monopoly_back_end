package warre.me.backend.shared.domain.cards.cardTypes;

import warre.me.backend.shared.domain.board.Board;
import warre.me.backend.shared.domain.board.property.StreetType;
import warre.me.backend.shared.domain.cards.Card;
import warre.me.backend.shared.domain.cards.DeckType;
import warre.me.backend.game.domain.game.Game;
import warre.me.backend.game.domain.gamePlayer.GamePlayer;

public class AdvanceToNearestStreetTypeCard extends Card {
    private final StreetType streetType;
    public AdvanceToNearestStreetTypeCard(String name, CardSpecificType cardSpecificType, StreetType streetType, DeckType deckType) {
        super(name, cardSpecificType, deckType);
        this.streetType= streetType;
    }

    @Override
    public void doThingUseCard(Game game, GamePlayer gamePlayer) {
        gamePlayer.goToPlaceCard(calcPlaceEndsUpOn(gamePlayer));
    }

    @Override
    public CardType getCardType() {
        return CardType.MOVER;
    }

    @Override
    public int calcPlaceEndsUpOn(GamePlayer gamePlayer) {
        return Board.getNearestStreetTypePlace(gamePlayer.getPlace(), streetType);
    }
}
