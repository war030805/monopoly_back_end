package warre.me.backend.lobby.domain;

import lombok.Getter;
import lombok.Setter;
import warre.me.backend.player.domain.PlayerId;

@Getter
@Setter
public class LobbyPlayer {
    private final PlayerId playerId;

    private int trowSum;
    private String color;
    private int movePlace;

    public LobbyPlayer(PlayerId playerId, String color) {
        this.playerId = playerId;
        this.color=color;
        trowSum=0;
        movePlace=-1;
    }
}
