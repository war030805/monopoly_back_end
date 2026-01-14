package warre.me.backend.chared.domain.cards;

import warre.me.backend.game.domain.game.Game;
import warre.me.backend.game.domain.gamePlayer.GamePlayer;

public class PaysPerHouseAndHotelCard extends Card {
    private final int paysPerHouse;
    private final int paysPerHotel;
    public PaysPerHouseAndHotelCard(String name, CardType cardType, int paysPerHouse, int paysPerHotel) {
        super(name, cardType);
        this.paysPerHouse = paysPerHouse;
        this.paysPerHotel = paysPerHotel;
    }

    @Override
    void doThingUseCard(Game game, GamePlayer gamePlayer) {
        var housesToPay=gamePlayer.getCountOfHouses() * paysPerHouse;
        int hotelsToPay= gamePlayer.getCountOfHotels() * paysPerHotel;

        gamePlayer.payMoney(hotelsToPay + housesToPay);
    }
}
