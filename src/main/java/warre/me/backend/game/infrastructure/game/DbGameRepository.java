package warre.me.backend.game.infrastructure.game;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import warre.me.backend.game.domain.game.Game;
import warre.me.backend.game.domain.game.GameId;
import warre.me.backend.game.domain.game.GameRepository;
import warre.me.backend.game.infrastructure.game.jpa.JpaGameEntity;
import warre.me.backend.game.infrastructure.game.jpa.JpaGameRepository;

import java.util.Optional;

@Repository
@Profile("db")
public class DbGameRepository implements GameRepository {

    private final JpaGameRepository jpaGameRepository;

    public DbGameRepository(JpaGameRepository jpaGameRepository) {
        this.jpaGameRepository = jpaGameRepository;
    }


    @Override
    public Optional<Game> findByGameId(GameId gameId) {
        return jpaGameRepository.findById(gameId.id())
                .map(JpaGameEntity::toDomain);
    }

    @Override
    public void save(Game game) {
        jpaGameRepository.save(JpaGameEntity.fromDomain(game));
    }
}
