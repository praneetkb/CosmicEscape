package ca.sfu.cmpt276.fall2025.team14.integration;

import ca.sfu.cmpt276.fall2025.team14.IntegrationTestBase;
import ca.sfu.cmpt276.fall2025.team14.model.Button;
import ca.sfu.cmpt276.fall2025.team14.model.Door;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DoorButtonIntegrationTest extends IntegrationTestBase {

    // door button and door interaction

    @Test
    public void testDoorButtonOpensDoor() {

        Door door = new Door();
        Button button = new Button("door-btn");

        assertFalse(door.isOpen()); // door should start closed
        assertFalse(button.pressed()); // button should start unpressed

        button.pressButton();

        // pressing button should open the door
        if (button.pressed()) {
            door.open();
        }

        assertTrue(door.isOpen()); // door should now be open

        // release button and close the door
        button.releaseButton();
        if (!button.pressed()) {
            door.close();
        }

        assertFalse(door.isOpen()); // door should close when button is released
    }
}
