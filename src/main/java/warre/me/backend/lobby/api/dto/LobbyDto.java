package warre.me.backend.lobby.api.dto;

import warre.me.backend.shared.domain.dice.Dices;

import java.util.List;
import java.util.UUID;

public record LobbyDto(
        UUID lobbyId,
        UUID host,
        List<LobbyPlayerDto> lobbyPlayers,
        Dices dices
) {
}
