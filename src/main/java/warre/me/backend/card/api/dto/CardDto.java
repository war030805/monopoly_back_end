package warre.me.backend.card.api.dto;

import warre.me.backend.shared.domain.cards.Card;
import warre.me.backend.shared.domain.cards.DeckType;
import warre.me.backend.shared.domain.cards.cardTypes.CardSpecificType;
import warre.me.backend.shared.domain.cards.cardTypes.CardType;

public record CardDto(
        CardSpecificType cardSpecificType,
        String name,
        DeckType deckType,
        boolean canSave,
        CardType cardType
) {
    public static CardDto fromDomain(Card card) {
        return new CardDto(
                card.getCardSpecificType(),
                card.getName(),
                card.getDeckType(),
                card.canSave(),
                card.getCardType()
        );
    }



}
