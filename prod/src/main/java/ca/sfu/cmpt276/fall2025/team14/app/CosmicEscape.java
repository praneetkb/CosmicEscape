package ca.sfu.cmpt276.fall2025.team14.app;

import ca.sfu.cmpt276.fall2025.team14.model.*;
import ca.sfu.cmpt276.fall2025.team14.model.Door;
import ca.sfu.cmpt276.fall2025.team14.model.Vision;
import ca.sfu.cmpt276.fall2025.team14.model.Invisibility;
import ca.sfu.cmpt276.fall2025.team14.model.Jetpack;
import ca.sfu.cmpt276.fall2025.team14.model.Timestop;
import ca.sfu.cmpt276.fall2025.team14.screens.InGameScreen;
import ca.sfu.cmpt276.fall2025.team14.screens.MainMenuScreen;
import ca.sfu.cmpt276.fall2025.team14.screens.WinScreen;
import ca.sfu.cmpt276.fall2025.team14.utils.PathMapObjectLoader;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.environment.Environment;
import de.gurkenlabs.litiengine.environment.PropMapObjectLoader;
import de.gurkenlabs.litiengine.resources.Resources;

/**
 * Entry point for the Cosmic Escape game.  
 * Initializes the LITIengine framework, registers custom game object types,
 * loads resources, sets up screens, and starts the game loop.
 */
public class CosmicEscape {

    /**
     * The main method responsible for initializing and launching the game.  
     * Sets game metadata, loads resources, registers custom loaders, initializes
     * game logic, and finally starts the engine's main loop.
     *
     * @param args command-line arguments passed to the application
     */
    public static void main(String[] args) {
        // set meta information about the game
        Game.info().setName("Cosmic Escape");
        Game.info().setVersion("v1.0.0");
        Game.info().setDescription("A top-down 2D escape game! Watch out for the aliens!");

        // initialize LITIengine
        Game.init(args);

        // set base render scale
        Game.graphics().setBaseRenderScale(4f);
        Game.config().graphics().setEnableResolutionScale(true);

        // load resources from utiLITI file
        Resources.load("cosmic-escape.litidata");
        Resources.spritesheets().load("sprites/scrolling_bg.png", 960,540);

        //set title and icon for the window
        Game.window().setTitle("Cosmic Escape");
        Game.window().setIcon(Resources.images().get("sprites/icon.png"));

        // Register custom object loaders
        PropMapObjectLoader.registerCustomPropType(Button.class);
        PropMapObjectLoader.registerCustomPropType(DoorButton.class);
        PropMapObjectLoader.registerCustomPropType(LaserButton.class);
        PropMapObjectLoader.registerCustomPropType(Door.class);
        PropMapObjectLoader.registerCustomPropType(Crystal.class);
        PropMapObjectLoader.registerCustomPropType(Teleporter.class);
        PropMapObjectLoader.registerCustomPropType(Jetpack.class);
        PropMapObjectLoader.registerCustomPropType(Invisibility.class);
        PropMapObjectLoader.registerCustomPropType(Timestop.class);
        PropMapObjectLoader.registerCustomPropType(Punishment.class);
        PropMapObjectLoader.registerCustomPropType(Lasers.class);
        PropMapObjectLoader.registerCustomPropType(Slime.class);
        PropMapObjectLoader.registerCustomPropType(Radiation.class);
        PropMapObjectLoader.registerCustomPropType(AlienCharm.class);
        PropMapObjectLoader.registerCustomPropType(Vision.class);
        Environment.registerMapObjectLoader(new PathMapObjectLoader());

        // initialize game logic
        GameLogic.init();

        // add screens
        Game.screens().add(new MainMenuScreen());
        Game.screens().add(new InGameScreen());
        Game.screens().add(new WinScreen());

        // Initialize sound
        Game.config().sound().setMusicVolume(0.5f);
        Game.config().sound().setSoundVolume(0.5f);

        // start the game loop
        Game.start();
    }
}

