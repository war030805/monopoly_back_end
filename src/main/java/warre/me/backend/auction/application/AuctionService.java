package warre.me.backend.auction.application;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import warre.me.backend.auction.domain.Auction;
import warre.me.backend.auction.domain.AuctionException;
import warre.me.backend.auction.domain.AuctionRepository;
import warre.me.backend.game.domain.game.Game;
import warre.me.backend.game.domain.game.GameId;
import warre.me.backend.game.domain.gamePlayer.GamePlayer;
import warre.me.backend.player.domain.PlayerId;

import java.time.Instant;

@Service
@Transactional
public class AuctionService {
    private final AuctionRepository auctionRepository;
    private final TaskScheduler taskScheduler;

    public AuctionService(AuctionRepository auctionRepository, TaskScheduler taskScheduler) {
        this.auctionRepository = auctionRepository;
        this.taskScheduler = taskScheduler;
    }


    public void makeAuction(Game game, GamePlayer starter) {
        Auction auction = Auction.makeAuction(game, starter);

        var existingAuctionOptional = auctionRepository.findByGameId(game.getGameId());

        if (existingAuctionOptional.isPresent()) {
            var existingAuction = existingAuctionOptional.get();

            if (!existingAuction.isDone()) {
                throw new AuctionException("Auction exists");
            }
        }


        auctionRepository.save(auction);
    }

    public Auction getAuction(GameId gameId, PlayerId playerId) {
        Auction auction = auctionRepository.findByGameId(gameId)
                .orElseThrow(gameId::notFound);

        auction.isMember(playerId);

        return auction;
    }

    public void placeBet(GameId gameId, PlayerId playerId, int bet) {
        Auction auction = auctionRepository.findByGameId(gameId)
                .orElseThrow(gameId::notFound);

        auction.placeBet(playerId, bet);

        auctionRepository.save(auction);
    }

    public void passBet(GameId gameId, PlayerId playerId) {
        Auction auction = auctionRepository.findByGameId(gameId)
                .orElseThrow(gameId::notFound);

        auction.passBet(playerId);

        auctionRepository.save(auction);
    }

    public void checkTimerIsDone() {
        var allNotDone = auctionRepository.findAllThatAreNotDone();

        allNotDone.parallelStream()
                .forEach(Auction::checkHasToEnd);

        auctionRepository.saveAll(allNotDone);

        allNotDone.parallelStream()
                .filter(Auction::isDone)
                .map(Auction::getGameId)
                .forEach(gameId ->
                        taskScheduler.schedule(() ->
                                        auctionRepository.removeById(gameId),
                                Instant.now().plusSeconds(30))
                );
    }



}
