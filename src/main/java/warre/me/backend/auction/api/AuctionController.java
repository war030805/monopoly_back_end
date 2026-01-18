package warre.me.backend.auction.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import warre.me.backend.auction.api.dto.AuctionDto;
import warre.me.backend.auction.application.AuctionService;
import warre.me.backend.auction.domain.Auction;
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
        Auction auction= auctionService.getAuction(new GameId(gameId), PlayerId.fromToken(token));

        return ResponseEntity.ok(AuctionDto.fromDomain(auction));
    }
}
