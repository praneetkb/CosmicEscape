package ca.sfu.cmpt276.fall2025.team14.unit;

import ca.sfu.cmpt276.fall2025.team14.IntegrationTestBase;
import ca.sfu.cmpt276.fall2025.team14.app.GameLogic;
import ca.sfu.cmpt276.fall2025.team14.model.Button;
import ca.sfu.cmpt276.fall2025.team14.model.DoorButton;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DoorButtonTest extends IntegrationTestBase {

    // door button initial state
    @Test
    public void testInitialState() {
        DoorButton button = new DoorButton();
        assertFalse(button.isPressed());  // default should be unpressed
        assertTrue(button.hasCollision()); // default collision enabled
    }

    // door button pressed
    @Test
    public void testPressButton() {
        DoorButton button = new DoorButton();
        button.pressButton();

        assertTrue(button.isPressed());     // should now be pressed
        button.update();                    // runs super.update()
        assertFalse(button.hasCollision()); // button should disable collision when pressed
    }

    // door button unpressed
    @Test
    public void testReleaseButton() {
        DoorButton button = new DoorButton();

        button.pressButton();
        assertTrue(button.isPressed());

        button.releaseButton();             // inherited from Button
        assertFalse(button.isPressed());    // should be unpressed again
    }
}
