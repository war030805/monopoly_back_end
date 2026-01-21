package warre.me.backend.game.application.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import warre.me.backend.game.domain.game.GameId;
import warre.me.backend.player.domain.PlayerId;
import warre.me.backend.shared.domain.board.property.Property;

@Getter
public class EndAuctionEvent extends ApplicationEvent {
    private final int priceToPay;
    private final PlayerId winner;
    private final GameId gameId;
    private final Property property;

    public EndAuctionEvent(Object source, int priceToPay, PlayerId winner, GameId gameId, Property property) {
        super(source);
        this.priceToPay = priceToPay;
        this.winner = winner;
        this.gameId = gameId;
        this.property = property;
    }
}
