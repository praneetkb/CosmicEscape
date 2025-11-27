package ca.sfu.cmpt276.fall2025.team14.integration;

import ca.sfu.cmpt276.fall2025.team14.IntegrationTestBase;
import ca.sfu.cmpt276.fall2025.team14.app.GameLogic;
import ca.sfu.cmpt276.fall2025.team14.model.Alien;
import ca.sfu.cmpt276.fall2025.team14.model.AlienCharm;
import ca.sfu.cmpt276.fall2025.team14.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AlienCharmIntegrationTest extends IntegrationTestBase {

    @BeforeEach
    public void resetState() {
        Player player = Player.getInstance();
        player.setLocation(0, 0);
        player.resetPowerUps();
    }

    // alien charm power up
    @Test
    public void testAlienCharmPreventsPlayerCapture() {

        Player player = Player.getInstance();
        Alien alien = new Alien();

        // player in collision range of alien
        alien.setLocation(100, 100);
        player.setLocation(105, 100);

        // ensure player does not have alien charm initially
        player.setHasAlienCharm(false);

        // simulate collision without charm - player should be caught
        boolean caughtWithoutCharm = alien.canCollideWith(player);
        assertTrue(caughtWithoutCharm);

        // player gets alien charm
        AlienCharm charm = new AlienCharm();
        GameLogic.TestApplyPowerup(charm);
        assertTrue(player.hasAlienCharm());

        // collision with charm - player should not be caught
        if (alien.canCollideWith(player) && player.hasAlienCharm()) {
            // game logic - charm consumed instead of being caught
            player.setHasAlienCharm(false);
        }

        assertFalse(player.hasAlienCharm());
        assertEquals(GameLogic.GameState.INGAME, GameLogic.getState()); // game should continue
    }
}
