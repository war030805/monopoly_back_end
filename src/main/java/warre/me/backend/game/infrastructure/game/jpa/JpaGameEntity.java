package warre.me.backend.game.infrastructure.game.jpa;

import jakarta.persistence.*;
import org.springframework.context.annotation.Profile;
import warre.me.backend.game.domain.game.Game;
import warre.me.backend.game.domain.game.GameId;
import warre.me.backend.game.domain.gamePlayer.GamePlayer;
import warre.me.backend.game.domain.gamePlayer.PlayerId;
import warre.me.backend.game.infrastructure.gamePlayer.jpa.JpaGamePlayerEntity;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "games")
public class JpaGameEntity {

    @Id
    private UUID id;

    @MapsId("gameId") // gebruikt gameId uit embedded id
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "game_id")
    private List<JpaGamePlayerEntity> players;

    @Column(nullable = false)
    private UUID currentPlayer;

    protected JpaGameEntity() {
    } // for JPA

    public JpaGameEntity(UUID id, List<JpaGamePlayerEntity> players, UUID currentPlayer) {
        this.id = id;
        this.players = players;
        this.currentPlayer = currentPlayer;
    }


    public static JpaGameEntity fromDomain(Game game) {
        return new JpaGameEntity(
                game.getGameId().id(),
                game.getPlayers().stream()
                        .map(gamePlayer -> JpaGamePlayerEntity.fromDomain(gamePlayer, game.getGameId()))
                        .toList(),
                game.getCurrentPlayer().id()
        );
    }


    public Game toDomain() {
        return new Game(
                new GameId(id),
                players.stream()
                        .map(JpaGamePlayerEntity::toDomain)
                        .collect(Collectors.toMap(GamePlayer::getPlayerId, Function.identity())),
                new PlayerId(currentPlayer));
    }
}
