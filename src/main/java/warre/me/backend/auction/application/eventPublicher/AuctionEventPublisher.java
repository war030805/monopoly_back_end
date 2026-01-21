package warre.me.backend.auction.application.eventPublicher;


import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import warre.me.backend.auction.domain.auction.Auction;
import warre.me.backend.game.application.event.EndAuctionEvent;

@Component
public class AuctionEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public AuctionEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void endAuction(Auction auction) {
        var winner= auction.getWinner()
                        .orElseThrow();

        applicationEventPublisher.publishEvent(new EndAuctionEvent(
                this,
                winner.getBet(),
                winner.getPlayerId(),
                auction.getGameId(),
                auction.getProperty()
        ));
    }
}
