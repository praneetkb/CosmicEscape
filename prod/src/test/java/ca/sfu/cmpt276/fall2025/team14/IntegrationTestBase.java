package ca.sfu.cmpt276.fall2025.team14;

import ca.sfu.cmpt276.fall2025.team14.app.GameLogic;
import de.gurkenlabs.litiengine.Game;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;

/**
 * Base class for integration tests that need the LitiEngine game engine.
 * Initializes the engine once before all tests.
 */
public abstract class IntegrationTestBase {
    private static boolean engineInitialized = false;

    @BeforeAll
    public static void setupGameEngine() {
        if (engineInitialized) {
            return;
        }

        try {
            System.out.println("Attempting to initialize LitiEngine...");
            System.out.println("Headless: " + System.getProperty("java.awt.headless"));

            Game.init();

            GameLogic.setState(GameLogic.GameState.INGAME);

            engineInitialized = true;
            System.out.println("LitiEngine initialized successfully");

        } catch (Exception e) {
            System.err.println("Failed to initialize LitiEngine");
            throw new RuntimeException("Cannot run integration tests without game engine", e);
        }
    }

    @AfterAll
    public static void cleanup() {
        System.out.println("Integration tests complete");
    }
}
