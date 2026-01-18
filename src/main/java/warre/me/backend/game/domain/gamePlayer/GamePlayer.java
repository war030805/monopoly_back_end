package warre.me.backend.game.domain.gamePlayer;

import lombok.Getter;
import warre.me.backend.shared.domain.board.Board;
import warre.me.backend.shared.domain.board.property.Property;
import warre.me.backend.shared.domain.board.property.StreetType;
import warre.me.backend.shared.domain.board.tile.TileType;
import warre.me.backend.shared.domain.cards.Card;
import warre.me.backend.shared.domain.cards.cardTypes.CardSpecificType;
import warre.me.backend.shared.domain.dice.Dices;
import warre.me.backend.game.domain.game.BuyException;
import warre.me.backend.game.domain.game.Game;
import warre.me.backend.game.domain.game.GameException;
import warre.me.backend.game.domain.game.MoneyException;
import warre.me.backend.game.domain.property.OwnProperty;
import warre.me.backend.player.domain.PlayerId;

import java.util.*;

import static warre.me.backend.game.domain.gamePlayer.Action.*;


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

    @Getter
    private final List<Card> ownedCards;

    @Getter
    private Card cardGot;

    private final List<Action> actionsDone;

    public GamePlayer(PlayerId playerId, int movePlace, String color) {
        this.playerId = playerId;
        this.movePlace = movePlace;
        ownsProperties =new HashMap<>();
        isBankrupt=false;
        action=WAITING;
        this.color=color;
        ownedCards=new ArrayList<>();
        actionsDone=new LinkedList<>();
    }

    public GamePlayer(PlayerId playerId, Map<Property, OwnProperty> ownsProperties, int movePlace, int money, int place,
                      boolean isBankrupt, Action action, String color) {
        this.playerId = playerId;
        this.ownsProperties = ownsProperties;
        this.movePlace = movePlace;
        this.money = money;
        this.place = place;
        this.isBankrupt = isBankrupt;
        this.action = action;
        this.color=color;
        ownedCards=new ArrayList<>();
        actionsDone=new LinkedList<>();
    }

    public void addToPlace(int places) {
        var newPlace= place + places;

        if (newPlace >= Board.TILES_SIZE) {
            newPlace= newPlace - Board.TILES_SIZE;
            giveMoney(200);
        }

        if (newPlace < 0) {
            newPlace= Math.abs(newPlace);
            newPlace= Board.TILES_SIZE - newPlace;
        }
        place= newPlace;
    }

    public void buyProperty() {
        bankruptCheck();
        var property=Board.buyProperty(place);

        if (property.getPropertyBuyCost() > money) {
            throw new BuyException("does not have enough money to buy property");
        }

        payMoney(property.getPropertyBuyCost());

        setCurrentAction(BUY_PROPERTY);

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

        payMoney(moneyToPay);

        setCurrentAction(PAY_RENT);
    }

    private int getMoneyFromPlayerForProperty(Property property, Dices dices) {
        bankruptCheck();

        if (!ownsProperty(property)) {
            throw new GameException("cannot get money of property when he does have it");
        }

        var ownProperty= getOwnPropertyByProperty(property);


        var owsOfCards= calcOwsOnOwnPropertyIfNeeded(property);

        int price=ownProperty.calcLandPrice(dices, owsOfCards);

        giveMoney(price);

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
        setCurrentAction(MOVED);
    }

    public OwnProperty getOwnPropertyByProperty(Property property) {
        return Optional.ofNullable(ownsProperties.get(property))
                .orElseThrow(property::notFound);
    }

    @Override
    public int compareTo(GamePlayer o) {
        return Integer.compare(movePlace, o.movePlace);
    }

    public void throwDices(DoAction doAction) {
        bankruptCheck();

        switch (doAction) {
            case MOVING -> setCurrentAction(TROWING_DICES);
            case PROPERTY -> setCurrentAction(TROWING_DICES_FOR_PROPERTY);
        }

    }

    public void endMove() {
        bankruptCheck();
        setCurrentAction(WAITING);
        actionsDone.clear();
    }

    public void payTax() {
        bankruptCheck();
        var tax=Board.getTaxFromPlace(place);
        if (tax> money) {
            throw new MoneyException("you do not have enough money to pay taxes");
        }

        setCurrentAction(PAYING_TAXES);
        payMoney(tax);
    }

    public void goToPlaceCard(int placeToMove) {
        bankruptCheck();
        var placesToMove= calcDistanceToPlace(placeToMove);
        addToPlace(placesToMove);
        setCurrentAction(MOVED_AFTER_CARD);
    }

    private int calcDistanceToPlace(int place) {
        var places= place- this.place;

        if (places<0) {
            //positief maken
            places= Math.abs(places);
            places= Board.TILES_SIZE - places;
        }

        return places;
    }

    public void payMoney(int money) {
        bankruptCheck();
        if (money> this.money) {
            throw new MoneyException();
        }
        this.money-=money;
    }

    public void giveMoney(int money) {
        this.money+=money;
    }

    public void goOutOfJail() {

    }

    public void goToPrison() {
    }

    public int getCountOfHouses() {
        return getListOfOwnProperty().stream()
                .filter(ownProperty -> ownProperty.getProperty().isNormalProperty())
                .filter(ownProperty -> ownProperty.getHouses()<5)
                .mapToInt(OwnProperty::getHouses)
                .sum();
    }

    public int getCountOfHotels() {
        return getListOfOwnProperty().stream()
                .filter(ownProperty -> ownProperty.getProperty().isNormalProperty())
                .filter(ownProperty -> ownProperty.getHouses()==5)
                .toList()
                .size();
    }

    public void pullCard(Card card) {
        bankruptCheck();
        if (this.cardGot!=null) {
            throw new GameException("you cannot add a cord if you got already one card");
        }

        this.cardGot=card;

        setCurrentAction(PULLED_CARD);
    }

    public TileType getTileTypeStandingOn() {
        return Board.getTileTypeFromPlace(place);
    }

    public Optional<Card> getCardGotOptional() {
        return Optional.ofNullable(cardGot);
    }

    public void saveCard() {
        bankruptCheck();
        if (cardGot==null) {
            throw new GameException("cannot save card if you got none");
        }

        if (!cardGot.canSave()) {
            throw new GameException("cannot save card");
        }

        ownedCards.add(cardGot);

        setCurrentAction(SAVED_CARD);

        cardGot=null;
    }

    public Card useCard() {
        bankruptCheck();
        if (cardGot==null) {
            throw new GameException("cannot use card if you got none");
        }
        var cardTed= cardGot;
        cardGot=null;

        setCurrentAction(USED_CARD);
        return cardTed;
    }

    public Card useSavedCard(CardSpecificType cardSpecificType) {
        bankruptCheck();

        var cardToUse=ownedCards.stream()
                .filter(card -> card.getCardSpecificType().equals(cardSpecificType))
                .findAny()
                .orElseThrow(cardSpecificType::notFound);

        ownedCards.remove(cardToUse);

        setCurrentAction(USED_SAVED_CARD);
        return cardToUse;
    }

    public boolean canPlayCurrentAction(Game game) {
        return switch (action) {
            case PAY_RENT, WAITING, TROWING_DICES, MOVED, BUY_PROPERTY, PAYING_TAXES, USED_CARD, SAVED_CARD,
                 USED_SAVED_CARD, MOVED_AFTER_CARD, TROWING_DICES_FOR_PROPERTY, AUCTION -> true;
            case PULLED_CARD -> canPay(cardGot.getMoneyToPay(game, this));
        };
    }

    private boolean canPay(int moneyToPay) {
        return money > moneyToPay;
    }


    private void setCurrentAction(Action newAction) {
        bankruptCheck();
        actionsDone.add(action);

        action=newAction;
    }

    public List<Action> getActionsDone() {
        return actionsDone.stream().toList();
    }

    public void startAuction() {
        bankruptCheck();
        setCurrentAction(Action.AUCTION);

        Board.getPropertyFromPlace(place);
    }
}
