package warre.me.backend.auction.domain.auction;

import warre.me.backend.game.domain.game.GameId;

import java.util.List;
import java.util.Optional;

public interface AuctionRepository {

    Optional<Auction> findByGameIdAndIsNotDone(GameId gameId);

    boolean save(Auction auction);

    List<Auction> findAll();

    List<Auction> findAllThatAreNotDone();

    void saveAll(List<Auction> allNotDone);
}
