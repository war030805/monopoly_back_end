package warre.me.backend.auction.application.listeners;

import org.springframework.transaction.event.TransactionalEventListener;
import warre.me.backend.auction.application.AuctionService;
import warre.me.backend.auction.application.event.StartAuctionEvent;

import static org.springframework.transaction.event.TransactionPhase.BEFORE_COMMIT;

public class StartAuctionListener {
    private final AuctionService auctionService;

    public StartAuctionListener(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @TransactionalEventListener(phase = BEFORE_COMMIT)
    public void startAuction(StartAuctionEvent startAuctionEvent) {
        auctionService.makeAuction(startAuctionEvent.getGame(), startAuctionEvent.getStarter());
    }
}
