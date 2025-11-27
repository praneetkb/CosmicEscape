package ca.sfu.cmpt276.fall2025.team14.unit;

import ca.sfu.cmpt276.fall2025.team14.IntegrationTestBase;
import ca.sfu.cmpt276.fall2025.team14.app.GameLogic;
import ca.sfu.cmpt276.fall2025.team14.model.Player;
import ca.sfu.cmpt276.fall2025.team14.model.Timestop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TimestopTest extends IntegrationTestBase {

    @BeforeEach
    public void resetPlayer() {
        Player player = Player.getInstance();
        player.resetPowerUps();
    }

    // test timestop power up
    @Test
    public void testTimestop() {
        // timestop should not be active initially
        assertFalse(GameLogic.isTimeStopped());

        Timestop timestop = new Timestop();
        GameLogic.TestApplyPowerup(timestop);

        // should be active
        assertTrue(GameLogic.isTimeStopped());

        // deactivate
        GameLogic.setTimeStopped(false);
        assertFalse(GameLogic.isTimeStopped());
    }
}
