package warre.me.backend.auction.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import warre.me.backend.auction.domain.Auction;
import warre.me.backend.auction.domain.AuctionRepository;
import warre.me.backend.game.domain.game.Game;
import warre.me.backend.game.domain.game.GameId;
import warre.me.backend.game.domain.gamePlayer.GamePlayer;
import warre.me.backend.player.domain.PlayerId;

import java.util.List;

@Service
@Transactional
public class AuctionService {
    private final AuctionRepository auctionRepository;

    public AuctionService(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }


    public void makeAuction(Game game, GamePlayer starter) {
        Auction auction= Auction.makeAuction(game, starter);

        auctionRepository.save(auction);
    }

    public Auction getAuction(GameId gameId, PlayerId playerId) {
        Auction auction=auctionRepository.findByGameId(gameId)
                .orElseThrow(gameId::notFound);

        auction.isMember(playerId);

        return auction;
    }

    public void placeBet(GameId gameId, PlayerId playerId, int bet) {
        Auction auction=auctionRepository.findByGameId(gameId)
                .orElseThrow(gameId::notFound);

        auction.placeBet(playerId, bet);

        auctionRepository.save(auction);
    }

    public void passBet(GameId gameId, PlayerId playerId) {
        Auction auction=auctionRepository.findByGameId(gameId)
                .orElseThrow(gameId::notFound);

        auction.passBet(playerId);

        auctionRepository.save(auction);
    }

    public void checkTimerIsDone() {
        auctionRepository.findAllAuctions()
                .forEach();
    }
}
