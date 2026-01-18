package warre.me.backend.game.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import warre.me.backend.shared.domain.cards.cardTypes.CardSpecificType;
import warre.me.backend.game.api.dto.GameDataDto;
import warre.me.backend.game.application.GameService;
import warre.me.backend.game.domain.game.GameId;
import warre.me.backend.game.domain.gamePlayer.DoAction;
import warre.me.backend.player.domain.PlayerId;

import java.util.UUID;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/rollDice/{gameId}/{doAction}")
    public ResponseEntity<Void> rollDice(
            @PathVariable UUID gameId,
            @PathVariable DoAction doAction,
            @AuthenticationPrincipal Jwt token
    ) {
        PlayerId playerId= PlayerId.fromToken(token);
        gameService.rollDice(new GameId(gameId), playerId, doAction);

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
            @PathVariable UUID gameId,
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

    @PostMapping("/endMove/{gameId}")
    public ResponseEntity<Void> endMove(
            @PathVariable UUID gameId,
            @AuthenticationPrincipal Jwt token
    ) {
        PlayerId playerId= PlayerId.fromToken(token);
        gameService.endMove(new GameId(gameId), playerId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/payTax/{gameId}")
    public ResponseEntity<Void> payTax(
            @PathVariable UUID gameId,
            @AuthenticationPrincipal Jwt token
    ) {
        PlayerId playerId= PlayerId.fromToken(token);
        gameService.payTax(new GameId(gameId), playerId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/pullCard/{gameId}")
    public ResponseEntity<Void> pullCard(
            @PathVariable UUID gameId,
            @AuthenticationPrincipal Jwt token
    ) {
        PlayerId playerId= PlayerId.fromToken(token);
        gameService.pullCard(new GameId(gameId), playerId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/saveCard/{gameId}")
    public ResponseEntity<Void> saveCard(
            @PathVariable UUID gameId,
            @AuthenticationPrincipal Jwt token
    ) {
        PlayerId playerId= PlayerId.fromToken(token);
        gameService.saveCard(new GameId(gameId), playerId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/useCard/{gameId}")
    public ResponseEntity<Void> useCard(
            @PathVariable UUID gameId,
            @AuthenticationPrincipal Jwt token
    ) {
        PlayerId playerId= PlayerId.fromToken(token);
        gameService.useCard(new GameId(gameId), playerId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/useSavedCard/{gameId}/{cardSpecificType}")
    public ResponseEntity<Void> useSavedCard(
            @PathVariable UUID gameId,
            @PathVariable CardSpecificType cardSpecificType,
            @AuthenticationPrincipal Jwt token
    ) {
        PlayerId playerId= PlayerId.fromToken(token);
        gameService.useSavedCard(new GameId(gameId), playerId, cardSpecificType);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/startAuction/{gameId}/{cardSpecificType}")
    public ResponseEntity<Void> startAuction(
            @PathVariable UUID gameId,
            @AuthenticationPrincipal Jwt token
    ) {
        PlayerId playerId= PlayerId.fromToken(token);
        gameService.startAuction(new GameId(gameId), playerId);
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
