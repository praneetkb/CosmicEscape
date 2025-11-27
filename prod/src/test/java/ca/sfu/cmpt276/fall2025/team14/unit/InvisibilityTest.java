package ca.sfu.cmpt276.fall2025.team14.unit;

import ca.sfu.cmpt276.fall2025.team14.IntegrationTestBase;
import ca.sfu.cmpt276.fall2025.team14.app.GameLogic;
import ca.sfu.cmpt276.fall2025.team14.model.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InvisibilityTest extends IntegrationTestBase {

    // test invisibility powerup on player
    @Test
    public void testPlayerBecomesInvisible() {
        Player player = Player.getInstance();
        player.resetPowerUps(); // make sure player starts visible

        assertFalse(player.isInvisible());

        // simulate effect
        player.setInvisible(true);
        assertTrue(player.isInvisible()); // player should be invisible after collecting the powerup

        // reset
        player.resetPowerUps();
        assertFalse(player.isInvisible()); // player should be visible after reset
    }
}
