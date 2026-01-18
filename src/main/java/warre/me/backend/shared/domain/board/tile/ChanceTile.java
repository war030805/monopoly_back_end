package warre.me.backend.shared.domain.board.tile;

public class ChanceTile implements Tile {
    private static ChanceTile instance;

    private ChanceTile() {
    }

    public static ChanceTile getInstance() {
        if (instance==null) {
            instance= new ChanceTile();
        }
        return instance;
    }

    @Override
    public TileType getTileType() {
        return TileType.CHANCE;
    }

    @Override
    public String toString() {
        return "ChanceTile";
    }
}
