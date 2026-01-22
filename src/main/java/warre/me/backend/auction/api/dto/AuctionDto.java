package warre.me.backend.auction.api.dto;

import warre.me.backend.auction.domain.auction.Auction;
import warre.me.backend.shared.domain.board.Board;
import warre.me.backend.shared.domain.board.property.Property;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record AuctionDto(
        UUID auctionId,
        UUID gameId,
        List<AuctionPlayerDto> auctionPlayers,
        Property property,
        UUID starter,
        LocalDateTime lastBetTime,
        int place,
        AuctionPlayerDto winner,
        int maxBet
) {
    public static AuctionDto fromDomain(Auction auction) {
        return new AuctionDto(
                auction.getAuctionId().id(),
                auction.getGameId().id(),
                auction.getMembers().stream()
                        .map(AuctionPlayerDto::fromDomain)
                        .toList(),
                auction.getProperty(),
                auction.getStarter().id(),
                auction.getLastBetTime(),
                Board.getPlaceOfProperty(auction.getProperty()),
                auction.getWinner()
                        .map(AuctionPlayerDto::fromDomain)
                        .orElse(null),
                auction.getHighestBet().getBet()
        );
    }
}
