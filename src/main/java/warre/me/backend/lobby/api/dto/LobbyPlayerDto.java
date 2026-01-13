package warre.me.backend.lobby.api.dto;

import java.util.UUID;

public record LobbyPlayerDto(
        UUID playerId,
        int movePlace,
        String color
) {
}
