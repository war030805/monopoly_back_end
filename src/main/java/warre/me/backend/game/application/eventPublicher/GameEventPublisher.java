package warre.me.backend.game.application.eventPublicher;


import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import warre.me.backend.auction.application.event.StartAuctionEvent;
import warre.me.backend.game.domain.game.Game;
import warre.me.backend.game.domain.gamePlayer.GamePlayer;

@Component
public class GameEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public GameEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void startAuction(Game game, GamePlayer player) {
        applicationEventPublisher.publishEvent(new StartAuctionEvent(
                this,
                player,
                game
        ));
    }
}
