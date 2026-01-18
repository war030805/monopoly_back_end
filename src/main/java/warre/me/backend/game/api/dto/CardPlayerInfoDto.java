package warre.me.backend.game.api.dto;

import warre.me.backend.card.api.dto.CardDto;
import warre.me.backend.shared.domain.cards.Card;
import warre.me.backend.game.domain.game.Game;
import warre.me.backend.game.domain.gamePlayer.GamePlayer;

public record CardPlayerInfoDto(
        CardDto card,
        int moneyToPay,
        int placeEndsUpOn
) {
    public static CardPlayerInfoDto fromDomain(Card card, GamePlayer gamePlayer, Game game) {
        return new CardPlayerInfoDto(
                CardDto.fromDomain(card),
                card.getMoneyToPay(game, gamePlayer),
                card.calcPlaceEndsUpOn(gamePlayer)
        );
    }
}
