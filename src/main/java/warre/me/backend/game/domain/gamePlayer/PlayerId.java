package warre.me.backend.game.domain.gamePlayer;

import org.jmolecules.ddd.annotation.ValueObject;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.Assert;
import warre.me.backend.chared.domain.NotFoundException;
import warre.me.backend.chared.domain.NotFoundThrower;

import java.util.UUID;

@ValueObject
public record PlayerId(UUID id) implements NotFoundThrower {
    public PlayerId {
        Assert.notNull(id, "Id cannot be null");
    }

    public static PlayerId fromToken(Jwt token) {
        return new PlayerId(UUID.fromString(token.getSubject()));
    }

    public static PlayerId random() {
        return new PlayerId(UUID.randomUUID());
    }

    @Override
    public String notFoundMessage() {
        return NotFoundException.createNotFoundMessageFromNameAndId("player", id);
    }
}