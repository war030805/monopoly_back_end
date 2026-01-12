package warre.me.backend.lobby.domain;

import lombok.Getter;
import lombok.Setter;
import warre.me.backend.game.domain.gamePlayer.PlayerId;

@Getter
@Setter
public class LobbyPlayer {
    private final PlayerId playerId;

    private int movePlace;
    private String color;

    public LobbyPlayer(PlayerId playerId, String color) {
        this.playerId = playerId;
        this.color=color;
    }
}
