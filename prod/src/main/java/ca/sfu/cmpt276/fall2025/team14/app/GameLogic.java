package ca.sfu.cmpt276.fall2025.team14.app;

import ca.sfu.cmpt276.fall2025.team14.model.Alien;
import ca.sfu.cmpt276.fall2025.team14.model.Button;
import ca.sfu.cmpt276.fall2025.team14.model.Player;
import ca.sfu.cmpt276.fall2025.team14.model.Powerup;
import ca.sfu.cmpt276.fall2025.team14.model.PowerUpType;
import ca.sfu.cmpt276.fall2025.team14.model.Turret;
import ca.sfu.cmpt276.fall2025.team14.model.Teleporter;
import ca.sfu.cmpt276.fall2025.team14.model.*;
import ca.sfu.cmpt276.fall2025.team14.screens.InGameScreen;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.IEntity;
import de.gurkenlabs.litiengine.entities.Spawnpoint;
import de.gurkenlabs.litiengine.environment.Environment;
import de.gurkenlabs.litiengine.environment.EnvironmentListener;
import de.gurkenlabs.litiengine.graphics.Camera;
import de.gurkenlabs.litiengine.graphics.LocationLockCamera;
import de.gurkenlabs.litiengine.input.Input;
import de.gurkenlabs.litiengine.resources.Resources;
import ca.sfu.cmpt276.fall2025.team14.model.Crystal;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public final class GameLogic {

    // ----------------------- GAME STATE -----------------------
    public enum GameState {
        LOADING,
        MENU,
        INGAME,
        PAUSE,
        WIN
    }
    private static GameState STATE = GameState.MENU;

    // ----------------------- FIELDS -----------------------

    // --- LEVELS ---
    private static final ArrayList<String> LEVELS = new ArrayList<>();
    private static int currentLevelIndex = 0;
    private static int remainingCrystals = 1;

    // --- TIMER ---
    private static final int TIMER_MAX = 60;
    private static int remainingTime = TIMER_MAX;
    private static long lastTimeUpdate = System.currentTimeMillis();

    // --- POWER-UPS ---

    private static boolean isTimeStopped = false;
    private static final int TIMESTOP_DURATION = 5000; // 5 seconds

    /**
     * The increased movement speed when the Jetpack power-up is active.
     */
    private static final float JETPACK_VELOCITY = Player.getPlayerDefaultVelocity() * 1.5f; // 1.5x speed boost

    /**
     * Duration for the Jetpack power-up in milliseconds.
     */
    private static final int JETPACK_DURATION = 5000; // 5 seconds

    /**
     * Duration for the Invisibility power-up in milliseconds.
     */
    private static final int INVISIBILITY_DURATION = 5000; // 5 seconds

    /**
     * Duration for the temporary invulnerability after using Alien Charm in milliseconds.
     */
    private static final int CHARM_INVULNERABILITY_DURATION = 2000;

    // --- PUNISHMENTS ---
    private static boolean inSlime = false;

    // ----------------------- MAIN METHODS -----------------------

    // ----------- Initialization -----------

    public static void init() {
        // Add levels
        LEVELS.add("tutorial");
        LEVELS.add("level1");
        LEVELS.add("level2");
        // Add default game logic for when a level is loaded
        Game.world().addListener(new EnvironmentListener() {
            @Override
            public void initialized(Environment environment) {
                // Initialize camera to lock onto player
                Camera camera = new LocationLockCamera(Player.instance());
                camera.setClampToMap(true);
                camera.setZoom(3, 0); // default zoom is 3
                Game.world().setCamera(camera);
            }

            @Override
            public void loaded(Environment environment) {
                if (environment != null) {
                    // Set crystal amounts
                    remainingCrystals = Game.world().environment().getEntities(Crystal.class).size();
                    //--- Load player and turrets from spawn points when loaded ---
                    // Aliens are spawned with path, so no need to check
                    // Player
                    Spawnpoint playerSpawn = environment.getSpawnpoint("player-spawn");
                    if (playerSpawn != null) {
                        playerSpawn.spawn(Player.instance());
                    }
                    // Turret
                    for (Spawnpoint spawnpoint : environment.getSpawnpoints()) {
                        if (spawnpoint.getName().equals("turret-spawn")) {
                            Turret turret = new Turret();
                            // Rotation set in utiLITI
                            double minRot = spawnpoint.getProperties().getDoubleValue("minRotation");
                            double maxRot = spawnpoint.getProperties().getDoubleValue("maxRotation");
                            // Rotations
                            turret.setMinRotation(minRot);
                            turret.setMaxRotation(maxRot);
                            spawnpoint.spawn(turret);
                        }
                    }
                }
            }
        });
        // attach main update to LITI game loop
        Game.loop().attach(GameLogic::update);
        // Pause with Esc key
        Input.keyboard().onKeyReleased(KeyEvent.VK_ESCAPE, e -> {
            if (STATE == GameState.PAUSE || STATE == GameState.INGAME) {
                pauseGame();
            }
        });
    }

    // ----------- Main Game Loop -----------

    private static void update() {
        if (Game.world().environment() == null || STATE != GameState.INGAME) {
            return;
        }
        // Handle collisions
        handleCollisions();

        // Countdown Timer
        if ((!Game.world().environment().getMap().getName().equals("tutorial")) && STATE != GameState.PAUSE) {
            // timer countdown for every second
            long now = System.currentTimeMillis();
            if (now - lastTimeUpdate >= 1000) {
                remainingTime = Math.max(remainingTime - 1, 0); // to prevent negative time
                lastTimeUpdate = now;
                if (remainingTime == 0) {
                    restartLevel();
                }
            }
        }
        // Win condition
        if (currentLevelIndex >= LEVELS.size()) {
            endGame();
        }
    }

    private static void pauseGame() {
        if (STATE == GameState.INGAME) {
            Game.loop().setTimeScale(0);
            STATE = GameState.PAUSE;
        } else {
            Game.loop().setTimeScale(1);
            STATE = GameState.INGAME;
        }
    }

    private static void endGame() {
        // Set game state
        STATE = GameState.WIN;
        // Detach from loop
        Game.loop().detach(GameLogic::update);
        // Fade out
        Game.window().getRenderComponent().fadeOut(1000);
        // Audio cue
        Game.audio().playSound("level-up").setVolume(0.5f);
        // Transition to game
        Game.loop().perform(1000, () -> Game.screens().display("WIN-SCREEN"));
    }

    // ----------- Collision -----------

    private static void handleCollisions() {
        if (Game.world().environment() == null) {
            return;
        }
        // for loop will get every entity and check it against player
        for (IEntity entity : Game.world().environment().getEntities()) {
            boolean isColliding = isCollidingWithPlayer(entity);
            // Enemies
            if (entity instanceof Enemy) {
                if (( (Enemy) entity).playerInLos() || isColliding) {
                    if (!isPlayerProtected()) {
                        restartLevel();
                    }
                    continue;
                }
                continue;
            }
            // All other entities
            if (isColliding) {
                collisionHelper(entity);
            }
        }
    }

    private static void collisionHelper(IEntity entity) {
        // Button
        if (entity instanceof Button) {
            if (!((Button) entity).pressed()) {
                ((Button) entity).pressButton();
                Game.audio().playSound("button").setVolume(0.5f);
            }
        }
        // Teleporter (only activated when all crystals collected)
        if (entity instanceof Teleporter) {
            // teleporter will open only if player collides and all crystals are collected
            if (getRemainingCrystals() == 0 && STATE == GameState.INGAME) {
                Game.loop().perform(1, () -> {
                    currentLevelIndex++;
                    if (currentLevelIndex >= LEVELS.size()) {
                        return;
                    }
                    loadLevel();
                    Game.audio().playSound("level-up").setVolume(0.5f);
                });
            }
        }
        // Crystals
        if (entity instanceof Crystal) {
            Game.audio().playSound("crystal-pickup").setVolume(0.5f);
            Game.world().environment().remove(entity);
            remainingCrystals = Game.world().environment().getEntities(Crystal.class).size();
        }
        // Powerups
        if (entity instanceof Powerup) {
            applyPowerUpEffect((Powerup)  entity);
            // Remove the powerup from the world after it's collected
            Game.world().environment().remove(entity);
        }
        // Punishments
        if (entity instanceof Punishment) {
            applyPunishmentEffect((Punishment) entity);
        }
    }

    private static boolean isPlayerProtected() {
        Player p = Player.instance();
        if (p.isInvulnerable()) return true;
        if (p.isInvisible())    return true;
        if (p.hasAlienCharm()) {
            p.setHasAlienCharm(false);
            // Apply brief invulnerability to prevent immediate double-kill
            p.setInvulnerable(true);
            Game.loop().perform(CHARM_INVULNERABILITY_DURATION, () -> p.setInvulnerable(false));
            return true;
        }
        return false;
    }

    private static boolean isCollidingWithPlayer(IEntity entity) {
        return Player.instance().getCollisionBox().intersects(entity.getBoundingBox());
    }

    // ----------- Level Handling -----------

    public static void loadLevel() {
        STATE = GameState.LOADING;
        InGameScreen.pickNewBackground();
        // --- NEW: Reset power-up states ---
        Player.instance().resetPowerUps();
        if (isTimeStopped) {
            // Ensure enemies are unpaused if level restarts during a timestop
            isTimeStopped = false;
            pauseEnemies(false);
        }
        // Fade in
        Game.window().getRenderComponent().fadeIn(1000);
        // Load environment
        Game.world().loadEnvironment(LEVELS.get(currentLevelIndex));
        remainingTime = TIMER_MAX;
        // Stop player movement
        Player.instance().stopMovement();
        STATE = GameState.INGAME;
    }

    private static void restartLevel() {
        if (STATE != GameState.INGAME) {
            return;
        }
        Game.audio().playSound("game-over");
        Game.world().reset(Resources.maps().get(LEVELS.get(currentLevelIndex)));
        loadLevel();
    }

    // ----------- Powerups & Punishments -----------
    /**
     * Applies the effect of a collected power-up.
     * @param powerup The Powerup entity collided with.
     */
    private static void applyPowerUpEffect(Powerup powerup) {
        if (Game.world().environment() == null) {
            return;
        }
        // Get powerup type
        PowerUpType type = powerup.getType();
        if (type == null) return; // Invalid powerup from map
        // Play audio cue
        Game.audio().playSound("powerup-pickup").setVolume(0.5f);
        // Apply effects
        Player p = Player.instance();
        switch (type) {
            case TIMESTOP -> {
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
            case JETPACK -> {
                // Apply speed boost
                p.getVelocity().setBaseValue(JETPACK_VELOCITY);
                // Set a timer to remove the speed boost after the duration
                Game.loop().perform(JETPACK_DURATION, () -> {
                    // Check if another jetpack was picked up in the meantime.
                    // Only reset to default if the current velocity is still the jetpack velocity.
                    if (p.getVelocity().get() == JETPACK_VELOCITY) {
                        p.getVelocity().setBaseValue(Player.getPlayerDefaultVelocity());
                    }
                });
            }
            case INVISIBILITY -> {
                // Make player invisible
                p.setInvisible(true);
                // Set a timer to become visible again
                Game.loop().perform(INVISIBILITY_DURATION, () -> p.setInvisible(false));
            }
            case ALIEN_CHARM -> p.setHasAlienCharm(true);
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
            // Pause animation
            if (paused) alien.animations().getCurrent().pause();
            else alien.animations().getCurrent().unpause();
            // Stop movement
            alien.setTurnOnMove(!paused);
            alien.getVelocity().setBaseValue(paused ? 0 : Alien.getDefaultVelocity());
        }

        // Pause/unpause all Turrets
        for (Turret turret : Game.world().environment().getEntities(Turret.class)) {
            // Animations and movement handled in class
            turret.setDegPerSec(paused ? 0: Turret.getDefaultDegPerSec());
        }
    }

    private static void applyPunishmentEffect(Punishment punishment) {
        if (Game.world().environment() == null) {
            return;
        }
        // Get punishment type
        PunishmentType type = punishment.getPunishmentType();
        if (type == null) return;
        // Apply punishment
        switch (type) {
            case SLIME -> {
                inSlime = true;
                Player.instance().getVelocity().setBaseValue(inSlime ? 20 : Player.getPlayerDefaultVelocity());
                Game.loop().perform(500, () -> {
                    if (Game.world().environment().getEntities(Slime.class)
                            .stream()
                            .noneMatch(GameLogic::isCollidingWithPlayer)){
                        inSlime = false;
                        Player.instance().getVelocity().setBaseValue(Player.getPlayerDefaultVelocity());
                    }
                });
            }
            case RADIATION ->  {
                if (!Radiation.isCountdownStarted()) {
                    Radiation.startCountdown();
                }
                if (Radiation.getCountdownTimer() <= 0) {
                    restartLevel();
                }
                Game.loop().perform(500, () -> {
                    if (Game.world().environment().getEntities(Radiation.class)
                            .stream()
                            .noneMatch(GameLogic::isCollidingWithPlayer)) {
                        Radiation.stopCountdown();
                    }
                });
            }
            case LASER -> restartLevel();
        }
    }

    // ----------- Getters & Setters -----------
    // HELPERS FOR TESTING

    public static int getCurrentLevelIndex() {
        return currentLevelIndex;
    }

    public static void testHandleCollisions() { handleCollisions(); }

    public static void setRemainingTime(int time) { remainingTime = time; }

    public static Environment getEnvironment() { return Game.world().environment(); }

    public static void TestApplyPunishment(Punishment p) {
        GameLogic.applyPunishmentEffect(p);
    }

    public static void TestApplyPowerup(Powerup pu) {
        GameLogic.applyPowerUpEffect(pu);
    }

    public static boolean isInSlime() {
        return inSlime;
    }

    public static boolean isTimeStopped() {
        return isTimeStopped;
    }

    public static void setTimeStopped(boolean value) {
        isTimeStopped = value;
    }

    public static void testUpdate() {
        update();
    }

    public static int getRemainingCrystals() { return remainingCrystals; }

    public static void setRemainingCrystals(int value) { remainingCrystals = value; }

    public static void decrementCrystalCount() {
        remainingCrystals--;
    }

    public static int getRemainingTime() { return remainingTime; }

    public static GameState getState() {
        return STATE;
    }

    public static void setState(GameState state) {
        GameLogic.STATE = state;
    }
}