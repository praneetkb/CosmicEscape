package ca.sfu.cmpt276.fall2025.team14.integration;

import ca.sfu.cmpt276.fall2025.team14.IntegrationTestBase;
import ca.sfu.cmpt276.fall2025.team14.app.GameLogic;
import ca.sfu.cmpt276.fall2025.team14.model.Alien;
import ca.sfu.cmpt276.fall2025.team14.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// FIX: Extend IntegrationTestBase to ensure the engine initializes correctly
public class GameLogicIntegrationTest extends IntegrationTestBase {

    // testing restart level when time is over
    @Test
    public void testRestartLevelOnTimeOver() {

        // set game state to INGAME
        GameLogic.setState(GameLogic.GameState.INGAME);

        // set timer to 0
        GameLogic.setRemainingTime(0);

        int initialLevel = GameLogic.getCurrentLevelIndex();

        // Trigger update
        GameLogic.testUpdate();

        // level should restart - current level should remain the same and should be reloaded
        assertEquals(initialLevel, GameLogic.getCurrentLevelIndex());
    }

    /*
    testing restart level when enemy collision
    @Test
    public void testRestartLevelOnEnemyCollision() {

        Alien alien = new Alien();
        alien.setLocation(200, 200);

        Player player = Player.getInstance();
        player.setLocation(200, 200); // same position as enemy

        int initialLevel = GameLogic.getCurrentLevelIndex();
        GameLogic.testHandleCollisions();

        // collision should trigger level restart
        assertEquals(initialLevel, GameLogic.getCurrentLevelIndex());
    }
     */
}
