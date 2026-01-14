package warre.me.backend.chared.domain.cards.chance;

import warre.me.backend.chared.domain.board.property.Property;
import warre.me.backend.chared.domain.board.property.StreetType;
import warre.me.backend.chared.domain.cards.AdvanceToNearestStreetTypeCard;
import warre.me.backend.chared.domain.cards.AdvanceToPropertyCard;
import warre.me.backend.chared.domain.cards.Card;
import warre.me.backend.chared.domain.cards.GoToPlaceCard;

import java.util.List;

import static warre.me.backend.chared.domain.board.property.Property.PALL_MALL;
import static warre.me.backend.chared.domain.board.property.Property.TRAFALGAR_SQUARE;
import static warre.me.backend.chared.domain.cards.CardType.*;

public abstract class ChanceCards {
    private final static List<Card> cards= makeCards();


    private static List<Card> makeCards() {
        return List.of(
                new GoToPlaceCard("Advance to \"Go\". (Collect $200)", ADVANCE_TO_GO_COLLECT_200, 0, false),
                new AdvanceToPropertyCard(ADVANCE_TO_ILLINOIS_AVENUE, TRAFALGAR_SQUARE),
                new AdvanceToPropertyCard(ADVANCE_TO_ST_CHARLES_PLACE, PALL_MALL),
                new AdvanceToNearestStreetTypeCard("Advance token to the nearest Utility.", ADVANCE_TO_NEAREST_UTILITY, StreetType.UTILITY),
                new AdvanceToNearestStreetTypeCard("Advance token to the nearest Railroad.", ADVANCE_TO_NEAREST_RAILROAD, StreetType.UTILITY)
        );
    }
}
