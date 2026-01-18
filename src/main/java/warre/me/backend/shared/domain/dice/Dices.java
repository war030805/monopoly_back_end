package warre.me.backend.shared.domain.dice;

public record Dices(
        int dice1, int dice2
) {

    public static Dices trowDices() {
        var dice1= Dice.trowDice();
        var dice2= Dice.trowDice();

        return new Dices(dice1, dice2);
    }

    public int sum() {
        return dice1 + dice2;
    }
}
