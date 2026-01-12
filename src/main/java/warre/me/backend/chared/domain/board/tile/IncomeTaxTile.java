package warre.me.backend.chared.domain.board.tile;

public record IncomeTaxTile(
        int tax
) implements Tile {

    @Override
    public TileType getTileType() {
        return TileType.TAX;
    }

    @Override
    public int getTax() {
        return tax;
    }

    @Override
    public String toString() {
        return "IncomeTaxTile";
    }
}
