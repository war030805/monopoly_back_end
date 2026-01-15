package warre.me.backend.card.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import warre.me.backend.card.application.CardService;
import warre.me.backend.card.api.dto.CardDto;

import java.util.List;

@RestController
@RequestMapping("/api/card")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }


    @GetMapping("/communityCards")
    public ResponseEntity<List<CardDto>> getCommunityCards(
    ) {
        var cards= cardService.getCommunityCards();
        return ResponseEntity.ok(cards.stream()
                .map(CardDto::fromDomain)
                .toList());
    }

    @GetMapping("/chanceCards")
    public ResponseEntity<List<CardDto>> getChanceCards(
    ) {
        var cards= cardService.getChanceCards();
        return ResponseEntity.ok(cards.stream()
                .map(CardDto::fromDomain)
                .toList());
    }
}
