package ca.sfu.cmpt276.fall2025.team14.integration;

import ca.sfu.cmpt276.fall2025.team14.IntegrationTestBase;
import ca.sfu.cmpt276.fall2025.team14.app.GameLogic;
import ca.sfu.cmpt276.fall2025.team14.model.Alien;
import ca.sfu.cmpt276.fall2025.team14.model.Invisibility;
import ca.sfu.cmpt276.fall2025.team14.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InvisibilityIntegrationTest extends IntegrationTestBase {

    @BeforeEach
    public void resetState() {
        Player player = Player.getInstance();
        player.setLocation(0, 0);
        player.resetPowerUps();
    }

    // invisibility powerup with player and alien
    @Test
    public void testInvisibility() {
        Player player = Player.getInstance();

        // create a mock Alien that respects invisibility
        Alien alien = new Alien() {
            @Override
            public boolean playerInLos() {
                // mock LOS: returns false if player is invisible
                return !Player.getInstance().isInvisible();
            }
        };

        // player is within alien's LOS initially
        alien.setLocation(0, 0);
        player.setLocation(5, 0);

        player.setInvisible(false); // initially visible
        assertTrue(alien.playerInLos());

        // apply invisibility powerup
        Invisibility invis = new Invisibility();
        GameLogic.TestApplyPowerup(invis);
        assertTrue(player.isInvisible());

        // check alien detection with mocked LOS
        assertFalse(alien.playerInLos());

        // reset player
        player.resetPowerUps();
        assertFalse(player.isInvisible());
    }

}
