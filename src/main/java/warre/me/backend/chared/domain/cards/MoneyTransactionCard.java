package warre.me.backend.chared.domain.cards;

import warre.me.backend.game.domain.gamePlayer.GamePlayer;

public class MoneyTransactionCard extends Card {

    private final int money;
    private MoneyTransactionCard(String name, CardType cardType, int money) {
        super(name, cardType);
        this.money=money;
    }

    protected MoneyTransactionCard makeYouPayingCard(String name, CardType cardType, int money) {
        return new MoneyTransactionCard(
                name,
                cardType,
                -money
        );
    }

    protected MoneyTransactionCard makeBankPayingCard(String name, CardType cardType, int money) {
        return new MoneyTransactionCard(
                name,
                cardType,
                money
        );
    }

    @Override
    void doThingLandOnCard(GamePlayer gamePlayer) {
        gamePlayer.payMoney(-money);
    }
}
