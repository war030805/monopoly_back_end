package warre.me.backend.game.domain.game;

import org.jmolecules.ddd.annotation.ValueObject;
import org.springframework.util.Assert;
import warre.me.backend.chared.domain.NotFoundException;
import warre.me.backend.chared.domain.NotFoundThrower;

import java.util.UUID;

@ValueObject
public record GameId(UUID id) implements NotFoundThrower {
    public GameId {
        Assert.notNull(id, "Id cannot be null");
    }

    @Override
    public String notFoundMessage() {
        return NotFoundException.createNotFoundMessageFromNameAndId("game", id);
    }
}