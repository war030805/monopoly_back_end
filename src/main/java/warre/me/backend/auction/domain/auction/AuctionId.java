package warre.me.backend.auction.domain.auction;

import org.jmolecules.ddd.annotation.ValueObject;
import org.springframework.util.Assert;

import java.util.UUID;

@ValueObject
public record AuctionId(UUID id) {
    public AuctionId {
        Assert.notNull(id, "Id cannot be null");
    }
}