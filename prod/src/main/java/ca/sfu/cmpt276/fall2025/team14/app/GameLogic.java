package ca.sfu.cmpt276.fall2025.team14.app;

import ca.sfu.cmpt276.fall2025.team14.model.Alien;
import ca.sfu.cmpt276.fall2025.team14.model.Player;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.IEntity;
import de.gurkenlabs.litiengine.entities.Spawnpoint;
import de.gurkenlabs.litiengine.graphics.Camera;
import de.gurkenlabs.litiengine.graphics.LocationLockCamera;

import java.util.Collection;

public final class GameLogic {

    // map file name for different levels
    private static final String[] levels = {"level1", "level2", "level3"};
    private static int currentLevelIndex = 0;

    // max number of hits (enemy collisions) before game restarts 
    private static final int maxHits = 3; 

    // counter for hits (enemy collision)
    private static int hits = 0; 

    // start time in seconds 
    private static int remainingTime = 120; 

    // called when player is hit by an enemy 
    public static void handlePlayerHit() {
        hits++;
        remainingTime -= 20; // time penalty for each hit (enemy collision)
    
        if (hits >= maxHits) {
            System.out.println("You lost! Restarting...");
            Game.loop().perform(1000, GameLogic::restartLevel); // delay for the user to see the dying effect and print message before restarting
        }
    }

    private GameLogic() {
    }

    public static void init() {

        // we'll use a camera in our game that is locked to the location of the player
        Camera camera = new LocationLockCamera(Player.instance());
        camera.setClampToMap(true);
        camera.setZoom(3, 0);
        Game.world().setCamera(camera);

        // add default game logic for when a level was loaded
        Game.world().onLoaded(e -> {

            // reset hits and time  
            hits = 0;
            remainingTime = 120;

            // spawn the player instance on the spawn point with the name "enter"
            Spawnpoint enter = e.getSpawnpoint("player-spawn");
            if (enter != null) {
                enter.spawn(Player.instance());
            }

            // spawn enemies 
            Spawnpoint enemySpawn = e.getSpawnpoint("enemy-spawn");
            Collection<IEntity> enemies = Game.world().environment().getEntities();

            for (IEntity entity : enemies) {
                if (entity instanceof Alien) {
                    enemySpawn.spawn((Alien) entity);
                }
            }

             // decreases game time every second
             Game.loop().perform(1000, () -> {
                remainingTime--;
                if (remainingTime <= 0) {
                    restartLevel();
                }
            });
        });
    }

    private static void restartLevel() {

        // stop player movement 
        Player.instance().stopMovement();

        // reload the current environment
        Game.world().loadEnvironment(levels[currentLevelIndex]);
    }
}

/* add this for next levels. Increment currentLevelIndex before loading the next level

private static void loadNextLevel() {
    currentLevelIndex++;
    if (currentLevelIndex >= LEVELS.length) {
        currentLevelIndex = 0; // loop back to first level if needed
    }
    Game.world().loadEnvironment(LEVELS[currentLevelIndex]);
} 
    */