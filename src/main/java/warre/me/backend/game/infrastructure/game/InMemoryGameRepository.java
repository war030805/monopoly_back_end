package warre.me.backend.game.infrastructure.game;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import warre.me.backend.game.domain.game.Game;
import warre.me.backend.game.domain.game.GameId;
import warre.me.backend.game.domain.game.GameRepository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


@Repository
@Profile("inmemory")
public class InMemoryGameRepository implements GameRepository {
    private final Map<GameId, Game> store = new ConcurrentHashMap<>();

    @Override
    public void save(Game game) {
        store.put(game.getGameId(), game);
    }

    @Override
    public Optional<Game> findByGameId(GameId gameId) {
        return Optional.ofNullable(store.get(gameId));
    }
}
