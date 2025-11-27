package ca.sfu.cmpt276.fall2025.team14.unit;

import ca.sfu.cmpt276.fall2025.team14.IntegrationTestBase;
import ca.sfu.cmpt276.fall2025.team14.model.Crystal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CrystalTest extends IntegrationTestBase {

    // test crystal initial state and collection
    @Test
    public void testCrystalCollection() {
        Crystal c = new Crystal();
        assertFalse(c.isCollected()); // should start as not collected

        c.collect();
        assertTrue(c.isCollected()); // should be collected
    }
}
