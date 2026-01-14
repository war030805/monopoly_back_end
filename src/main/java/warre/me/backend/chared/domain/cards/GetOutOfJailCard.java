package warre.me.backend.chared.domain.cards;

import warre.me.backend.game.domain.game.Game;
import warre.me.backend.game.domain.gamePlayer.GamePlayer;

public class GetOutOfJailCard extends Card {

    protected GetOutOfJailCard(String name) {
        super(name, CardType.GET_OUT_OF_JAIL_FREE);
    }

    @Override
    void doThingUseCard(Game game, GamePlayer gamePlayer) {
        gamePlayer.goOutOfJail();
    }
}
