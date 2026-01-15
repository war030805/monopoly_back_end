package warre.me.backend.card.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import warre.me.backend.chared.domain.board.Board;
import warre.me.backend.chared.domain.board.tile.Tile;
import warre.me.backend.chared.domain.cards.Card;
import warre.me.backend.chared.domain.cards.chance.ChanceCards;
import warre.me.backend.chared.domain.cards.communityChest.CommunityChestCards;

import java.util.List;

@Service
@Transactional
public class CardService {

    public List<Card> getCommunityCards() {
        return CommunityChestCards.getAllCards();
    }

    public List<Card> getChanceCards() {
        return ChanceCards.getAllCards();
    }
}
