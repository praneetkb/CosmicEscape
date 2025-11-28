package ca.sfu.cmpt276.fall2025.team14.unit;

import ca.sfu.cmpt276.fall2025.team14.IntegrationTestBase;
import ca.sfu.cmpt276.fall2025.team14.app.GameLogic;
import ca.sfu.cmpt276.fall2025.team14.model.Lasers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LasersTest extends IntegrationTestBase {

    // laser intial state
    @Test
    public void testLaserInitialState() {
        Lasers laser = new Lasers();
        assertNotNull(laser); // should be on

        // update - to attach button
        laser.update();
        assertNotNull(laser.getButton());
    }

    // laser collision - should restart level
    @Test
    public void testLaserCollision() {
        Lasers laser = new Lasers();

        GameLogic.TestApplyPunishment(laser);

        // check that the game state has reset to INGAME (after restart)
        assertEquals(GameLogic.GameState.INGAME, GameLogic.getState());
    }
}
