package warre.me.backend.auction.infrastructure;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import warre.me.backend.auction.domain.auction.Auction;
import warre.me.backend.auction.domain.auction.AuctionId;
import warre.me.backend.auction.domain.auction.AuctionRepository;
import warre.me.backend.game.domain.game.GameId;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


@Repository
@Profile("inmemory")
public class InMemoryAuctionRepository implements AuctionRepository {
    private final Map<AuctionId, Auction> store = new ConcurrentHashMap<>();

    @Override
    public boolean save(Auction auction) {
        return store.put(auction.getAuctionId(), auction)==null;
    }

    @Override
    public List<Auction> findAll() {
        return store.values().stream().toList();
    }

    @Override
    public List<Auction> findAllThatAreNotDone() {
        return findAll().parallelStream()
                .filter(auction -> !auction.isDone())
                .toList();
    }

    @Override
    public void saveAll(List<Auction> allNotDone) {
        allNotDone.forEach(this::save);
    }

    @Override
    public Optional<Auction> findByGameIdAndIsNotDone(GameId gameId) {
        return findAllThatAreNotDone().stream()
                .filter(auction -> auction.getGameId().equals(gameId))
                .findAny();
    }
}
