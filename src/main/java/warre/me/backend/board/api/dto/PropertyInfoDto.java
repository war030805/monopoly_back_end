package warre.me.backend.board.api.dto;

import warre.me.backend.shared.domain.board.property.Property;
import warre.me.backend.shared.domain.board.property.StreetType;
import warre.me.backend.shared.domain.board.tile.Tile;

import java.util.List;

public record PropertyInfoDto(
        Property property,
        StreetType streetType,
        String color,
        int propertyBuyCost,
        List<Integer> rents
) implements ExtraInfoDto {
    public static PropertyInfoDto fromDomain(Tile tile) {
        var property = tile.getProperty();
        return new PropertyInfoDto(
                property,
                property.getStreetType(),
                property.getStreetType().hex(),
                property.getPropertyBuyCost(),
                property.getAllRentsRight()
        );
    }
}
