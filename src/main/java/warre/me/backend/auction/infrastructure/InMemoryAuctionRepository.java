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
    public boolean save(Auction auction) {
        return store.put(auction.getGameId(), auction)==null;
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
    public void saveAll(List<Auction> auctions) {
        auctions.forEach(this::save);
    }

    @Override
    public void removeById(GameId gameId) {
        store.remove(gameId);
    }

    @Override
    public Optional<Auction> findByGameId(GameId gameId) {
        return Optional.ofNullable(store.get(gameId));
    }
}
