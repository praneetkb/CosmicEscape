package ca.sfu.cmpt276.fall2025.team14.app;

import de.gurkenlabs.litiengine.Game;

public class CosmicEscape {
    
    private CosmicEscape() {}

    public static void main(String[] args) {

        // initialize LITIengine
        Game.init(args);

        // initialize game logic 
        GameLogic.init();

        // load the first level
        Game.world().loadEnvironment("level1"); 

        // start the game loop
        Game.start();
    }
}
