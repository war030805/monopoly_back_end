package warre.me.backend.game.infrastructure.gamePlayer.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import warre.me.backend.game.domain.game.GameId;
import warre.me.backend.game.domain.gamePlayer.PlayerId;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class JpaGamePlayerId implements Serializable {

    @Getter
    @Column(name = "player_id", nullable = false)
    private UUID userId;

    @Column(name = "game_id", nullable = false)
    private UUID gameId;

    protected JpaGamePlayerId() {
    } // for JPA

    public JpaGamePlayerId(PlayerId userId, GameId gameId) {
        this.userId = userId.id();
        this.gameId = gameId.id();
    }
}
