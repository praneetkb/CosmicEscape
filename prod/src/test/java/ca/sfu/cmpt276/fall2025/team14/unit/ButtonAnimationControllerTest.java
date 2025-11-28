package ca.sfu.cmpt276.fall2025.team14.unit;

import ca.sfu.cmpt276.fall2025.team14.model.Button;
import ca.sfu.cmpt276.fall2025.team14.model.ButtonAnimationController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ButtonAnimationControllerTest {

    @Test
    void testGetCurrentImagePressedUnpressed() {

        Button b = new Button("button");
        ButtonAnimationController controller = new ButtonAnimationController(b);

        // test unpressed
        b.releaseButton();
        assertDoesNotThrow(() -> controller.getCurrentImage());

        // test pressed
        b.pressButton();
        assertDoesNotThrow(() -> controller.getCurrentImage());
    }
}
