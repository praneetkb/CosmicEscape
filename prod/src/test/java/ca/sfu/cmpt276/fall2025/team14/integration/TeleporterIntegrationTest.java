package ca.sfu.cmpt276.fall2025.team14.integration;

import ca.sfu.cmpt276.fall2025.team14.IntegrationTestBase;
import ca.sfu.cmpt276.fall2025.team14.app.GameLogic;
import ca.sfu.cmpt276.fall2025.team14.model.Player;
import ca.sfu.cmpt276.fall2025.team14.model.Teleporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TeleporterIntegrationTest extends IntegrationTestBase {

    @BeforeEach
    public void resetState() {
        Player player = Player.getInstance();
        player.setLocation(0, 0);
        player.resetPowerUps();
    }

    // teleporter should allow player to go to next level
    @Test
    public void testPlayerTeleporterInteraction() {
        Teleporter teleporter = new Teleporter();
        Player player = Player.getInstance();

        // open teleporter
        teleporter.tryOpen(0, 3);
        assertTrue(teleporter.isOpen());

        // move player onto teleporter
        player.setLocation(teleporter.getX(), teleporter.getY());
        teleporter.update(); // ensure teleporter checks collisions

        // reset level to first
        GameLogic.setState(GameLogic.GameState.INGAME);
        int initialLevel = GameLogic.getCurrentLevelIndex();

        // reset player position
        player.setLocation(0, 0);

        // place player at teleporter location
        player.setLocation(teleporter.getX(), teleporter.getY());

        // trigger collision
        GameLogic.testHandleCollisions();

        // level should increase
        assertEquals(initialLevel + 1, GameLogic.getCurrentLevelIndex()); // TEST NOT WORKING - GAME LOGIC ERROR!
    }
}
