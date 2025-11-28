package ca.sfu.cmpt276.fall2025.team14.unit;

import ca.sfu.cmpt276.fall2025.team14.model.Door;
import ca.sfu.cmpt276.fall2025.team14.model.DoorAnimationController;
import ca.sfu.cmpt276.fall2025.team14.model.Button;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class DoorAnimationControllerTest {

    @Test
    void testGetCurrentImageWithButtonStates() {
        Door d = new Door();
        DoorAnimationController controller = new DoorAnimationController(d);

        // no button linked
        assertDoesNotThrow(() -> controller.getCurrentImage());

        // link button unpressed
        Button b = new Button("button");
        d.setButton(b);
        b.releaseButton();
        assertDoesNotThrow(() -> controller.getCurrentImage());

        // button pressed
        b.pressButton();
        assertDoesNotThrow(() -> controller.getCurrentImage());
    }
}
