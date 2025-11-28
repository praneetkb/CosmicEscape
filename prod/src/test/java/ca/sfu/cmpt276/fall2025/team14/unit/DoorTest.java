package ca.sfu.cmpt276.fall2025.team14.unit;

import ca.sfu.cmpt276.fall2025.team14.IntegrationTestBase;
import ca.sfu.cmpt276.fall2025.team14.app.GameLogic;
import ca.sfu.cmpt276.fall2025.team14.model.Door;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DoorTest extends IntegrationTestBase {

    // door initial state - should be closed
    @Test
    public void testDoorInitialState() {
        Door d = new Door();
        assertFalse(d.isOpen());
    }

    // door collision toggle (open and close)
    @Test
    public void testDoorToggle() {
        Door d = new Door();
        d.open();
        assertTrue(d.isOpen()); // should be opened after calling open()
        d.close();
        assertFalse(d.isOpen()); // should be closed
    }
}
