package warre.me.backend.auction.domain;

import lombok.Getter;
import warre.me.backend.auction.domain.auction.AuctionException;
import warre.me.backend.game.domain.game.MoneyException;
import warre.me.backend.game.domain.gamePlayer.GamePlayer;
import warre.me.backend.player.domain.PlayerId;

@Getter
public class AuctionPlayer implements Comparable<AuctionPlayer>{
    private final PlayerId playerId;
    private final int money;

    private int bet=0;

    private boolean betting;
    private final boolean isBankrupt;

    private final String color;

    public AuctionPlayer(PlayerId playerId, int money, boolean betting, boolean isBankrupt, String color) {
        this.playerId = playerId;
        this.money = money;
        this.betting=betting;
        this.isBankrupt=isBankrupt;
        this.color = color;
    }

    public static AuctionPlayer makePLayer(GamePlayer gamePlayer) {
        return new AuctionPlayer(
                gamePlayer.getPlayerId(),
                gamePlayer.getMoney(),
                !gamePlayer.isBankrupt(),
                gamePlayer.isBankrupt(),
                gamePlayer.getColor()
        );
    }

    private void isBettingCheck() {
        if (!isBetting()) {
            throw new AuctionException("cannot do actions when not betting");
        }
    }

    @Override
    public int compareTo(AuctionPlayer o) {
        return Integer.compare(o.bet, bet);
    }

    public void betAmount(int betSetting) {
        isBettingCheck();
        if (betSetting> money) {
            throw new MoneyException("cannot bet this amount");
        }
        bet=betSetting;
    }

    public void passBet() {
        isBettingCheck();
        betting=false;
    }
}
