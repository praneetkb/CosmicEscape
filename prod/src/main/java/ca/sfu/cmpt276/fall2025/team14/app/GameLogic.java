package ca.sfu.cmpt276.fall2025.team14.app;

import ca.sfu.cmpt276.fall2025.team14.model.*;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.Creature;
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
import java.util.Collection;
import java.util.List;

public final class GameLogic {

    // ----------------------- FIELDS -----------------------

    // map file name for different levels
    private static final String[] LEVELS = {"tutorial", "level1", "level2", "level3"};
    private static int currentLevelIndex = 0;

    // start time in seconds
    private static int remainingTime = 120;

    // ----------------------- MAIN METHODS -----------------------

    public static void init() {

        // add default game logic for when a level is loaded
        Game.world().addListener(new EnvironmentListener() {
            @Override
            public void initialized(Environment environment) {
                // Initialize camera to lock onto player
                Camera camera = new LocationLockCamera(Player.instance());
                camera.setClampToMap(true);
                camera.setZoom(1, 0);
                Game.world().setCamera(camera);
            }

            @Override
            public void loaded(Environment environment) {

                if (environment != null) {
                    Spawnpoint enter = environment.getSpawnpoint("player-spawn");
                    if (enter != null) {
                        enter.spawn(Player.instance());
                    }

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
        // Restart level for debugging
        Input.keyboard().onKeyPressed(KeyEvent.VK_R, e -> Game.loop().perform(1, GameLogic::restartLevel));

        if (Game.world().environment() == null) {
            loadLevel();
        }

        handleCollisions();
    }

    private static void handleCollisions() {

        Rectangle2D playerBox = Player.instance().getCollisionBox();

        for (ICollisionEntity entity : Game.physics().getCollisionEntities()) {
            if (playerBox.intersects(entity.getBoundingBox())) {
                if (entity instanceof Alien || entity instanceof Turret ||  entity instanceof Vision) {
                    restartLevel();
                }

                if (entity instanceof Door) {
                    entity.setCollision(false);
                }
            }
        }
    }

    // ----------- Level Handling -----------

    private static void loadLevel() {
        Game.window().getRenderComponent().fadeIn(500);
        Game.world().loadEnvironment(LEVELS[currentLevelIndex]);
    }

    public static void restartLevel() {
        // stop player movement
        Player.instance().stopMovement();

        // fade in
        Game.window().getRenderComponent().fadeIn(500);

        // reset environment
        Game.world().reset(Resources.maps().get(LEVELS[currentLevelIndex]));
        Game.world().loadEnvironment(LEVELS[currentLevelIndex]);
    }
}
