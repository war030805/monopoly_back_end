package warre.me.backend.chared.domain.cards;

import warre.me.backend.game.domain.game.Game;
import warre.me.backend.game.domain.gamePlayer.GamePlayer;

public class PayEachPlayerCard extends Card {
    private final int money;
    protected PayEachPlayerCard(String name, CardType cardType, int money) {
        super(name, cardType);
        this.money = money;
    }

    @Override
    void doThingUseCard(Game game, GamePlayer gamePlayer) {
        game.payEachPlayer(gamePlayer, money);
    }
}
