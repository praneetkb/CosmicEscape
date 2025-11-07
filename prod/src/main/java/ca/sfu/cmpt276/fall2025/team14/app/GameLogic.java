package ca.sfu.cmpt276.fall2025.team14.app;

import ca.sfu.cmpt276.fall2025.team14.model.Alien;
import ca.sfu.cmpt276.fall2025.team14.model.Door;
import ca.sfu.cmpt276.fall2025.team14.model.Player;
import ca.sfu.cmpt276.fall2025.team14.model.Powerup;
import ca.sfu.cmpt276.fall2025.team14.model.PowerUpType;
import ca.sfu.cmpt276.fall2025.team14.model.Turret;
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
import java.util.Collection;
import java.util.List;

public final class GameLogic {

    // ----------------------- FIELDS -----------------------

    // map file name for different levels
    private static final String[] LEVELS = {"tutorial"};  // "level1", "level2", "level3"};
    private static int currentLevelIndex = 0;

    // start time in seconds
    private static int remainingTime = 120;

    // --- NEW FIELDS FOR POWER-UPS ---
    private static boolean isTimeStopped = false;
    private static final int TIMESTOP_DURATION = 5000; // 5 seconds
    // --- END NEW FIELDS ---


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

                    // Note: Turret spawning logic might be replaced by PropMapObjectLoader
                    // if you make Turret a Prop. This is fine for now.
                    Spawnpoint turrets = environment.getSpawnpoint("turret-spawn");
                    if (turrets != null) {
                        turrets.spawn(new Turret());
                    }

                    // Power-ups are NOT spawned here. They are loaded from the map
                    // as Props, thanks to PropMapObjectLoader in CosmicEscape.java
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

        // Only handle collisions if the environment is loaded
        if (Game.world().environment() != null) {
            handleCollisions();
        }
    }

    private static void handleCollisions() {
        Player player = Player.instance();

        for (ICollisionEntity entity : Game.physics().getCollisionEntities()) {

            // check if player collides with...
             if (player.getBoundingBox().intersects(entity.getBoundingBox())) {
                
                // ...Enemies (Alien or Turret)
                if (entity instanceof Alien || entity instanceof Turret) {
                    
                    if (player.isInvisible()) {
                        // Player is safe, do nothing
                    } else if (player.hasAlienCharm()) {
                        // Player is safe, but consumes the charm
                        player.useAlienCharm();
                    } else {
                        // Player is caught
                        restartLevel(); 
                    }
                
                // ...Powerups
                } else if (entity instanceof Powerup) {
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
        Game.window().getRenderComponent().fadeIn(500);
        Game.world().loadEnvironment(LEVELS[currentLevelIndex]);
    }

    public static void restartLevel() {
        // stop player movement
        Player.instance().stopMovement();

        // --- NEW: Reset power-up states ---
        Player.instance().resetPowerUps();
        if (isTimeStopped) {
            // Ensure enemies are unpaused if level restarts during a timestop
            isTimeStopped = false;
            pauseEnemies(false); 
        }

        // fade in
        Game.window().getRenderComponent().fadeIn(500);

        // reset environment
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
            turret.setRotating(!paused); // setRotating(true) means it rotates
        }
    }
}