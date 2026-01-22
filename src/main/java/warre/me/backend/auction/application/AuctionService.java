package warre.me.backend.auction.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import warre.me.backend.auction.application.eventPublicher.AuctionEventPublisher;
import warre.me.backend.auction.domain.auction.Auction;
import warre.me.backend.auction.domain.auction.AuctionException;
import warre.me.backend.auction.domain.auction.AuctionRepository;
import warre.me.backend.game.domain.game.Game;
import warre.me.backend.game.domain.game.GameId;
import warre.me.backend.game.domain.gamePlayer.GamePlayer;
import warre.me.backend.player.domain.PlayerId;

@Service
@Transactional
public class AuctionService {
    private final AuctionRepository auctionRepository;
    private final AuctionEventPublisher eventPublisher;

    public AuctionService(AuctionRepository auctionRepository, AuctionEventPublisher eventPublisher) {
        this.auctionRepository = auctionRepository;
        this.eventPublisher = eventPublisher;
    }


    public void makeAuction(Game game, GamePlayer starter) {
        Auction auction = Auction.makeAuction(game, starter);

        auctionRepository.save(auction);
    }

    public Auction getAuction(GameId gameId, PlayerId playerId) {
        Auction auction = auctionRepository.findByGameIdAndIsNotClosed(gameId)
                .orElseThrow(gameId::notFound);

        auction.isMember(playerId);

        return auction;
    }

    public void placeBet(GameId gameId, PlayerId playerId, int bet) {
        Auction auction = auctionRepository.findByGameIdAndIsNotClosed(gameId)
                .orElseThrow(gameId::notFound);

        auction.placeBet(playerId, bet);

        auctionRepository.save(auction);
    }

    public void passBet(GameId gameId, PlayerId playerId) {
        Auction auction = auctionRepository.findByGameIdAndIsNotClosed(gameId)
                .orElseThrow(gameId::notFound);

        auction.passBet(playerId);

        auctionRepository.save(auction);
    }

    public void checkTimerIsDone() {
        var allNotDone = auctionRepository.findAllThatAreNotDone();

        allNotDone.parallelStream()
                        .forEach(Auction::resetTimerIfNotBet);

        allNotDone.parallelStream()
                .forEach(Auction::checkHasToEnd);

        auctionRepository.saveAll(allNotDone);
    }

    public void closeAuction(GameId gameId, PlayerId playerId) {
        Auction auction = auctionRepository.findByGameIdAndIsNotClosed(gameId)
                .orElseThrow(gameId::notFound);

        auction.getAuctionPLayer(playerId)
                        .orElseThrow(() -> new AuctionException("player is not part of auction"));

        auction.closeAuction();

        eventPublisher.endAuction(auction);

        auctionRepository.save(auction);
    }
}
