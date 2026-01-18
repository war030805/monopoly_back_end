package warre.me.backend.card.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import warre.me.backend.shared.domain.cards.Card;
import warre.me.backend.shared.domain.cards.chance.ChanceCards;
import warre.me.backend.shared.domain.cards.communityChest.CommunityChestCards;

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
