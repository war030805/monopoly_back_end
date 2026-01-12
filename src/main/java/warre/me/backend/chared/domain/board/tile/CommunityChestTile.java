package warre.me.backend.chared.domain.board.tile;

public class CommunityChestTile implements Tile {
    private static CommunityChestTile instance;

    private CommunityChestTile() {
    }

    public static CommunityChestTile getInstance() {
        if (instance==null) {
            instance= new CommunityChestTile();
        }
        return instance;
    }

    @Override
    public TileType getTileType() {
        return TileType.COMMUNITY_CHEST;
    }

    @Override
    public String toString() {
        return "CommunityChestTile";
    }


}
