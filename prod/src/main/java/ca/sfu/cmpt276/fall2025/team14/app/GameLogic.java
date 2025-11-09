package ca.sfu.cmpt276.fall2025.team14.app;

import ca.sfu.cmpt276.fall2025.team14.model.Alien;
import ca.sfu.cmpt276.fall2025.team14.model.Button;
import ca.sfu.cmpt276.fall2025.team14.model.Player;
import ca.sfu.cmpt276.fall2025.team14.model.Powerup;
import ca.sfu.cmpt276.fall2025.team14.model.PowerUpType;
import ca.sfu.cmpt276.fall2025.team14.model.Turret;
import ca.sfu.cmpt276.fall2025.team14.model.Teleporter;
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
import ca.sfu.cmpt276.fall2025.team14.model.Crystal;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

public final class GameLogic {

    // ----------------------- FIELDS -----------------------

    // map file name for different levels
    private static final String[] LEVELS = {"tutorial"};  // "level1", "level2", "level3"};
    private static int currentLevelIndex = 0;
    private static int remainingTime = 120; // start time in seconds
    private static long lastTimeUpdate = System.currentTimeMillis(); // timer counter
    private static int remainingCrystals = 1; // we start with 1 crystal for tutorial level

    // --- NEW FIELDS FOR POWER-UPS ---
    private static boolean isTimeStopped = false;
    private static final int TIMESTOP_DURATION = 5000; // 5 seconds
    // --- END NEW FIELDS ---

    // --- PUNISHMENT SYSTEM SETUP (FROM PREVIOUS STEP) ---
    // (Ensure you keep the previously added code for PUNISHMENT_FACTORY and GLOBAL_PUNISHMENT_EFFECTS)
    // ...
    // --- END PUNISHMENT SYSTEM SETUP ---


    // ----------------------- MAIN METHODS -----------------------

    // getters for HUD
    public static int getRemainingCrystals() {

        return remainingCrystals;
    }

    public static int getRemainingTime() {

        return remainingTime;
    }

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
        // R to restart level for debugging
        Input.keyboard().onKeyPressed(KeyEvent.VK_R, e -> Game.loop().perform(1, GameLogic::restartLevel));
        // Load non null level
        if (Game.world().environment() == null) {
            loadLevel();
        }
        // Handle collisions
        handleCollisions();

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
        // Get player collision box
        Rectangle2D playerBox = Player.instance().getCollisionBox();
        // for loop will get every collisionable entity and check it against player
        for (ICollisionEntity entity : Game.physics().getCollisionEntities()) {
            if (playerBox.intersects(entity.getBoundingBox())) {
                // Restart level on any enemy collision
                if (entity instanceof Alien || entity instanceof Turret || entity instanceof Vision) {
                    // ...Enemies (Alien or Turret)
                    if (Player.instance().isInvulnerable()) { // <-- ADDED: Check invulnerability first
                        // Player is safe due to recent charm use, do nothing
                    } else if (Player.instance().isInvisible()) {
                        // Player is safe due to Invisibility power-up
                    } else if (Player.instance().hasAlienCharm()) {
                        // Player is safe, but consumes the charm
                        Player.instance().useAlienCharm();
                    } else {
                        // Player is caught
                        restartLevel();
                    }
                }
                // TO DO: Check other collision entities (door, powerups, etc.)
                if (entity instanceof Button) {
                    ((Button) entity).pressButton();
                }
                // teleporter
                if (entity instanceof Teleporter) {
                    // teleporter will open only if player collides and all crystals are collected
                    if (getRemainingCrystals() == 0) {
                        System.out.println("Level complete!");
                        // nextLevel(); --> will add later
                    }
                }
                // for crystal collection
                if (entity instanceof Crystal) {
                    Game.world().environment().remove(entity);
                    remainingCrystals--;
                }
                // Powerups
                if (entity instanceof Powerup) {
                    Powerup powerup = (Powerup) entity;
                    applyPowerUpEffect(powerup);
                    // Remove the powerup from the world after it's collected
                    Game.world().environment().remove(powerup);
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
        // --- NEW: Reset power-up states ---
        Player.instance().resetPowerUps();
        if (isTimeStopped) {
            // Ensure enemies are unpaused if level restarts during a timestop
            isTimeStopped = false;
            pauseEnemies(false);
        }
        // Fade in
        Game.window().getRenderComponent().fadeIn(500);
        // Reset environment
        Game.world().reset(Resources.maps().get(LEVELS[currentLevelIndex]));
        Game.world().loadEnvironment(LEVELS[currentLevelIndex]);
    }

    /**
     * Applies the effect of a collected power-up.
     * @param powerup The Powerup entity collided with.
     */
    private static void applyPowerUpEffect(Powerup powerup) {
        Player player = Player.instance();
        PowerUpType type = powerup.getType();

        if (type == null) return; // Invalid powerup from map

        // Apply player-specific effects (speed, invisibility, charm)
        player.applyPowerUp(type);

        // Apply global effects (timestop)
        if (type == PowerUpType.TIMESTOP) {
            if (!isTimeStopped) { // Prevent stacking durations
                isTimeStopped = true;
                pauseEnemies(true);

                // Set timer to unpause enemies
                Game.loop().perform(TIMESTOP_DURATION, () -> {
                    isTimeStopped = false;
                    pauseEnemies(false);
                });
            }
        }
    }

    /**
     * Pauses or unpauses all enemies in the world for the TIMESTOP power-up.
     * @param paused true to pause enemies, false to unpause.
     */
    private static void pauseEnemies(boolean paused) {
        if (Game.world().environment() == null) {
            return;
        }

        // Pause/unpause all Aliens
        for (Alien alien : Game.world().environment().getEntities(Alien.class)) {
            // Get default velocity from Alien.java's @MovementInfo annotation
            float defaultAlienVelocity = 20;
            alien.getVelocity().setBaseValue(paused ? 0 : defaultAlienVelocity);
        }

        // Pause/unpause all Turrets
        for (Turret turret : Game.world().environment().getEntities(Turret.class)) {
            // TO DO: Once turret rotation implemented
            //turret.setRotating(!paused); // setRotating(true) means it rotates
        }
    }
}