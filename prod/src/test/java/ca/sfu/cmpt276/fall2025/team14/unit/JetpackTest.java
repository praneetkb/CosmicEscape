package ca.sfu.cmpt276.fall2025.team14.unit;

import ca.sfu.cmpt276.fall2025.team14.IntegrationTestBase;
import ca.sfu.cmpt276.fall2025.team14.app.GameLogic;
import ca.sfu.cmpt276.fall2025.team14.model.Jetpack;
import ca.sfu.cmpt276.fall2025.team14.model.PowerUpType;
import ca.sfu.cmpt276.fall2025.team14.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JetpackTest extends IntegrationTestBase {

    @BeforeEach
    public void resetPlayer() {
        Player player = Player.getInstance();
        player.resetPowerUps();
    }

    // type test
    @Test
    public void testJetpackGetType() {
        Jetpack jetpack = new Jetpack();
        assertEquals(PowerUpType.JETPACK, jetpack.getType());
    }

    // test jetpack powerup on player
    @Test
    public void testJetpack() {
        Player player = Player.getInstance();
        player.resetPowerUps(); // ensure default speed of player
        float defaultSpeed = Player.getPlayerDefaultVelocity();

        assertEquals(defaultSpeed, player.getVelocity().get());

        // simulate effect
        float boostedSpeed = defaultSpeed * 1.5f; // same as GameLogic
        player.getVelocity().setBaseValue(boostedSpeed);
        assertEquals(boostedSpeed, player.getVelocity().get()); // player speed should increase

        // Reset
        player.resetPowerUps();
        assertEquals(defaultSpeed, player.getVelocity().get()); // default after reset
    }
}
