package warre.me.backend.auction.domain.auction;

import org.jmolecules.ddd.annotation.ValueObject;
import org.springframework.util.Assert;
import warre.me.backend.shared.domain.NotFoundException;
import warre.me.backend.shared.domain.NotFoundThrower;

import java.util.UUID;

@ValueObject
public record AuctionId(UUID id) implements NotFoundThrower {
    public AuctionId {
        Assert.notNull(id, "Id cannot be null");
    }

    public static AuctionId random() {
        return new AuctionId(UUID.randomUUID());
    }

    @Override
    public String notFoundMessage() {
        return NotFoundException.createNotFoundMessageFromNameAndId("auction", id);
    }
}