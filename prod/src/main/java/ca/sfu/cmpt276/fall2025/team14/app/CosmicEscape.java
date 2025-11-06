package ca.sfu.cmpt276.fall2025.team14.app;

import de.gurkenlabs.litiengine.Game;

public class CosmicEscape {
    
    private CosmicEscape() {}

    public static void main(String[] args) {

        // initialize litiengine
        Game.init(args);

        // initialize game logic 
        GameLogic.init();

        // start the game loop
        Game.start();

        // load the first level
        Game.world().loadEnvironment("level1");
    }
}
