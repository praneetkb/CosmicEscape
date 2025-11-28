package ca.sfu.cmpt276.fall2025.team14.unit;

import ca.sfu.cmpt276.fall2025.team14.app.GameLogic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CrystalTest {

    @BeforeEach
    public void resetState() {
        GameLogic.setRemainingCrystals(3);
    }

    // test crystal collection updates game logic
    @Test
    public void testCrystalCollectionUpdatesGameLogic() {

        GameLogic.setRemainingCrystals(3); // default

        // simulate collecting a crystal
        GameLogic.decrementCrystalCount();

        assertEquals(2, GameLogic.getRemainingCrystals()); // remaining crystal should decrease by 1
    }

    // Test collecting multiple crystals
    @Test
    public void testMultipleCrystalCollection() {
        assertEquals(3, GameLogic.getRemainingCrystals());

        // Collect 2 crystals
        GameLogic.decrementCrystalCount();
        GameLogic.decrementCrystalCount();

        assertEquals(1, GameLogic.getRemainingCrystals());
    }

    // Test collecting all crystals
    @Test
    public void testAllCrystalsCollected() {
        GameLogic.setRemainingCrystals(3);

        // Collect all 3
        GameLogic.decrementCrystalCount();
        GameLogic.decrementCrystalCount();
        GameLogic.decrementCrystalCount();

        // Should have 0 remaining (ready for teleporter)
        assertEquals(0, GameLogic.getRemainingCrystals());
    }
}
