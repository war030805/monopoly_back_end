package warre.me.backend.shared.domain.cards.cardTypes;

import warre.me.backend.shared.domain.NotFoundThrower;

public enum CardSpecificType implements NotFoundThrower {
    ADVANCE_TO_GO_COLLECT_200,
    ADVANCE_TO_ILLINOIS_AVENUE,
    ADVANCE_TO_ST_CHARLES_PLACE,
    ADVANCE_TO_NEAREST_UTILITY,
    ADVANCE_TO_NEAREST_RAILROAD,
    BANK_PAYS_DIVIDEND_50,
    GET_OUT_OF_JAIL_FREE,
    GO_BACK_THREE_SPACES,
    GO_TO_JAIL,
    GENERAL_REPAIRS_PAY_PER_HOUSE_25_PER_HOTEL_100,
    TRIP_TO_READING_RAILROAD,
    ADVANCE_TO_BOARDWALK,
    ELECTED_CHAIRMAN_PAY_EACH_PLAYER_50,
    BUILDING_AND_LOAN_MATURES_COLLECT_150,


    // Community Chest
    BANK_ERROR_IN_YOUR_FAVOR_COLLECT_200,
    DOCTORS_FEES_PAY_50,
    SALE_OF_STOCK_COLLECT_50,
    GRAND_OPERA_NIGHT_COLLECT_50_FROM_EACH_PLAYER,
    HOLIDAY_FUND_MATURES_COLLECT_100,
    INCOME_TAX_REFUND_COLLECT_20,
    ITS_YOUR_BIRTHDAY_COLLECT_10_FROM_EACH_PLAYER,
    LIFE_INSURANCE_MATURES_COLLECT_100,
    HOSPITAL_FEES_PAY_50,
    SCHOOL_FEES_PAY_50,
    CONSULTANCY_FEE_COLLECT_25,
    STREET_REPAIRS_PAY_40_PER_HOUSE_115_PER_HOTEL,
    BEAUTY_CONTEST_SECOND_PRIZE_COLLECT_10,
    INHERIT_100;

    @Override
    public String notFoundMessage() {
        return "could not find cardSpecificType of name" + this.name();
    }


    @Override
    public String toString() {
        return name().toLowerCase().replace("_", " ");
    }
}

