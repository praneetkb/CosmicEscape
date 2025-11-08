package ca.sfu.cmpt276.fall2025.team14.app;

import ca.sfu.cmpt276.fall2025.team14.model.Alien;
import ca.sfu.cmpt276.fall2025.team14.model.Door;
import ca.sfu.cmpt276.fall2025.team14.model.Player;
import ca.sfu.cmpt276.fall2025.team14.model.Turret;
import ca.sfu.cmpt276.fall2025.team14.model.Teleporter;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.ICollisionEntity;
import de.gurkenlabs.litiengine.entities.Spawnpoint;
import de.gurkenlabs.litiengine.environment.Environment;
import de.gurkenlabs.litiengine.environment.EnvironmentListener;
import de.gurkenlabs.litiengine.graphics.Camera;
import de.gurkenlabs.litiengine.graphics.LocationLockCamera;
import de.gurkenlabs.litiengine.input.Input;
import de.gurkenlabs.litiengine.resources.Resources;
import ca.sfu.cmpt276.fall2025.team14.model.Crystal;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.List;


public final class GameLogic {

    // ----------------------- FIELDS -----------------------

    // map file name for different levels
    private static final String[] LEVELS = {"tutorial"};  // "level1", "level2", "level3"};
    private static int currentLevelIndex = 0;
    private static int remainingTime = 120; // start time in seconds
    private static long lastTimeUpdate = System.currentTimeMillis(); // timer counter
    private static int remainingCrystals = 3; // we start with 3 crystals for tutorial level


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

                    Spawnpoint crystal = environment.getSpawnpoint("crystal-spawn");
                    if (crystal != null) {
                        crystal.spawn(new Crystal());
                    }
                }
            }
        });

        // attach main update to LITI game loop
        Game.loop().attach(GameLogic::update);
    }


    private static void update() {

        // restart level for debugging
        Input.keyboard().onKeyPressed(KeyEvent.VK_R, e -> Game.loop().perform(1, GameLogic::restartLevel));

        if (Game.world().environment() == null) {
            loadLevel();
        }

        handleCollisions();

        // to ensure buttons and doors get their update method called each frame
        for (ICollisionEntity entity : Game.physics().getCollisionEntities()) {
            if (entity instanceof Door door) {
                ((IUpdateable) door).update();
            }
            if (entity instanceof Button button) {
                ((IUpdateable) button).update(); // this is because it is getting confused with the AWT update
            }
        }

        // timer countdown for every second
        long now = System.currentTimeMillis();
        if (now - lastTimeUpdate >= 1000) {
            remainingTime = Math.max(remainingTime - 1, 0); // to prevent negative time
            lastTimeUpdate = now;

            if (remainingTime == 0) {
                restartLevel(); // will add "Game Over" later
            }
        }
    }

    private static void handleCollisions() {
        Player player = Player.instance();

        for (ICollisionEntity entity : Game.physics().getCollisionEntities()) {

            // check if player collides with enemies
            if (player.getCollisionBox().intersects(entity.getBoundingBox())) {
                if (entity instanceof Alien || entity instanceof Turret) {
                    restartLevel(); // reset level on collision
                }
            }

            // for crystal collection
            if (player.getCollisionBox().intersects(entity.getBoundingBox())) {
                if (entity instanceof Crystal crystal) {
                    Game.world().environment().remove(crystal);
                    remainingCrystals--;
                }
            }

            // teleporter
            if (player.getCollisionBox().intersects(entity.getBoundingBox())) {
                if (entity instanceof Teleporter teleporter) {
                    // teleporter will open only if player collides and all crystals are collected
                    if (GameLogic.getRemainingCrystals() == 0 &&
                            player.getCollisionBox().intersects(entity.getBoundingBox())) {
                        System.out.println("Level complete!");
                        // nextLevel(); --> will add later
                    }
                }
            }
        }
    }

    // getters for HUD
    public static int getRemainingCrystals() {
        return remainingCrystals;
    }

    public static int getRemainingTime() {
        return remainingTime;
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
