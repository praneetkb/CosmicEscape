package ca.sfu.cmpt276.fall2025.team14.unit;

import ca.sfu.cmpt276.fall2025.team14.app.GameLogic;
import ca.sfu.cmpt276.fall2025.team14.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class GameLogicTest {

    @Test
    public void testCollisionHelper() {

        // button
        Button button = new LaserButton();
        GameLogic.testCollisionHelper(button); // first press
        GameLogic.testCollisionHelper(button); // already pressed branch

        // crystal
        Crystal crystal = new Crystal();
        GameLogic.testCollisionHelper(crystal);

        // powerup
        Powerup jetpack = new Jetpack();
        GameLogic.testCollisionHelper(jetpack);

        // powerup with null type
        Powerup unknownPowerup = new Powerup("unknown") {
            @Override
            public PowerUpType getType() { return null; }
        };
        GameLogic.testCollisionHelper(unknownPowerup); // should safely do nothing

        // punishment
        Punishment slime = new Slime();
        GameLogic.testCollisionHelper(slime);

        // teleporter
        Teleporter t = new Teleporter();
        t.tryOpen(1, 1); // still has remaining crystals, should not open
        GameLogic.testCollisionHelper(t);

        // teleporter open
        Teleporter t2 = new Teleporter();
        t2.tryOpen(0, 1); // conditions met, should open
        GameLogic.testCollisionHelper(t2);
    }

    @Test
    public void testCollisionHelperDoesNotThrow() {

        assertDoesNotThrow(() -> {
            GameLogic.testCollisionHelper(new LaserButton());
            GameLogic.testCollisionHelper(new Crystal());
            GameLogic.testCollisionHelper(new Jetpack());
            GameLogic.testCollisionHelper(new Slime());
            GameLogic.testCollisionHelper(new Teleporter());
        });
    }
}
