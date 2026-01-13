package warre.me.backend.lobby.domain.lobby;

import org.jmolecules.ddd.annotation.ValueObject;
import org.springframework.util.Assert;

import java.util.UUID;

@ValueObject
public record LobbyId(UUID id) {
    public LobbyId {
        Assert.notNull(id, "Id cannot be null");
    }

    public static LobbyId random() {
        return new LobbyId(UUID.randomUUID());
    }
}