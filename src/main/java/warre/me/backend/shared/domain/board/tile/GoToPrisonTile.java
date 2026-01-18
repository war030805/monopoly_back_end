package warre.me.backend.shared.domain.board.tile;

public class GoToPrisonTile implements Tile {

    @Override
    public TileType getTileType() {
        return TileType.GO_TO_PRISON;
    }

    @Override
    public boolean isCorner() {
        return true;
    }

    @Override
    public String toString() {
        return "GoToPrisonTile";
    }
}
