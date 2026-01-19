package warre.me.backend.auction.infrastructure;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import warre.me.backend.auction.domain.Auction;
import warre.me.backend.auction.domain.AuctionRepository;
import warre.me.backend.game.domain.game.Game;
import warre.me.backend.game.domain.game.GameId;
import warre.me.backend.game.domain.game.GameRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


@Repository
@Profile("inmemory")
public class InMemoryAuctionRepository implements AuctionRepository {
    private final Map<GameId, Auction> store = new ConcurrentHashMap<>();

    @Override
    public void save(Auction auction) {
        store.put(auction.getGameId(), auction);
    }

    @Override
    public List<Auction> findAllAuctions() {
        return store.values().stream().toList();
    }

    @Override
    public Optional<Auction> findByGameId(GameId gameId) {
        return Optional.ofNullable(store.get(gameId));
    }
}
