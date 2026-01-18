package warre.me.backend.shared.domain.board.tile;

import warre.me.backend.shared.domain.board.property.Property;

public record PropertyTile(
       Property property
) implements Tile {
    @Override
    public Property getProperty() {
        return property;
    }

    @Override
    public TileType getTileType() {
        return TileType.PROPERTY;
    }

    @Override
    public String getName() {
        return property.name().toLowerCase().replace("_"," ");
    }

    @Override
    public String toString() {
        return property.name();
    }
}
