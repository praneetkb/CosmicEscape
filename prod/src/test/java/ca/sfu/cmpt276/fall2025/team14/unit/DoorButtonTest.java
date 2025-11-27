package ca.sfu.cmpt276.fall2025.team14.unit;

import ca.sfu.cmpt276.fall2025.team14.IntegrationTestBase;
import ca.sfu.cmpt276.fall2025.team14.app.GameLogic;
import ca.sfu.cmpt276.fall2025.team14.model.Button;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DoorButtonTest extends IntegrationTestBase {

    // door button state - initial and toggle (pressed and unpressed)
    @Test
    public void testButtonState() {
        Button b = new Button("dummy");
        assertFalse(b.pressed()); // should start unpressed
        b.pressButton();
        assertTrue(b.pressed()); // should be pressed
        b.releaseButton();
        assertFalse(b.pressed()); // should return to unpressed
    }

    // door button collision change
    @Test
    public void testButtonCollision() {
        Button b = new Button("dummy");
        b.pressButton();
        b.update();
        assertFalse(b.hasCollision()); // when pressed, button collision should turn off
    }
}
