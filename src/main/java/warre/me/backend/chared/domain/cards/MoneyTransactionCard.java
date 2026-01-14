package warre.me.backend.chared.domain.cards;

import warre.me.backend.game.domain.game.Game;
import warre.me.backend.game.domain.gamePlayer.GamePlayer;

public class MoneyTransactionCard extends Card {

    private final int money;
    private final boolean paying;
    public MoneyTransactionCard(String name, CardType cardType, int money, boolean paying) {
        super(name, cardType);
        this.money=money;
        this.paying = paying;
    }


    @Override
    void doThingUseCard(Game game, GamePlayer gamePlayer) {
        if (paying) {
            gamePlayer.payMoney(money);
        } else {
            gamePlayer.giveMoney(money);
        }
    }
}
