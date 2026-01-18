package warre.me.backend.auction.domain;

import lombok.Getter;
import warre.me.backend.game.domain.gamePlayer.GamePlayer;
import warre.me.backend.player.domain.PlayerId;

@Getter
public class AuctionPlayer implements Comparable<AuctionPlayer>{
    private final PlayerId playerId;
    private final int money;

    private int bet=0;

    private boolean betting;
    private final boolean isBankrupt;

    public AuctionPlayer(PlayerId playerId, int money, boolean betting, boolean isBankrupt) {
        this.playerId = playerId;
        this.money = money;
        this.betting=betting;
        this.isBankrupt=isBankrupt;
    }

    public static AuctionPlayer makePLayer(GamePlayer gamePlayer) {
        return new AuctionPlayer(
                gamePlayer.getPlayerId(),
                gamePlayer.getMoney(),
                !gamePlayer.isBankrupt(),
                gamePlayer.isBankrupt()
        );
    }

    @Override
    public int compareTo(AuctionPlayer o) {
        return Integer.compare(o.bet, bet);
    }
}
