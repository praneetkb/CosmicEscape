package ca.sfu.cmpt276.fall2025.team14.unit;

import ca.sfu.cmpt276.fall2025.team14.model.Button;
import ca.sfu.cmpt276.fall2025.team14.model.Door;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ButtonTest {

    // button initial state
    @Test
    void testInitialPressedState() {
        Button btn = new Button("button");
        assertFalse(btn.pressed());
    }

    // button pressed
    @Test
    void testPressButton() {
        Button btn = new Button("button");
        btn.pressButton();
        assertTrue(btn.pressed());
    }

    // button collision
    @Test
    void testUpdateTurnsOffCollisionWhenPressed() {
        Button btn = new Button("button");

        // press, then update
        btn.pressButton();
        btn.update();

        assertFalse(btn.hasCollision()); // when pressed, button collision should turn off
    }
}
