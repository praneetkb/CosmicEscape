package ca.sfu.cmpt276.fall2025.team14.unit;

import ca.sfu.cmpt276.fall2025.team14.IntegrationTestBase;
import ca.sfu.cmpt276.fall2025.team14.app.GameLogic;
import ca.sfu.cmpt276.fall2025.team14.model.LaserButton;
import ca.sfu.cmpt276.fall2025.team14.model.Lasers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LaserButtonTest extends IntegrationTestBase {

    @Test
    void testLaserButtonPresses() {
        LaserButton b = new LaserButton();
        assertFalse(b.isPressed());

        b.pressButton();
        assertTrue(b.isPressed());
    }

    @Test
    void testLaserButtonUpdateCallsSuper() {
        LaserButton b = new LaserButton();
        b.pressButton();
        b.update();

        assertTrue(b.isPressed()); // unchanged
    }

}
