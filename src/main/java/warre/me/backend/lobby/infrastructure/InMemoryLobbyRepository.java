package warre.me.backend.lobby.infrastructure;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import warre.me.backend.game.domain.game.Game;
import warre.me.backend.game.domain.game.GameId;
import warre.me.backend.game.domain.game.GameRepository;
import warre.me.backend.lobby.domain.lobby.Lobby;
import warre.me.backend.lobby.domain.lobby.LobbyId;
import warre.me.backend.lobby.domain.lobby.LobbyRepository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


@Repository
@Profile("inmemory")
public class InMemoryLobbyRepository implements LobbyRepository {
    private final Map<LobbyId, Lobby> store = new ConcurrentHashMap<>();

    @Override
    public void save(Lobby lobby) {
        store.put(lobby.getLobbyId(), lobby);
    }

    @Override
    public Optional<Lobby> findByGameId(LobbyId lobbyId) {
        return Optional.ofNullable(store.get(lobbyId));
    }
}
