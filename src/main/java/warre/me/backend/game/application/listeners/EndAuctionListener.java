package warre.me.backend.game.application.listeners;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import warre.me.backend.auction.application.AuctionService;
import warre.me.backend.game.application.GameService;
import warre.me.backend.game.application.event.EndAuctionEvent;

@Component
public class EndAuctionListener {
    private final GameService gameService;

    public EndAuctionListener(GameService gameService) {
        this.gameService = gameService;
    }

    @EventListener
    public void endAuction(EndAuctionEvent endAuctionEvent) {
        gameService.endAuction(
                endAuctionEvent.getGameId(),
                endAuctionEvent.getPriceToPay(),
                endAuctionEvent.getWinner(),
                endAuctionEvent.getProperty()
        );
    }
}
