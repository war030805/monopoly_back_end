package warre.me.backend.chared.domain.cards.cardTypes;

import warre.me.backend.chared.domain.cards.Card;
import warre.me.backend.chared.domain.cards.DeckType;
import warre.me.backend.game.domain.game.Game;
import warre.me.backend.game.domain.gamePlayer.GamePlayer;

public class PayEachPlayerCard extends Card {
    private final int money;
    public PayEachPlayerCard(String name, CardSpecificType cardSpecificType, int money, DeckType deckType) {
        super(name, cardSpecificType, deckType);
        this.money = money;
    }

    @Override
    public void doThingUseCard(Game game, GamePlayer gamePlayer) {
        game.payEachPlayer(gamePlayer, money);
    }

    @Override
    public CardType getCardType() {
        return CardType.TRANSACTION;
    }

    @Override
    public int getMoneyToPay(Game game, GamePlayer gamePlayer) {
        return game.calcPayEachPlayer(money);
    }
}
