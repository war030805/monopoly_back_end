package warre.me.backend.chared.domain.dice;

import java.util.Random;

public class Dice {
    private static final Random RANDOM= new Random();

    public static int trowDice() {
        return RANDOM.nextInt(1,7);
    }
}
