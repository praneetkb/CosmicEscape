package ca.sfu.cmpt276.fall2025.team14.integration;

import ca.sfu.cmpt276.fall2025.team14.IntegrationTestBase;
import ca.sfu.cmpt276.fall2025.team14.app.GameLogic;
import ca.sfu.cmpt276.fall2025.team14.model.Crystal;
import ca.sfu.cmpt276.fall2025.team14.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CrystalIntegrationTest extends IntegrationTestBase {

    @BeforeEach
    public void resetState() {
        Player player = Player.getInstance();
        player.setLocation(0, 0);
        player.resetPowerUps();
    }

    // crystal and game logic (crystal collection)
    @Test
    public void testCrystalCollectionUpdatesGameLogic() {

        GameLogic.setRemainingCrystals(3); // default

        Crystal crystal = new Crystal();
        assertFalse(crystal.isCollected()); // should start as uncollected

        crystal.collect();
        GameLogic.decrementCrystalCount(); // update game logic

        assertTrue(crystal.isCollected());

        assertEquals(2, GameLogic.getRemainingCrystals()); // remaining crystal should decrease by 1
    }
}
