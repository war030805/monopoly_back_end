package warre.me.backend.chared.domain.cards;

import warre.me.backend.game.domain.game.Game;
import warre.me.backend.game.domain.gamePlayer.GamePlayer;

public class GeneralRepairs extends Card {
    protected GeneralRepairs(String name, CardType cardType) {
        super(name, cardType);
    }

    @Override
    void doThingUseCard(Game game, GamePlayer gamePlayer) {
        var housesToPay=gamePlayer.getCountOfHouses() *25;
        int hotelsToPay= gamePlayer.getCountOfHotels() * 100;

        gamePlayer.payMoney(hotelsToPay + housesToPay);
    }
}
