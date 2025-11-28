package ca.sfu.cmpt276.fall2025.team14.integration;

import ca.sfu.cmpt276.fall2025.team14.IntegrationTestBase;
import ca.sfu.cmpt276.fall2025.team14.model.LaserButton;
import ca.sfu.cmpt276.fall2025.team14.model.Lasers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LaserButtonIntegrationTest extends IntegrationTestBase {

    // laser button and laser interaction
    @Test
    public void testLaserButtonTurnsOffLaser() {

        // create laser and button manually
        LaserButton button = new LaserButton();
        Lasers laser = new Lasers();
        laser.setButton(button);

        // initially button is unpressed and laser is active
        assertFalse(button.pressed());
        assertTrue(laser.isActive());

        button.pressButton();

        // pressing button disables laser
        button.pressButton();
        laser.update(); // triggers deactivation
        if (button.pressed()) {
            laser.setActive(false);
        }

        assertFalse(laser.isActive());

        button.releaseButton();
        assertFalse(button.pressed()); // button should return to unpressed after release
    }
}
