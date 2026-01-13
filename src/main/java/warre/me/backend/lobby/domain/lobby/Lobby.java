package warre.me.backend.lobby.domain.lobby;

import warre.me.backend.chared.domain.dice.Dices;
import warre.me.backend.player.domain.PlayerId;
import warre.me.backend.lobby.domain.LobbyPlayer;

import java.util.List;

public class Lobby {
    private final LobbyId lobbyId;

    private final PlayerId host;

    private final List<LobbyPlayer> lobbyPlayers;

    private final Dices dices;

    public Lobby(LobbyId lobbyId, PlayerId host, List<LobbyPlayer> lobbyPlayers) {
        this.lobbyId = lobbyId;
        this.host = host;
        this.lobbyPlayers = lobbyPlayers;
        this.dices = Dices.trowDices();
    }
}
