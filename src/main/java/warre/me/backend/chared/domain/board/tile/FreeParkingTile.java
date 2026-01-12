package warre.me.backend.chared.domain.board.tile;

public class FreeParkingTile implements Tile {
    @Override
    public TileType getTileType() {
        return TileType.FREE_PARKING;
    }

    @Override
    public boolean isCorner() {
        return true;
    }

    @Override
    public String toString() {
        return "FreeParkingTile";
    }
}
