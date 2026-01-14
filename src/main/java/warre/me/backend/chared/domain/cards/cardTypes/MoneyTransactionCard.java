package warre.me.backend.chared.domain.cards.cardTypes;

import warre.me.backend.chared.domain.cards.Card;
import warre.me.backend.chared.domain.cards.DeckType;
import warre.me.backend.game.domain.game.Game;
import warre.me.backend.game.domain.gamePlayer.GamePlayer;

public class MoneyTransactionCard extends Card {

    private final int money;
    private final boolean paying;
    public MoneyTransactionCard(String name, CardType cardType, int money, boolean paying, DeckType deckType) {
        super(name, cardType, deckType);
        this.money=money;
        this.paying = paying;
    }


    @Override
    public void doThingUseCard(Game game, GamePlayer gamePlayer) {
        if (paying) {
            gamePlayer.payMoney(money);
        } else {
            gamePlayer.giveMoney(money);
        }
    }
}
