package warre.me.backend.game.domain.gamePlayer;

import lombok.Getter;
import warre.me.backend.chared.domain.board.Board;
import warre.me.backend.chared.domain.board.property.Property;
import warre.me.backend.chared.domain.board.property.StreetType;
import warre.me.backend.chared.domain.dice.Dices;
import warre.me.backend.game.domain.game.BuyException;
import warre.me.backend.game.domain.game.GameException;
import warre.me.backend.game.domain.game.MoneyException;
import warre.me.backend.game.domain.property.OwnProperty;
import warre.me.backend.player.domain.PlayerId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static warre.me.backend.game.domain.gamePlayer.Action.WAITING;


public class GamePlayer implements Comparable<GamePlayer> {
    @Getter
    private final PlayerId playerId;

    @Getter
    private int money= 1500;

    @Getter
    private int place;
    private final Map<Property, OwnProperty> ownsProperties;

    @Getter
    private final int movePlace;

    @Getter
    private boolean isBankrupt;

    @Getter
    private Action action;

    @Getter
    private final String color;

    public GamePlayer(PlayerId playerId, int movePlace, String color) {
        this.playerId = playerId;
        this.movePlace = movePlace;
        ownsProperties =new HashMap<>();
        isBankrupt=false;
        action=WAITING;
        this.color=color;
    }

    public GamePlayer(PlayerId playerId, Map<Property, OwnProperty> ownsProperties, int movePlace, int money, int place, boolean isBankrupt, Action action, String color) {
        this.playerId = playerId;
        this.ownsProperties = ownsProperties;
        this.movePlace = movePlace;
        this.money = money;
        this.place = place;
        this.isBankrupt = isBankrupt;
        this.action = action;
        this.color=color;
    }

    public void addToPlace(int places) {
        var newPlace= place + places;

        if (newPlace >= Board.TILES_SIZE) {
            newPlace= newPlace - Board.TILES_SIZE;
        }
        place= newPlace;
    }

    public void buyProperty() {
        bankruptCheck();
        var property=Board.buyProperty(place);

        if (property.getPropertyBuyCost() > money) {
            throw new BuyException("does not have enough money to buy property");
        }

        money-=property.getPropertyBuyCost();

        ownsProperties.put(property, new OwnProperty(
                property
        ));
    }

    public boolean ownsProperty(Property property) {
        return ownsProperties.containsKey(property);
    }

    public void payLandOnOtherProperty(GamePlayer owner, Dices dices) {
        bankruptCheck();
        var property=Board.buyProperty(place);

        var moneyToPay= owner.getMoneyFromPlayerForProperty(property, dices);

        if (moneyToPay>=money) {
            throw new MoneyException("cannot buy dos not have enough money");
        }

        money-=moneyToPay;
    }

    private int getMoneyFromPlayerForProperty(Property property, Dices dices) {
        bankruptCheck();

        if (!ownsProperty(property)) {
            throw new GameException("cannot get money of property when he does have it");
        }

        var ownProperty= getOwnPropertyByProperty(property);


        var owsOfCards= calcOwsOnOwnPropertyIfNeeded(property);

        int price=ownProperty.calcLandPrice(dices, owsOfCards);

        money+=price;

        return price;
    }

    public int calcOwnsCountOfStreetType(StreetType streetType) {
        return ownsProperties.values().stream()
                .filter(ownProperty -> ownProperty.getProperty().getStreetType().equals(streetType))
                .toList()
                .size();
    }

    public int calcOwsOnOwnPropertyIfNeeded(Property property) {
        return getOwnPropertyByProperty(property).getCountOwnsIfNeeded()
                .map(this::calcOwnsCountOfStreetType)
                .orElse(0);
    }

    public List<OwnProperty> getListOfOwnProperty() {
        return ownsProperties.values().stream()
                .toList();
    }

    private void bankruptCheck() {
        if (isBankrupt) {
            throw new GameException("cannot do actions when bankrupt");
        }
    }

    public void move(Dices dices) {
        bankruptCheck();
        addToPlace(dices.sum());
    }

    public OwnProperty getOwnPropertyByProperty(Property property) {
        return Optional.ofNullable(ownsProperties.get(property))
                .orElseThrow(property::notFound);
    }

    @Override
    public int compareTo(GamePlayer o) {
        return Integer.compare(movePlace, o.movePlace);
    }

    public void throwDices() {
        action= Action.TROWING_DICES;
    }
}
