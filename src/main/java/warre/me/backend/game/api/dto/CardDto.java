package warre.me.backend.game.api.dto;

import warre.me.backend.chared.domain.cards.cardTypes.CardType;

public record CardDto(
        CardType cardType,
        String name
) {
}
