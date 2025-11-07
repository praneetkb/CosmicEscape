package ca.sfu.cmpt276.fall2025.team14.app;

import ca.sfu.cmpt276.fall2025.team14.model.*;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.ICollisionEntity;
import de.gurkenlabs.litiengine.entities.Spawnpoint;
import de.gurkenlabs.litiengine.environment.Environment;
import de.gurkenlabs.litiengine.environment.EnvironmentListener;
import de.gurkenlabs.litiengine.graphics.Camera;
import de.gurkenlabs.litiengine.graphics.LocationLockCamera;
import de.gurkenlabs.litiengine.input.Input;
import de.gurkenlabs.litiengine.resources.Resources;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

public final class GameLogic {

    // ----------------------- FIELDS -----------------------

    // map file name for different levels
    private static final String[] LEVELS = {"tutorial", "level1", "level2", "level3"};
    private static int currentLevelIndex = 0;

    // start time in seconds
    private static int remainingTime = 120;

    // ----------------------- MAIN METHODS -----------------------

    public static void init() {
        // Add default game logic for when a level is loaded
        Game.world().addListener(new EnvironmentListener() {
            @Override
            public void initialized(Environment environment) {
                // Initialize camera to lock onto player
                Camera camera = new LocationLockCamera(Player.instance());
                camera.setClampToMap(true);
                camera.setZoom(1, 0); // default zoom is 3
                Game.world().setCamera(camera);
            }

            @Override
            public void loaded(Environment environment) {
                // Load player and turrets from spawn points when loaded
                // Aliens are spawned with path, so no need to check
                if (environment != null) {
                    // Player
                    Spawnpoint enter = environment.getSpawnpoint("player-spawn");
                    if (enter != null) {
                        enter.spawn(Player.instance());
                    }
                    // Turret
                    Spawnpoint turrets = environment.getSpawnpoint("turret-spawn");
                    if (turrets != null) {
                        turrets.spawn(new Turret());
                    }
                }
            }
        });
        // attach main update to LITI game loop
        Game.loop().attach(GameLogic::update);
    }

    private static void update() {
        // R to restart level for debugging
        Input.keyboard().onKeyPressed(KeyEvent.VK_R, e -> Game.loop().perform(1, GameLogic::restartLevel));
        // Load non null level
        if (Game.world().environment() == null) {
            loadLevel();
        }
        // Handle collisions
        handleCollisions();
    }

    private static void handleCollisions() {
        // Get player collision box
        Rectangle2D playerBox = Player.instance().getCollisionBox();
        // for loop will get every collisionable entity and check it against player
        for (ICollisionEntity entity : Game.physics().getCollisionEntities()) {
            if (playerBox.intersects(entity.getBoundingBox())) {
                // Restart level on any enemy collision
                if (entity instanceof Alien || entity instanceof Turret ||  entity instanceof Vision) {
                    restartLevel();
                }

                // TO DO: Check other collision entities (door, powerups, etc.)
                if (entity instanceof Door) {
                    entity.setCollision(false);
                }
            }
        }
    }

    // ----------- Level Handling -----------

    private static void loadLevel() {
        // Fade out
        Game.window().getRenderComponent().fadeIn(500);
        // Load environment
        Game.world().loadEnvironment(LEVELS[currentLevelIndex]);
    }

    public static void restartLevel() {
        // Stop player movement
        Player.instance().stopMovement();
        // Fade in
        Game.window().getRenderComponent().fadeIn(500);
        // Reset environment
        Game.world().reset(Resources.maps().get(LEVELS[currentLevelIndex]));
        Game.world().loadEnvironment(LEVELS[currentLevelIndex]);
    }
}
