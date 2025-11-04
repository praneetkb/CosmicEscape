package ca.sfu.cmpt276.fall2025.team14.app;

import ca.sfu.cmpt276.fall2025.team14.screens.InGameScreen;
import ca.sfu.cmpt276.fall2025.team14.utils.PathMapObjectLoader;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.environment.Environment;
import de.gurkenlabs.litiengine.resources.Resources;

public class Program {
    public static void main(String[] args) {
        // set meta information about the game
        Game.info().setName("Cosmic Escape");
        Game.info().setVersion("v1.0.0");
        Game.info().setDescription("A top-down 2D escape game! Watch out for the aliens!");

        // init the game infrastructure
        Game.init(args);

        // set the icon for the game (this has to be done after initialization because the ScreenManager will not be present otherwise)
        Game.graphics().setBaseRenderScale(4f);

        // load data from the utiLITI game file
        Resources.load("cosmic-escape.litidata");
        Environment.registerMapObjectLoader(new PathMapObjectLoader());

        GameLogic.init();

        // add the screens that will help you organize the different states of your game
        Game.screens().add(new InGameScreen());

        // load the first level (resources for the map were implicitly loaded from the game file)
        Game.world().loadEnvironment("tutorial");
        Game.start();
    }
}