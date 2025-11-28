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
        Alien alien = new Alien();

        // player is within alien's LOS
        alien.setLocation(0, 0);
        player.setLocation(5, 0);

        // initially player is visible so detectable by alien
        player.setInvisible(false);
        alien.update();
        assertTrue(alien.playerInLos());

        // player should be invisible now
        Invisibility invis = new Invisibility();
        GameLogic.TestApplyPowerup(invis);
        assertTrue(player.isInvisible());

        // alien should not be able to detect player now
        alien.update();
        assertFalse(alien.playerInLos()); // TEST NOT WORKING - GAME LOGIC ERROR!

        // reset player visibility
        player.resetPowerUps();
        assertFalse(player.isInvisible());
    }
}
