package ca.sfu.cmpt276.fall2025.team14.unit;

import ca.sfu.cmpt276.fall2025.team14.IntegrationTestBase;
import ca.sfu.cmpt276.fall2025.team14.app.GameLogic;
import ca.sfu.cmpt276.fall2025.team14.model.Player;
import ca.sfu.cmpt276.fall2025.team14.model.Teleporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TeleporterTest extends IntegrationTestBase {

    @BeforeEach
    public void resetPlayer() {
        Player player = Player.getInstance();
        player.resetPowerUps();
    }

    // teleporter - initial state and collision
    @Test
    public void testTeleporterState() {
        Teleporter t = new Teleporter();
        assertFalse(t.isOpen()); // should be closed at the start
        assertTrue(t.hasCollision()); //  should have collision when closed
    }

    // teleporter condition 1 to open - check crystals
    @Test
    public void testTeleporterCrystalsRemaining() {
        Teleporter t = new Teleporter();

        t.tryOpen(3, 1); // any value > 0 would work

        assertFalse(t.isOpen()); // must stay closed if crystals still remain
        assertTrue(t.hasCollision()); // collision must remain on when closed
    }

    // teleporter condition 2 to open - within time limit
    @Test
    public void testTeleporterTimeOver() {
        Teleporter t = new Teleporter();

        t.tryOpen(0, 0); // no time left

        assertFalse(t.isOpen()); // must stay closed if time is over
        assertTrue(t.hasCollision()); // collision must remain on when closed
    }

    // teleporter - both conditions met
    @Test
    public void testTeleporterConditionsMet() {
        Teleporter t = new Teleporter();

        t.tryOpen(0, 1); // any value > 0 would work

        assertTrue(t.isOpen()); // should open when all crystals are collected and time remains
        assertFalse(t.hasCollision()); // collision must be off when teleporter opens
    }
}
