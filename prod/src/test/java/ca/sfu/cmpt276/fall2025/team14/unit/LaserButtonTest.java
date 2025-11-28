package ca.sfu.cmpt276.fall2025.team14.unit;

import ca.sfu.cmpt276.fall2025.team14.IntegrationTestBase;
import ca.sfu.cmpt276.fall2025.team14.app.GameLogic;
import ca.sfu.cmpt276.fall2025.team14.model.Button;
import ca.sfu.cmpt276.fall2025.team14.model.Lasers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LaserButtonTest extends IntegrationTestBase {

    // laser button initial state - should start unpressed
    @Test
    public void testLaserButtonInitialState() {
        Lasers laser = new Lasers();
        laser.update(); // attach button
        Button button = laser.getButton();

        assertFalse(button.pressed());
    }

    // laser button toggle (pressed and unpressed)
    @Test
    public void testLaserButtonToggle() {
        Lasers laser = new Lasers();
        laser.update(); // attach button
        Button button = laser.getButton();

        // press button
        button.pressButton();
        assertTrue(button.pressed());

        // release button
        button.releaseButton();
        assertFalse(button.pressed());
    }
}
