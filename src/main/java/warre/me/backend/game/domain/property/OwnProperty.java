package warre.me.backend.game.domain.property;

import lombok.Getter;
import warre.me.backend.chared.domain.board.property.Property;
import warre.me.backend.chared.domain.board.property.StreetType;
import warre.me.backend.chared.domain.dice.Dices;

import java.util.Optional;

import static warre.me.backend.chared.domain.board.property.StreetType.STATION;
import static warre.me.backend.chared.domain.board.property.StreetType.UTILITY;

@Getter
public class OwnProperty {
    private final OwnPropertyId ownPropertyId;
    private final Property property;
    private int houses; //als het de station is of de utility dan is dit altijd 0

    public OwnProperty(Property property) {
        this.ownPropertyId = OwnPropertyId.random();
        this.property = property;
        houses=0;
    }

    public OwnProperty(OwnPropertyId ownPropertyId, Property property, int houses) {
        this.ownPropertyId = ownPropertyId;
        this.property = property;
        this.houses = houses;
    }

    public int calcLandPrice(Dices dices, int owns) {
        return switch (property.getStreetType()) {
            case UTILITY -> calcLandPriceWhenUtility(dices, owns);
            case STATION -> calcLandPriceWhenStation(owns);
            default -> calcLandPriceWhenNormal();
        };
    }

    public boolean needsToTrowDice() {
        return property.getStreetType().equals(UTILITY);
    }
    public Optional<StreetType> getCountOwnsIfNeeded() {
        return switch (property.getStreetType()) {
            case UTILITY -> Optional.of(UTILITY);
            case STATION -> Optional.of(STATION);
            default -> Optional.empty();
        };
    }
    private int calcLandPriceWhenUtility(Dices dices, int owns) {
        return property.getRent(owns) * dices.sum();
    }

    private int calcLandPriceWhenStation(int owns) {
        return property.getRent(owns);
    }

    private int calcLandPriceWhenNormal() {
        return property.getRent(houses);
    }
}
