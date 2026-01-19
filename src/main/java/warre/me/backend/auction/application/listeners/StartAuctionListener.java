package warre.me.backend.auction.application.listeners;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import warre.me.backend.auction.application.AuctionService;
import warre.me.backend.auction.application.event.StartAuctionEvent;

@Component
public class StartAuctionListener {
    private final AuctionService auctionService;

    public StartAuctionListener(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @EventListener
    public void startAuction(StartAuctionEvent startAuctionEvent) {
        auctionService.makeAuction(startAuctionEvent.getGame(), startAuctionEvent.getStarter());
    }
}
