package warre.me.backend.auction.domain;

import warre.me.backend.game.domain.game.GameId;

import java.util.Optional;

public interface AuctionRepository {

    Optional<Auction> findByGameId(GameId gameId);

    void save(Auction auction);
}
