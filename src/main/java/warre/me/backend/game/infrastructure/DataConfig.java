package warre.me.backend.game.infrastructure;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;
import warre.me.backend.game.domain.game.Game;
import warre.me.backend.game.domain.game.GameId;
import warre.me.backend.game.domain.game.GameRepository;
import warre.me.backend.game.domain.gamePlayer.GamePlayer;
import warre.me.backend.game.domain.gamePlayer.PlayerId;

import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
public class DataConfig {
    private final GameRepository gameRepository;

    public DataConfig(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void onStartup() {
        var currentPlayer = new GamePlayer(
                new PlayerId(UUID.fromString("a8befecd-694e-4748-b00c-a80fbf7b909e")),
                0,
                "red"
        );



        var player2= makePLayerWithStep("purple", 1,3);
        var player3= makePLayerWithStep("green", 2,3);
        var player4= makePLayerWithStep("yellow", 3,3);
        var player5= makePLayerWithStep("white", 4,3);
        var player6= makePLayerWithStep("black", 5,3);



        currentPlayer.addToPlace(3);

        Map<PlayerId, GamePlayer> playerMap = Stream.of(
                        currentPlayer,player2, player3, player4, player5, player6
                        )
                .collect(Collectors.toMap(GamePlayer::getPlayerId, Function.identity()));

        var game = new Game(
                new GameId(UUID.fromString("969ae20c-7641-4e3a-bfc8-ab3a16ff5b84")),
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
