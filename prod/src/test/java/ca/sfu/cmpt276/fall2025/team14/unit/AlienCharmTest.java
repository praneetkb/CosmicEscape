package ca.sfu.cmpt276.fall2025.team14.unit;

import ca.sfu.cmpt276.fall2025.team14.app.GameLogic;
import ca.sfu.cmpt276.fall2025.team14.model.AlienCharm;
import ca.sfu.cmpt276.fall2025.team14.model.Player;
import ca.sfu.cmpt276.fall2025.team14.IntegrationTestBase;
import ca.sfu.cmpt276.fall2025.team14.model.PowerUpType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AlienCharmTest extends IntegrationTestBase {

    @BeforeEach
    public void resetPlayer() {
        Player player = Player.getInstance();
        player.setHasAlienCharm(false);
        player.resetPowerUps();
    }

    // type check
    @Test
    public void testGetType() {
        AlienCharm charm = new AlienCharm();
        assertTrue(charm.getType() == PowerUpType.ALIEN_CHARM);
    }

    // test alien charm powerup
    @Test
    public void testAlienCharm() {
        Player player = Player.getInstance();

        // ensure player does not have alien charm initially
        player.setHasAlienCharm(false);
        assertFalse(player.hasAlienCharm());

        AlienCharm charm = new AlienCharm();
        player.setHasAlienCharm(true);

        assertTrue(player.hasAlienCharm());

        player.setHasAlienCharm(false);
        assertFalse(player.hasAlienCharm());
    }
}
