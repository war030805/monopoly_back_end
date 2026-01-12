package warre.me.backend.game.infrastructure.game.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaGameRepository extends JpaRepository<JpaGameEntity, UUID> {
}
