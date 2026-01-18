package warre.me.backend.auction.application.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import warre.me.backend.game.domain.game.Game;
import warre.me.backend.game.domain.gamePlayer.GamePlayer;

@Getter
public class StartAuctionEvent extends ApplicationEvent {
    private final GamePlayer starter;
    private final Game game;

    public StartAuctionEvent(Object source, GamePlayer starter, Game game) {
        super(source);
        this.starter = starter;
        this.game = game;
    }
}
