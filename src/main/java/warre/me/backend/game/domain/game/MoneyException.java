package warre.me.backend.game.domain.game;

public class MoneyException extends GameException {
    public MoneyException(String message) {
        super(message);
    }

    public MoneyException() {
        super("user does not have enough money");
    }
}
