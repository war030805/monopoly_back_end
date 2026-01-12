package warre.me.backend.chared.domain.board.property;

import java.awt.Color;

public enum StreetType {
    BROWN(new Color(0x6D4C41)),
    LIGHT_BLUE(new Color(0x29B6F6)),
    PINK(new Color(0xEC407A)),
    ORANGE(new Color(0xFF9800)),
    RED(new Color(0xF44336)),
    YELLOW(new Color(0xFFEB3B)),
    GREEN(new Color(0x4CAF50)),
    DARK_BLUE(new Color(0x1565C0)),
    STATION(new Color(0x9E9E9E)),
    UTILITY(new Color(0xBDBDBD));

    private final Color color;

    StreetType(Color color) {
        this.color = color;
    }

    /** "#RRGGBB" (zonder alpha) */
    public String hex() {
        return String.format("#%02X%02X%02X",
                color.getRed(),
                color.getGreen(),
                color.getBlue()
        );
    }
}