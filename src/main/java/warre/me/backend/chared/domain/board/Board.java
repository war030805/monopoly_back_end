package warre.me.backend.chared.domain.board;

import warre.me.backend.chared.domain.NotFoundException;
import warre.me.backend.chared.domain.board.property.Property;
import warre.me.backend.chared.domain.board.tile.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static warre.me.backend.chared.domain.board.property.Property.*;

public abstract class Board {
    private final static Tile[] BOARD_TILES=makeBoardTiles();

    public final static int TILES_SIZE= BOARD_TILES.length;

    private final static HashMap<Property, Integer> propertyPlaceMap= new HashMap<>();

    private static Tile[] makeBoardTiles() {
        return new Tile[] {
                new GoTile(),
                new PropertyTile(OLD_KENT_ROAD),
                CommunityChestTile.getInstance(),
                new PropertyTile(WHITECHAPEL_ROAD),
                new IncomeTaxTile(200),
                new PropertyTile(KINGS_CROSS_STATION),
                new PropertyTile(THE_ANGEL_ISLINGTON),
                ChanceTile.getInstance(),
                new PropertyTile(EUSTON_ROAD),
                new PropertyTile(PENTONVILLE_ROAD),

                new PrisonTile(),
                new PropertyTile(PALL_MALL),
                new PropertyTile(ELECTRIC_COMPANY),
                new PropertyTile(WHITEHALL),
                new PropertyTile(NORTHUMBERLAND_AVENUE),
                new PropertyTile(MARYLEBONE_STATION),
                new PropertyTile(BOW_STREET),
                CommunityChestTile.getInstance(),
                new PropertyTile(MARLBOROUGH_STREET),
                new PropertyTile(VINE_STREET),
                new FreeParkingTile(),

                new PropertyTile(THE_STRAND),
                ChanceTile.getInstance(),
                new PropertyTile(FLEET_STREET),
                new PropertyTile(TRAFALGAR_SQUARE),
                new PropertyTile(FENCHURCH_ST_STATION),
                new PropertyTile(LEICESTER_SQUARE),
                new PropertyTile(COVENTRY_STREET),
                new PropertyTile(WATER_WORKS),
                new PropertyTile(PICCADILLY),
                new GoToPrisonTile(),

                new PropertyTile(REGENT_STREET),
                new PropertyTile(OXFORD_STREET),
                CommunityChestTile.getInstance(),
                new PropertyTile(BOND_STREET),
                new PropertyTile(LIVERPOOL_STREET_STATION),
                ChanceTile.getInstance(),
                new PropertyTile(PARK_LANE),
                new IncomeTaxTile(100),
                new PropertyTile(MAYFAIR)
        };
    }

    public static Property buyProperty(int place) {
        return getPropertyFromPlace(place);
    }

    public static TileType getTileTypeFromPlace(int place) {
        return BOARD_TILES[place].getTileType();
    }

    public static int getPlaceOfProperty(Property property) {
        return propertyPlaceMap.computeIfAbsent(property, Board::computeIfAbsent);
    }

    private static int computeIfAbsent(Property property) {
        for (int i = 0; i < BOARD_TILES.length; i++) {
            var tile=BOARD_TILES[i];
            if (tile.getTileType().equals(TileType.PROPERTY) && tile.getProperty().equals(property)) {
                return i;
            }
        }
        throw new NotFoundException("No property found");
    }

    public static Property getPropertyFromPlace(int place) {
        return BOARD_TILES[place].getProperty();
    }

    public static List<Tile> getFullBoard() {
        return Arrays.stream(BOARD_TILES).toList();
    }

    public static List<Tile> getFullBoardStartingAtPlace(int place) {

        var placeFromEnd= TILES_SIZE - place;
        var start= new ArrayList<>(getFullBoard().subList(placeFromEnd,TILES_SIZE));

        var end = getFullBoard().subList(0, placeFromEnd);

        start.addAll(end);
        return start.stream().toList();
    }
}
