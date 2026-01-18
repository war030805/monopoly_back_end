package warre.me.backend.shared.domain.cards.chance;

import warre.me.backend.shared.domain.board.property.StreetType;
import warre.me.backend.shared.domain.cards.*;
import warre.me.backend.shared.domain.cards.cardTypes.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static warre.me.backend.shared.domain.board.property.Property.*;
import static warre.me.backend.shared.domain.cards.cardTypes.CardSpecificType.*;

public abstract class ChanceCards {
    private final static Map<CardSpecificType, Card> cards = makeCards();


    private static Map<CardSpecificType, Card> makeCards() {
        return Stream.of(
                        new GoToPlaceCard("Advance to \"Go\". (Collect $200)", ADVANCE_TO_GO_COLLECT_200,
                                0, false, DeckType.CHANCE),

                        new AdvanceToPropertyCard(ADVANCE_TO_ILLINOIS_AVENUE, TRAFALGAR_SQUARE, DeckType.CHANCE),
                        new AdvanceToPropertyCard(ADVANCE_TO_ST_CHARLES_PLACE, PALL_MALL, DeckType.CHANCE),
                        new AdvanceToNearestStreetTypeCard("Advance token to the nearest Utility.",
                                ADVANCE_TO_NEAREST_UTILITY, StreetType.UTILITY, DeckType.CHANCE),
                        makeRailRoadCard(),

                        // 6) Dividend +50
                        new MoneyTransactionCard("Bank pays you dividend of $50.", BANK_PAYS_DIVIDEND_50, 50,
                                false, DeckType.CHANCE),

                        // 7) Get out of jail free
                        new GetOutOfJailCard("Get out of Jail Free.", DeckType.CHANCE),

                        // 8) Go back 3 spaces
                        new MoveRelativeCard("Go Back Three Spaces.", GO_BACK_THREE_SPACES, -3, DeckType.CHANCE),

                        // 9) Go to Jail
                        new GoToPlaceCard("Go to Jail. Go directly to Jail. Do not pass GO, do not collect $200.",
                                GO_TO_JAIL, -1, true, DeckType.CHANCE),

                        // 10) General repairs
                        new PaysPerHouseAndHotelCard(
                                "Make general repairs on all your property: For each house pay $25, For each hotel $100.",
                                GENERAL_REPAIRS_PAY_PER_HOUSE_25_PER_HOTEL_100,
                                25,
                                100,
                                DeckType.CHANCE
                        ),

                        // 11) Trip to Reading Railroad (in UK: King’s Cross)
                        new AdvanceToPropertyCard(TRIP_TO_READING_RAILROAD, KINGS_CROSS_STATION, DeckType.CHANCE),

                        // 12) Boardwalk (in UK: Mayfair)
                        new AdvanceToPropertyCard(ADVANCE_TO_BOARDWALK, MAYFAIR, DeckType.CHANCE),

                        // 13) Chairman: pay each player 50
                        new PayEachPlayerCard("You have been elected Chairman of the Board. Pay each player $50.",
                                ELECTED_CHAIRMAN_PAY_EACH_PLAYER_50, 50, DeckType.CHANCE),

                        // 14) Building & loan matures +150
                        new MoneyTransactionCard("Your building and loan matures. Collect $150.",
                                BUILDING_AND_LOAN_MATURES_COLLECT_150, 150, false, DeckType.CHANCE)
                )
                .collect(Collectors.toMap(Card::getCardSpecificType, Function.identity()));
    }

    private static Card makeRailRoadCard() {
        return new AdvanceToNearestStreetTypeCard("Advance token to the nearest Railroad.", ADVANCE_TO_NEAREST_RAILROAD, StreetType.STATION, DeckType.CHANCE);
    }

    public static List<Card> makeDeck() {
        var deck = new ArrayList<>(cards.values());

        deck.add(makeRailRoadCard());

        Collections.shuffle(deck);

        return deck;
    }

    public static Card getCard(CardSpecificType cardSpecificType) {
        return cards.get(cardSpecificType);
    }

    public static List<Card> getAllCards() {
        return cards.values().stream().toList();
    }
}
