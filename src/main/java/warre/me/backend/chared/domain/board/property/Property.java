package warre.me.backend.chared.domain.board.property;

import lombok.Getter;
import warre.me.backend.chared.domain.NotFoundThrower;

import java.util.Arrays;
import java.util.List;


public enum Property implements NotFoundThrower {
    OLD_KENT_ROAD(StreetType.BROWN, 60, List.of(2, 10, 30, 90, 160, 250)),
    WHITECHAPEL_ROAD(StreetType.BROWN, 60, List.of(4, 20, 60, 180, 320, 450)),

    KINGS_CROSS_STATION(StreetType.STATION, 200, List.of(0, 25, 50, 100, 200)),

    // LIGHT_BLUE
    THE_ANGEL_ISLINGTON(StreetType.LIGHT_BLUE, 100, List.of(6, 30, 90, 270, 400, 550)),
    EUSTON_ROAD(StreetType.LIGHT_BLUE, 100, List.of(6, 30, 90, 270, 400, 550)),
    PENTONVILLE_ROAD(StreetType.LIGHT_BLUE, 120, List.of(8, 40, 100, 300, 450, 600)),

    // PINK
    PALL_MALL(StreetType.PINK, 140, List.of(10, 50, 150, 450, 625, 750)),

    ELECTRIC_COMPANY(StreetType.UTILITY, 150, List.of(0, 4, 10)),

    WHITEHALL(StreetType.PINK, 140, List.of(10, 50, 150, 450, 625, 750)),
    NORTHUMBERLAND_AVENUE(StreetType.PINK, 160, List.of(12, 60, 180, 500, 700, 900)),

    MARYLEBONE_STATION(StreetType.STATION, 200, List.of(0, 25, 50, 100, 200)),

    // ORANGE
    BOW_STREET(StreetType.ORANGE, 180, List.of(14, 70, 200, 550, 750, 950)),
    MARLBOROUGH_STREET(StreetType.ORANGE, 180, List.of(14, 70, 200, 550, 750, 950)),
    VINE_STREET(StreetType.ORANGE, 200, List.of(16, 80, 220, 600, 800, 1000)),

    // RED
    THE_STRAND(StreetType.RED, 220, List.of(18, 90, 250, 700, 875, 1050)),
    FLEET_STREET(StreetType.RED, 220, List.of(18, 90, 250, 700, 875, 1050)),
    TRAFALGAR_SQUARE(StreetType.RED, 240, List.of(20, 100, 300, 750, 925, 1100)),

    FENCHURCH_ST_STATION(StreetType.STATION, 200, List.of(0, 25, 50, 100, 200)),

    // YELLOW
    LEICESTER_SQUARE(StreetType.YELLOW, 260, List.of(22, 110, 330, 800, 975, 1150)),
    COVENTRY_STREET(StreetType.YELLOW, 260, List.of(22, 110, 330, 800, 975, 1150)),

    WATER_WORKS(StreetType.UTILITY, 150, List.of(0, 4, 10)),

    PICCADILLY(StreetType.YELLOW, 280, List.of(22, 120, 360, 850, 1025, 1200)),

    // GREEN
    REGENT_STREET(StreetType.GREEN, 300, List.of(26, 130, 390, 900, 1100, 1275)),
    OXFORD_STREET(StreetType.GREEN, 300, List.of(26, 130, 390, 900, 1100, 1275)),
    BOND_STREET(StreetType.GREEN, 320, List.of(28, 150, 450, 1000, 1200, 1400)),

    LIVERPOOL_STREET_STATION(StreetType.STATION, 200, List.of(0, 25, 50, 100, 200)),

    // DARK_BLUE
    PARK_LANE(StreetType.DARK_BLUE, 350, List.of(35, 175, 500, 1100, 1300, 1500)),
    MAYFAIR(StreetType.DARK_BLUE, 400, List.of(50, 200, 600, 1400, 1700, 2000));

    @Getter
    private final StreetType streetType;

    @Getter
    private final int propertyBuyCost;
    private final int[] rents; // straten: index = #huizen (0..5), stations/utilities: index = #owned

    Property(StreetType streetType, int propertyBuyCost, List<Integer> rents) {
        this.streetType = streetType;
        this.propertyBuyCost = propertyBuyCost;
        this.rents = rents.stream().mapToInt(i -> i).toArray();
    }

    public int getRent(int index) {
        return rents[index];
    }

    @Override
    public String notFoundMessage() {
        return "could not find property of name" + this.name();
    }

    public List<Integer> getAllRentsRight() {
        return switch (this.streetType) {
            case UTILITY, STATION -> Arrays.stream(Arrays.copyOfRange(rents, 1, rents.length)).boxed().toList();
            default -> Arrays.stream(rents).boxed().toList();
        };
    }
}