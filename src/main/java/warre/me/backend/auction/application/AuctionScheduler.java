package warre.me.backend.auction.application;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AuctionScheduler {
    private final AuctionService auctionService;

    public AuctionScheduler(AuctionService auctionService) {
        this.auctionService = auctionService;
    }


    @Scheduled(fixedRate = 500 )
    public void checkTimerIsDone() {
        auctionService.checkTimerIsDone();
    }
}
