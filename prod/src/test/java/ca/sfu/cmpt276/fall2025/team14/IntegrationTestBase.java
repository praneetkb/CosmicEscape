package ca.sfu.cmpt276.fall2025.team14;

import ca.sfu.cmpt276.fall2025.team14.app.GameLogic;
import ca.sfu.cmpt276.fall2025.team14.model.Player;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.resources.Resources;
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

            // generate safe config
            Properties props = new Properties();
            props.setProperty("sfx_soundVolume", "0.0");
            props.setProperty("sfx_musicVolume", "0.0");
            props.setProperty("gfx_displayMode", "WINDOWED");
            props.setProperty("input_gamepadSupport", "false");
            props.setProperty("cl_exitOnError", "false");
            try (FileOutputStream out = new FileOutputStream("config.properties")) {
                props.store(out, "Test Config");
            }

            // initialize engine
            Game.init("-nogui");

            Game.config().client().setExitOnError(false);
            Game.config().sound().setMusicVolume(0);
            Game.config().input().setGamepadSupport(false);

            Resources.load("cosmic-escape.litidata");

            // initialize Game Logic
            GameLogic.init();

            // create player
            Player.getInstance();

            if (!Game.hasStarted()) {
                Game.start();
            }

            // load level
            GameLogic.loadLevel(); 
            GameLogic.setState(GameLogic.GameState.INGAME);
            
            engineInitialized = true;
            System.out.println("LitiEngine initialized successfully");

        } catch (Throwable t) {
            System.err.println("Failed to initialize LitiEngine");
            t.printStackTrace(); 
            throw new RuntimeException("Cannot run integration tests without game engine", t);
        }
    }

    @AfterAll
    public static void cleanup() {
        System.out.println("Integration tests complete");
        // generate safe config
    }
}