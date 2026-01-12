package warre.me.backend.board.api.dto;

import warre.me.backend.chared.domain.board.tile.Tile;

public record TaxInfoDto(
        int tax
) implements ExtraInfoDto {
    public static TaxInfoDto fromDomain(Tile tile) {
        return new TaxInfoDto(
                tile.getTax()
        );
    }
}
