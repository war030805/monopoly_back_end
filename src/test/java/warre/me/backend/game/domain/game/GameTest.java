package warre.me.backend.game.domain.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import warre.me.backend.game.domain.gamePlayer.Action;
import warre.me.backend.game.domain.gamePlayer.GamePlayer;
import warre.me.backend.lobby.domain.lobby.LobbyId;
import warre.me.backend.player.domain.PlayerId;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameTest {
    @Nested
    class EndMove {

        private GamePlayer player1;
        private GamePlayer player2;
        private GamePlayer player3;
        private GamePlayer player4;

        private Map<PlayerId, GamePlayer> map;
        @BeforeEach
        public void setup() {
            player1=createGamePlayer(0);
            player2=createGamePlayer(1);
            player3=createGamePlayer(2);
            player4=createGamePlayer(3);

            map= Stream.of(player1, player2, player3, player4)
                    .collect(Collectors.toMap(GamePlayer::getPlayerId, Function.identity()));
        }

        private GamePlayer createGamePlayer(int movePlace) {
            return new GamePlayer(PlayerId.random(), movePlace, "");
        }


        @Test
        public void shouldSetCurrentPlayerToSecondPlayer() {
            // Arrange
            var game= new Game(
                    GameId.random(),
                    LobbyId.random(),
                    map,
                    player1.getPlayerId()
            );

            // Act
            game.endMove(player1.getPlayerId());

            // Assert
            assertEquals(player2.getPlayerId(), game.getCurrentPlayerId());
            assertEquals(Action.WAITING, player1.getAction());
        }


        @Test
        public void shouldSetCurrentPlayerToFirstPlayer() {
            // Arrange
            var game= new Game(
                    GameId.random(),
                    LobbyId.random(),
                    map,
                    player4.getPlayerId()
            );

            // Act
            game.endMove(player4.getPlayerId());

            // Assert
            assertEquals(player1.getPlayerId(), game.getCurrentPlayerId());
            assertEquals(Action.WAITING, player4.getAction());
        }
    }
}