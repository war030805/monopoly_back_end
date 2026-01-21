package warre.me.backend.auction.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import warre.me.backend.auction.api.dto.AuctionDto;
import warre.me.backend.auction.api.dto.BetDto;
import warre.me.backend.auction.application.AuctionService;
import warre.me.backend.auction.domain.auction.Auction;
import warre.me.backend.game.domain.game.GameId;
import warre.me.backend.player.domain.PlayerId;

import java.util.UUID;

@RestController
@RequestMapping("/api/auction")
public class AuctionController {

    private final AuctionService auctionService;

    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @GetMapping("/{gameId}")
    private ResponseEntity<AuctionDto> getAuction(
            @PathVariable UUID gameId,
            @AuthenticationPrincipal Jwt token
    ) {
        Auction auction = auctionService.getAuction(new GameId(gameId), PlayerId.fromToken(token));

        return ResponseEntity.ok(AuctionDto.fromDomain(auction));
    }

    @PostMapping("/placeBet/{gameId}")
    private ResponseEntity<Void> placeBet(
            @PathVariable UUID gameId,
            @AuthenticationPrincipal Jwt token,
            @RequestBody BetDto bet
    ) {
        auctionService.placeBet(new GameId(gameId), PlayerId.fromToken(token), bet.bet());

        return ResponseEntity.ok().build();
    }


    @PostMapping("/pass/{gameId}")
    private ResponseEntity<Void> passBet(
            @PathVariable UUID gameId,
            @AuthenticationPrincipal Jwt token
    ) {
        auctionService.passBet(new GameId(gameId), PlayerId.fromToken(token));

        return ResponseEntity.ok().build();
    }

    @PostMapping("/closeAuction/{gameId}")
    private ResponseEntity<Void> closeAuction(
            @PathVariable UUID gameId,
            @AuthenticationPrincipal Jwt token
    ) {
        auctionService.closeAuction(new GameId(gameId), PlayerId.fromToken(token));

        return ResponseEntity.ok().build();
    }
}
