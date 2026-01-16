package warre.me.backend.game.domain.gamePlayer;

//dit gaat beslissen wat ze zien op het bord


public enum Action {
    /**
     * als je aan het wachten bent op een andere speler
     */
    WAITING,
    TROWING_DICES,
    MOVED,
    BUY_PROPERTY,
    PAYING_TAXES,
    PULLED_CARD,
    USED_CARD,
    SAVED_CARD,
    PAY_RENT, USED_SAVED_CARD
}
