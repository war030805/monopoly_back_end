package warre.me.backend.auction.api.dto;

import warre.me.backend.auction.domain.AuctionPlayer;

import java.util.UUID;

public record AuctionPlayerDto(
        UUID playerId,
        int money,
        int bet,
        boolean betting,
        boolean isBankrupt,
        String color
) {

    public static AuctionPlayerDto fromDomain(AuctionPlayer auctionPlayer) {
        return new AuctionPlayerDto(
                auctionPlayer.getPlayerId().id(),
                auctionPlayer.getMoney(),
                auctionPlayer.getBet(),
                auctionPlayer.isBetting(),
                auctionPlayer.isBankrupt(),
                auctionPlayer.getColor()
        );
    }
}
