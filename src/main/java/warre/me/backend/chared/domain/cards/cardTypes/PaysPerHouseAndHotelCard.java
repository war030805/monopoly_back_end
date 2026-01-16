package warre.me.backend.chared.domain.cards.cardTypes;

import warre.me.backend.chared.domain.cards.Card;
import warre.me.backend.chared.domain.cards.DeckType;
import warre.me.backend.game.domain.game.Game;
import warre.me.backend.game.domain.gamePlayer.GamePlayer;

public class PaysPerHouseAndHotelCard extends Card {
    private final int paysPerHouse;
    private final int paysPerHotel;
    public PaysPerHouseAndHotelCard(String name, CardSpecificType cardSpecificType, int paysPerHouse, int paysPerHotel, DeckType deckType) {
        super(name, cardSpecificType, deckType);
        this.paysPerHouse = paysPerHouse;
        this.paysPerHotel = paysPerHotel;
    }

    @Override
    public void doThingUseCard(Game game, GamePlayer gamePlayer) {
        gamePlayer.payMoney(getMoneyToPay(game, gamePlayer));
    }

    @Override
    public CardType getCardType() {
        return CardType.TRANSACTION;
    }

    @Override
    public int getMoneyToPay(Game game, GamePlayer gamePlayer) {
        var housesToPay=gamePlayer.getCountOfHouses() * paysPerHouse;
        int hotelsToPay= gamePlayer.getCountOfHotels() * paysPerHotel;

        return hotelsToPay+ housesToPay;
    }
}
