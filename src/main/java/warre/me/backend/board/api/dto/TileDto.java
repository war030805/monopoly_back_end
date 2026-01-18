package warre.me.backend.board.api.dto;

import warre.me.backend.shared.domain.board.tile.Tile;
import warre.me.backend.shared.domain.board.tile.TileType;

public record TileDto(
        ExtraInfoDto extraInfo,
        TileType tileType,
        String name,
        boolean isCorner
) {
    public static TileDto fromDomain(Tile tile) {
        ExtraInfoDto extraInfoDto=null;

        if (tile.isProperty()) {
            extraInfoDto= PropertyInfoDto.fromDomain(tile);
        }

        if (tile.isTax()) {
            extraInfoDto= TaxInfoDto.fromDomain(tile);
        }
        return new TileDto(
                extraInfoDto,
                tile.getTileType(),
                tile.getName(),
                tile.isCorner()
        );
    }
}
