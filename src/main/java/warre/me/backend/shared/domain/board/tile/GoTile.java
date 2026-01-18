package warre.me.backend.shared.domain.board.tile;

public class GoTile implements Tile {

    @Override
    public TileType getTileType() {
        return TileType.GO;
    }

    @Override
    public boolean isCorner() {
        return true;
    }

    @Override
    public String toString() {
        return "GoTile";
    }
}
