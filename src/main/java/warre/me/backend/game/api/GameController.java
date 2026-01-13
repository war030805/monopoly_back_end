package warre.me.backend.game.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import warre.me.backend.game.api.dto.GameDataDto;
import warre.me.backend.game.application.GameService;
import warre.me.backend.game.domain.game.GameId;
import warre.me.backend.player.domain.PlayerId;

import java.util.UUID;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/rollDice/{gameId}")
    public ResponseEntity<Void> rollDice(
            @PathVariable UUID gameId,
            @AuthenticationPrincipal Jwt token
    ) {
        PlayerId playerId= PlayerId.fromToken(token);
        gameService.rollDice(new GameId(gameId), playerId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/move/{gameId}")
    public ResponseEntity<Void> move(
            @PathVariable UUID gameId,
            @AuthenticationPrincipal Jwt token
    ) {
        PlayerId playerId= PlayerId.fromToken(token);
        gameService.move(new GameId(gameId), playerId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/buyProperty/{gameId}")
    public ResponseEntity<Void> buyProperty(
            UUID gameId,
            @AuthenticationPrincipal Jwt token
    ) {
        PlayerId playerId= PlayerId.fromToken(token);
        gameService.buyProperty(new GameId(gameId), playerId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/landOnOtherProperty/{gameId}")
    public ResponseEntity<Void> payLandOnOtherProperty(
            @PathVariable UUID gameId,
            @AuthenticationPrincipal Jwt token
    ) {
        PlayerId playerId= PlayerId.fromToken(token);
        gameService.payLandOnOtherProperty(new GameId(gameId), playerId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<GameDataDto> getGameData(
            @PathVariable UUID gameId,
            @AuthenticationPrincipal Jwt token
    ) {
        PlayerId playerId= PlayerId.fromToken(token);

        GameDataDto gameDataDto= gameService.getGameData(new GameId(gameId), playerId);
        return ResponseEntity.ok(gameDataDto);
    }
}
