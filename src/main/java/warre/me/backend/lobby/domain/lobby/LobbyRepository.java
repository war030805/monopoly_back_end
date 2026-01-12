package warre.me.backend.lobby.domain.lobby;

import java.util.Optional;

public interface LobbyRepository {
    void save(Lobby lobby);

    Optional<Lobby> findByGameId(LobbyId lobbyId);
}
