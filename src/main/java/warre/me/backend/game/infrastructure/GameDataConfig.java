package warre.me.backend.game.infrastructure;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;
import warre.me.backend.game.domain.game.Game;
import warre.me.backend.game.domain.game.GameId;
import warre.me.backend.game.domain.game.GameRepository;
import warre.me.backend.game.domain.gamePlayer.GamePlayer;
import warre.me.backend.player.domain.PlayerId;
import warre.me.backend.lobby.domain.lobby.LobbyId;

import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Transactional
@Configuration
public class GameDataConfig {
    private final GameRepository gameRepository;

    public GameDataConfig(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }


    @EventListener(ApplicationReadyEvent.class)
    public void onStartup() {
        var currentPlayer = new GamePlayer(
                new PlayerId(UUID.fromString("a8befecd-694e-4748-b00c-a80fbf7b909e")),
                0,
                "red"
        );

        var player2 = new GamePlayer(
                new PlayerId(UUID.fromString("a8207180-13fa-410e-8def-8583f394e5bf")),
                1,
                "purple"
        );



//        var player2= makePLayerWithStep("purple", 1,0);
//        var player3= makePLayerWithStep("green", 2,0);
//        var player4= makePLayerWithStep("yellow", 3,0);
//        var player5= makePLayerWithStep("white", 4,0);
//        var player6= makePLayerWithStep("black", 5,0);



        currentPlayer.addToPlace(0);

        Map<PlayerId, GamePlayer> playerMap = Stream.of(
                        currentPlayer
                        ,player2
                        )
                .collect(Collectors.toMap(GamePlayer::getPlayerId, Function.identity()));

        var game = new Game(
                new GameId(UUID.fromString("969ae20c-7641-4e3a-bfc8-ab3a16ff5b84")),
                LobbyId.random(),
                playerMap,
                currentPlayer.getPlayerId()
        );

        gameRepository.save(game);
    }

    private GamePlayer makePLayerWithStep(String color, int movePlace, int placeMove) {
        var player2= new GamePlayer(PlayerId.random(), movePlace, color);

        player2.addToPlace(placeMove);

        return player2;
    }
}
