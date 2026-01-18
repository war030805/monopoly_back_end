package warre.me.backend.shared.domain.board.tile;

public class PrisonTile implements Tile {

    @Override
    public TileType getTileType() {
        return TileType.PRISON;
    }

    @Override
    public boolean isCorner() {
        return true;
    }

    @Override
    public String toString() {
        return "PrisonTile";
    }
}
