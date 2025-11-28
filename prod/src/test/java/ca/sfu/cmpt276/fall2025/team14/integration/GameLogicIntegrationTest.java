package ca.sfu.cmpt276.fall2025.team14.integration;

import ca.sfu.cmpt276.fall2025.team14.IntegrationTestBase;
import ca.sfu.cmpt276.fall2025.team14.app.GameLogic;
import ca.sfu.cmpt276.fall2025.team14.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameLogicIntegrationTest extends IntegrationTestBase {

    // testing restart level when time is over
    @Test
    public void testRestartLevelOnTimeOver() {

        // set game state to INGAME
        GameLogic.setState(GameLogic.GameState.INGAME);

        // set timer to 0
        GameLogic.setRemainingTime(0);

        int initialLevel = GameLogic.getCurrentLevelIndex();

        // trigger update
        GameLogic.testUpdate();

        // level should restart - current level should remain the same and should be reloaded
        assertEquals(initialLevel, GameLogic.getCurrentLevelIndex());
    }

    // testing restart level when enemy collision
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

    // test pause
    @Test
    public void testPauseGame() {
        GameLogic.setState(GameLogic.GameState.INGAME);
        GameLogic.testPauseGame(); // will pause
        assertEquals(GameLogic.GameState.PAUSE, GameLogic.getState());

        GameLogic.testPauseGame(); // will unpause
        assertEquals(GameLogic.GameState.INGAME, GameLogic.getState());
    }

    // test end game
    @Test
    public void testEndGame() {
        assertDoesNotThrow(GameLogic::testEndGame);
    }
}
