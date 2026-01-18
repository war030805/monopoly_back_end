package warre.me.backend.game.api.dto;

import warre.me.backend.shared.domain.board.Board;
import warre.me.backend.shared.domain.board.property.Property;
import warre.me.backend.shared.domain.dice.Dices;
import warre.me.backend.game.domain.gamePlayer.GamePlayer;
import warre.me.backend.game.domain.property.OwnProperty;

public record OwnPropertyDto(
        Property property,
        int houses,
        int place,
        boolean needsToTwowDice,
        int price
) {

    public static OwnPropertyDto fromDomain(OwnProperty ownProperty, GamePlayer gamePlayer, Dices dices) {
        return new OwnPropertyDto(
                ownProperty.getProperty(),
                ownProperty.getHouses(),
                Board.getPlaceOfProperty(ownProperty.getProperty()),
                ownProperty.needsToTrowDice(),
                ownProperty.calcLandPrice(dices, gamePlayer.calcOwsOnOwnPropertyIfNeeded(ownProperty.getProperty()))
        );
    }
}
