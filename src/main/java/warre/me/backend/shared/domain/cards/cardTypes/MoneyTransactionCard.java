package warre.me.backend.shared.domain.cards.cardTypes;

import warre.me.backend.shared.domain.cards.Card;
import warre.me.backend.shared.domain.cards.DeckType;
import warre.me.backend.game.domain.game.Game;
import warre.me.backend.game.domain.gamePlayer.GamePlayer;

public class MoneyTransactionCard extends Card {

    private final int money;
    private final boolean paying;
    public MoneyTransactionCard(String name, CardSpecificType cardSpecificType, int money, boolean paying, DeckType deckType) {
        super(name, cardSpecificType, deckType);
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

    @Override
    public CardType getCardType() {
        return CardType.TRANSACTION;
    }

    @Override
    public int getMoneyToPay(Game game, GamePlayer gamePlayer) {
        if (paying) {
            return money;
        }
        return -money;
    }
}
