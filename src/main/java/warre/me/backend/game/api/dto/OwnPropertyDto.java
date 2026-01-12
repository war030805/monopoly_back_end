package warre.me.backend.game.api.dto;

import warre.me.backend.chared.domain.board.Board;
import warre.me.backend.chared.domain.board.property.Property;
import warre.me.backend.game.domain.gamePlayer.GamePlayer;
import warre.me.backend.game.domain.property.OwnProperty;

public record OwnPropertyDto(
        Property property,
        int houses,
        int place,
        boolean needsToTwowDice,
        int price
) {

    public static OwnPropertyDto fromDomain(OwnProperty ownProperty, GamePlayer gamePlayer) {
        return new OwnPropertyDto(
                ownProperty.getProperty(),
                ownProperty.getHouses(),
                Board.getPlaceOfProperty(ownProperty.getProperty()),
                ownProperty.needsToTrowDice(),
                ownProperty.calcLandPrice(gamePlayer.getDices(), gamePlayer.calcOwsOnOwnPropertyIfNeeded(ownProperty.getProperty()))
        );
    }
}
