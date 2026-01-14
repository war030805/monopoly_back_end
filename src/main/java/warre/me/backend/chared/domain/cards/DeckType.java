package warre.me.backend.chared.domain.cards;

public enum DeckType {
    CHANCE, COMMUNITY_CHEST;


    @Override
    public String toString() {
        return name().toLowerCase().replace("_", " ");
    }
}
