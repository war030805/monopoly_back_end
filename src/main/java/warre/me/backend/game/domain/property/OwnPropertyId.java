package warre.me.backend.game.domain.property;

import org.jmolecules.ddd.annotation.ValueObject;
import org.springframework.util.Assert;

import java.util.UUID;

@ValueObject
public record OwnPropertyId(UUID id) {
    public OwnPropertyId {
        Assert.notNull(id, "Id cannot be null");
    }

    public static OwnPropertyId random() {
        return new OwnPropertyId(UUID.randomUUID());
    }
}