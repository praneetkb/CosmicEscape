package ca.sfu.cmpt276.fall2025.team14;

import ca.sfu.cmpt276.fall2025.team14.app.GameLogic;
import ca.sfu.cmpt276.fall2025.team14.model.Player;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.resources.Resources; // <--- ADD IMPORT
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import java.io.FileOutputStream;
import java.util.Properties;

public abstract class IntegrationTestBase {
    private static boolean engineInitialized = false;

    @BeforeAll
    public static void setupGameEngine() {
        if (engineInitialized) {
            return;
        }

        try {
            System.out.println("Setting up LitiEngine for Testing...");

            // 1. Generate safe config
            Properties props = new Properties();
            props.setProperty("sfx_soundVolume", "0.0");
            props.setProperty("sfx_musicVolume", "0.0");
            props.setProperty("gfx_displayMode", "WINDOWED");
            props.setProperty("input_gamepadSupport", "false");
            props.setProperty("cl_exitOnError", "false");
            try (FileOutputStream out = new FileOutputStream("config.properties")) {
                props.store(out, "Test Config");
            }

            // 2. Init Engine
            Game.init("-nogui");

            // 3. Configure Engine
            Game.config().client().setExitOnError(false);
            Game.config().sound().setMusicVolume(0);
            Game.config().input().setGamepadSupport(false);

            // --- FIX 1: LOAD GAME RESOURCES ---
            // This is required so the engine knows what "tutorial" and sprites are.
            Resources.load("cosmic-escape.litidata");

            // 4. Initialize Game Logic
            GameLogic.init();

            // 5. Force Player Creation
            Player.getInstance();

            // --- FIX 2: START LOOP SAFELY ---
            // Only start the loop if it isn't already running (prevents Thread exceptions)
            if (!Game.hasStarted()) {
                Game.start();
            }

            // 6. Load Level
            GameLogic.loadLevel(); 
            GameLogic.setState(GameLogic.GameState.INGAME);
            
            engineInitialized = true;
            System.out.println("LitiEngine initialized successfully");

        } catch (Throwable t) {
            System.err.println("!!! FAILED TO INITIALIZE LITIENGINE !!!");
            t.printStackTrace(); 
            throw new RuntimeException("Cannot run integration tests without game engine", t);
        }
    }

    @AfterAll
    public static void cleanup() {
        System.out.println("Integration tests complete");
    }
}