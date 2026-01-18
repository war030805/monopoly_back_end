package warre.me.backend.shared.domain.board.tile;

import warre.me.backend.shared.domain.board.property.Property;
import warre.me.backend.game.domain.game.GameException;

public interface Tile {
    default Property getProperty() {
        throw new GameException("tile is not a property");
    }
    TileType getTileType();

    default boolean isCorner() {
        return false;
    }

    default boolean isProperty() {
        return getTileType().equals(TileType.PROPERTY);
    }

    default String getName() {
        return getTileType().name().toLowerCase().replace("_", " ");
    }

    default boolean isTax() {
        return getTileType().equals(TileType.TAX);
    }

    default int getTax() {
        throw new GameException("tile is not a tax tile");
    }


}
