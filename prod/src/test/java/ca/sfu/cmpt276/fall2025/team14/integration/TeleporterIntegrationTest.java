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

    @Test
    public void testPlayerTeleporterInteraction() {
        Teleporter teleporter = new Teleporter();
        Player player = Player.getInstance();

        // open teleporter
        teleporter.tryOpen(0, 3);
        assertTrue(teleporter.isOpen());

        // move player onto teleporter
        player.setLocation(teleporter.getX(), teleporter.getY());
        teleporter.update(); 

        // reset level to first
        GameLogic.setState(GameLogic.GameState.INGAME);
        
        // Ensure we are on a valid level index before starting
        GameLogic.setCurrentLevelIndex(0); 
        int initialLevel = GameLogic.getCurrentLevelIndex();

        // reset player position
        player.setLocation(0, 0);

        // place player at teleporter location
        player.setLocation(teleporter.getX(), teleporter.getY());

        // Trigger collision. 
        // NOTE: In GameLogic, this calls Game.loop().perform(1, ...)
        GameLogic.testHandleCollisions();

        // FIX: Wait for the Game Loop to process the scheduled task
        // The level change happens in the next engine tick, not immediately.
        try {
            Thread.sleep(200); // Wait 200ms for the loop to catch up
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Now check if the level increased
        assertEquals(initialLevel + 1, GameLogic.getCurrentLevelIndex()); 
    }
}