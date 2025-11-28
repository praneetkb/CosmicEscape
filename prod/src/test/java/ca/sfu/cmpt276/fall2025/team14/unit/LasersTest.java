package ca.sfu.cmpt276.fall2025.team14.unit;

import ca.sfu.cmpt276.fall2025.team14.IntegrationTestBase;
import ca.sfu.cmpt276.fall2025.team14.app.GameLogic;
import ca.sfu.cmpt276.fall2025.team14.model.Button;
import ca.sfu.cmpt276.fall2025.team14.model.LaserButton;
import ca.sfu.cmpt276.fall2025.team14.model.Lasers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LasersTest extends IntegrationTestBase {

    // laser intial state
    @Test
    public void testLaserInitialState() {

        // create a button instance to attach to laser
        Button button = new LaserButton();

        // create the laser and assign the button
        Lasers laser = new Lasers();
        laser.setButton(button); // ensure button is not null

        assertNotNull(laser.getButton());
    }

    // laser collision - should restart level
    @Test
    public void testLaserCollision() {
        Lasers laser = new Lasers();

        // apply punishment
        GameLogic.TestApplyPunishment(laser);

        GameLogic.testRestartLevel();
    }
}
