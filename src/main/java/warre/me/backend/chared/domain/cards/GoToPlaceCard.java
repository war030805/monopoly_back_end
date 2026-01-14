package warre.me.backend.chared.domain.cards;

import warre.me.backend.game.domain.game.Game;
import warre.me.backend.game.domain.gamePlayer.GamePlayer;

public class GoToPlaceCard extends Card {
    private final int place;
    private final boolean toPrison;
    public GoToPlaceCard(String name, CardType cardType, int place, boolean toPrison) {
        super(name, cardType);
        this.place = place;
        this.toPrison = toPrison;
    }

    @Override
    void doThingUseCard(Game game, GamePlayer gamePlayer) {
        if (toPrison) {
            gamePlayer.goToPrison();
        } else {
            gamePlayer.goToPlace(place);
        }
    }
}
