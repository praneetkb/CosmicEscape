package ca.sfu.cmpt276.fall2025.team14.unit;

import ca.sfu.cmpt276.fall2025.team14.app.GameLogic;
import org.junit.jupiter.api.BeforeEach;
import ca.sfu.cmpt276.fall2025.team14.model.Crystal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CrystalTest {

    @BeforeEach
    public void resetState() {
        GameLogic.setRemainingCrystals(3);
    }

    // initial state
    @Test
    public void testInitialState() {
        Crystal c = new Crystal();
        assertFalse(c.isCollected());  // default state
        c.setVisible(true);
        assertTrue(c.isVisible());
    }

    // test one crystal collection
    @Test
    public void testCollectCrystal() {
        Crystal c = new Crystal();

        c.collect();

        assertTrue(c.isCollected());     // state updated
        assertFalse(c.hasCollision());   // collision off
        assertFalse(c.isVisible());      // hidden after collecting
    }

    // test crystal collection updates game logic
    @Test
    public void testCrystalCollectionUpdatesGameLogic() {

        GameLogic.setRemainingCrystals(3); // default

        // simulate collecting a crystal
        GameLogic.decrementCrystalCount();

        assertEquals(2, GameLogic.getRemainingCrystals()); // remaining crystal should decrease by 1
    }

    // test collecting multiple crystals
    @Test
    public void testMultipleCrystalCollection() {
        assertEquals(3, GameLogic.getRemainingCrystals());

        // collect 2 crystals
        GameLogic.decrementCrystalCount();
        GameLogic.decrementCrystalCount();

        assertEquals(1, GameLogic.getRemainingCrystals());
    }

    // test collecting all crystals
    @Test
    public void testAllCrystalsCollected() {
        GameLogic.setRemainingCrystals(3);

        // collect all 3
        GameLogic.decrementCrystalCount();
        GameLogic.decrementCrystalCount();
        GameLogic.decrementCrystalCount();

        // should have 0 remaining (ready for teleporter)
        assertEquals(0, GameLogic.getRemainingCrystals());
    }

    @Test
    public void testCrystalUpdateDoesNotCrash() {
        Crystal c = new Crystal();

        // update does nothing but we test execution for coverage
        c.update();
    }
}
