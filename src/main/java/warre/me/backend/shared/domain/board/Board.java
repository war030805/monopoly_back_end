package warre.me.backend.shared.domain.board;

import org.springframework.data.util.Pair;
import warre.me.backend.shared.domain.NotFoundException;
import warre.me.backend.shared.domain.board.property.Property;
import warre.me.backend.shared.domain.board.property.StreetType;
import warre.me.backend.shared.domain.board.tile.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static warre.me.backend.shared.domain.board.property.Property.*;

public abstract class Board {
    private final static Tile[] BOARD_TILES = makeBoardTiles();

    public final static int TILES_SIZE = BOARD_TILES.length;

    private final static Map<Property, Integer> propertyPlaceMap = new ConcurrentHashMap<>();

    private static Tile[] makeBoardTiles() {
        return new Tile[]{
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
            var tile = BOARD_TILES[i];
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

        var placeFromEnd = TILES_SIZE - place;
        var start = new ArrayList<>(getFullBoard().subList(placeFromEnd, TILES_SIZE));

        var end = getFullBoard().subList(0, placeFromEnd);

        start.addAll(end);
        return start.stream().toList();
    }

    public static int getTaxFromPlace(int place) {
        return BOARD_TILES[place].getTax();
    }

    private static List<Tile> getAllPropertyTiles() {
        return getFullBoard().stream()
                .filter(Tile::isProperty)
                .toList();
    }

    public static int getNearestStreetTypePlace(int place, StreetType streetType) {
        var tileTypeOfCurrentPlace = getTileTypeFromPlace(place);

        if (tileTypeOfCurrentPlace.equals(TileType.PROPERTY)) {
            var property = getPropertyFromPlace(place);
            if (property.getStreetType().equals(streetType)) {
                return place;
            }
        }


        var beforeTiles = new ArrayList<>(getFullBoard().subList(0, place));
        Collections.reverse(beforeTiles);

        var afterTiles = getFullBoard().subList(place + 1, TILES_SIZE);

        var distanceAndPlacePairBefore=getDistanceToTileAndPlace(beforeTiles, streetType);
        var distanceAndPlacePairAfter=getDistanceToTileAndPlace(afterTiles, streetType);

        var distanceToBeforeTile = distanceAndPlacePairBefore.getFirst();

        var distanceToAfterTile = distanceAndPlacePairAfter.getFirst();

        var placeTileOfBefore = distanceAndPlacePairBefore.getSecond();

        var placeTileOfAfter = distanceAndPlacePairAfter.getSecond();


        if (distanceToBeforeTile < distanceToAfterTile) {
            return placeTileOfBefore;
        }

        return placeTileOfAfter;
    }

    private static Optional<Tile> getFirstOfPropertyOfList(List<Tile> tiles, StreetType streetType) {
        return tiles.stream()
                .filter(Tile::isProperty)
                .filter(tile -> tile.getProperty().getStreetType().equals(streetType))
                .findFirst();
    }

    /**
     *
     * @param tiles de tiles list
     * @return als eerste is het de distance en dan is de place
     */
    private static Pair<Integer, Integer> getDistanceToTileAndPlace(List<Tile> tiles, StreetType streetType) {
        var optionalTile=getFirstOfPropertyOfList(tiles, streetType);

        var distanceToTile = optionalTile
                .map(tiles::indexOf)
                .orElse(Integer.MAX_VALUE);

        var placeOfTile= optionalTile
                .map(tile -> getPlaceOfProperty(tile.getProperty()))
                .orElse(Integer.MAX_VALUE);

        return Pair.of(distanceToTile, placeOfTile);
    }

    public static int getPlaceOfPrison() {
        return 10;
    }
}
