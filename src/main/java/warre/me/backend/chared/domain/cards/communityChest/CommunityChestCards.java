package warre.me.backend.chared.domain.cards.communityChest;

import warre.me.backend.chared.domain.cards.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static warre.me.backend.chared.domain.cards.CardType.*;

public abstract class CommunityChestCards {
    private static final Map<CardType, Card> cards = makeCards();

    private static Map<CardType, Card> makeCards() {
        return Stream.of(
                        // Advance to Go (Collect $200)
                        new GoToPlaceCard("Advance to \"Go\". (Collect $200)",
                                ADVANCE_TO_GO_COLLECT_200, 0, false),

                        // Bank error in your favor. Collect $200.
                        new MoneyTransactionCard("Bank error in your favor. Collect $200.",
                                BANK_ERROR_IN_YOUR_FAVOR_COLLECT_200, 200, false),

                        // Doctor's fees. Pay $50.
                        new MoneyTransactionCard("Doctor's fees. Pay $50.",
                                DOCTORS_FEES_PAY_50, 50, true),

                        // From sale of stock you get $50.
                        new MoneyTransactionCard("From sale of stock you get $50.",
                                SALE_OF_STOCK_COLLECT_50, 50, false),

                        // Get Out of Jail Free.
                        new GetOutOfJailCard("Get Out of Jail Free."),

                        // Go to Jail...
                        new GoToPlaceCard("Go to Jail. Go directly to Jail. Do not pass Go, Do not collect $200.",
                                GO_TO_JAIL, -1, true),

                        // Grand Opera Night. Collect $50 from every player.
                        new PayEachPlayerCard("Grand Opera Night. Collect $50 from every player.",
                                GRAND_OPERA_NIGHT_COLLECT_50_FROM_EACH_PLAYER, 50),

                        // Holiday Fund matures. Collect $100.
                        new MoneyTransactionCard("Holiday Fund matures. Collect $100.",
                                HOLIDAY_FUND_MATURES_COLLECT_100, 100, false),

                        // Income tax refund. Collect $20.
                        new MoneyTransactionCard("Income tax refund. Collect $20.",
                                INCOME_TAX_REFUND_COLLECT_20, 20, false),

                        // It's your birthday. Collect $10 from every player.
                        new PayEachPlayerCard("It is your birthday. Collect $10 from every player.",
                                ITS_YOUR_BIRTHDAY_COLLECT_10_FROM_EACH_PLAYER, 10),

                        // Life insurance matures. Collect $100.
                        new MoneyTransactionCard("Life insurance matures. Collect $100.",
                                LIFE_INSURANCE_MATURES_COLLECT_100, 100, false),

                        // Hospital Fees. Pay $50.
                        new MoneyTransactionCard("Hospital Fees. Pay $50.",
                                HOSPITAL_FEES_PAY_50, 50, true),

                        // School fees. Pay $50.
                        new MoneyTransactionCard("School fees. Pay $50.",
                                SCHOOL_FEES_PAY_50, 50, true),

                        // Receive $25 consultancy fee.
                        new MoneyTransactionCard("Receive $25 consultancy fee.",
                                CONSULTANCY_FEE_COLLECT_25, 25, false),

                        // Street repairs: Pay $40 per house, $115 per hotel
                        new PaysPerHouseAndHotelCard(
                                "You are assessed for street repairs: Pay $40 per house and $115 per hotel you own.",
                                STREET_REPAIRS_PAY_40_PER_HOUSE_115_PER_HOTEL,
                                40,
                                115
                        ),

                        // Beauty contest second prize. Collect $10.
                        new MoneyTransactionCard("You have won second prize in a beauty contest. Collect $10.",
                                BEAUTY_CONTEST_SECOND_PRIZE_COLLECT_10, 10, false),

                        // You inherit $100.
                        new MoneyTransactionCard("You inherit $100.",
                                INHERIT_100, 100, false)
                )
                .collect(Collectors.toMap(Card::getCardType, Function.identity()));
    }

    public static List<Card> makeDeck() {
        var deck = new ArrayList<>(cards.values());

        // In Community Chest: Get Out of Jail Free is also a normal card in the deck.
        // NOTE: jouw GetOutOfJailCard constructor zet CardType.GET_OUT_OF_JAIL_FREE
        // Als je écht een aparte type wil (GET_OUT_OF_JAIL_FREE_CHEST), moet je die class aanpassen.
        // (zie comment hieronder)

        Collections.shuffle(deck);
        return deck;
    }

    public static Card getCard(CardType cardType) {
        return cards.get(cardType);
    }
}