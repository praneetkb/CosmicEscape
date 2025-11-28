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

/**
 * Core game logic handler for Cosmic Escape.
 * <p>
 * This class manages:
 * <ul>
 *   <li>Game state transitions (menu, in-game, pause, win)</li>
 *   <li>Level loading and restarting</li>
 *   <li>Player interactions and collisions</li>
 *   <li>Timers, power-ups, and punishments</li>
 *   <li>Enemy pausing and behavior logic</li>
 * </ul>
 *
 * All methods and fields are static, as this class acts as a global
 * controller invoked by the LITIengine game loop.
 */
public final class GameLogic {

    // ----------------------- GAME STATE -----------------------

    /**
     * All possible states the game can be in.
     */
    public enum GameState {
        LOADING,
        MENU,
        INGAME,
        PAUSE,
        WIN
    }

    /**
     * Current active game state. Defaults to the MENU state.
     */
    private static GameState STATE = GameState.MENU;

    /**
     * Whether test mode is active. When true, certain logic may be bypassed.
     */
    public static boolean TEST_MODE = false; // for tests

    // ----------------------- FIELDS -----------------------

    // --- LEVELS ---

    /**
     * List of all playable level names in order.
     */
    private static final ArrayList<String> LEVELS = new ArrayList<>();

    /**
     * The index of the currently loaded level inside {@link #LEVELS}.
     */
    private static int currentLevelIndex = 0;

    /**
     * Number of crystals remaining in the level.
     * When it reaches 0, the teleporter can be used.
     */
    private static int remainingCrystals = 1;

    // --- TIMER ---

    /**
     * Maximum allowed level time in seconds.
     */
    private static final int TIMER_MAX = 60;

    /**
     * Remaining time before the player is forced to restart.
     */
    private static int remainingTime = TIMER_MAX;

    /**
     * Timestamp of the last time update, used for 1-second countdown logic.
     */
    private static long lastTimeUpdate = System.currentTimeMillis();

    // --- POWER-UPS ---

    /**
     * Whether the TIMESTOP power-up is currently active.
     */
    private static boolean isTimeStopped = false;

    /**
     * Duration of the TIMESTOP power-up in milliseconds.
     */
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
     * Duration for temporary invulnerability granted by Alien Charm.
     */
    private static final int CHARM_INVULNERABILITY_DURATION = 2000;

    // --- PUNISHMENTS ---

    /**
     * Whether the player is currently inside slime (slowed).
     */
    private static boolean inSlime = false;

    // ----------------------- MAIN METHODS -----------------------

    // ----------- Initialization -----------

    /**
     * Initializes all game logic:
     * <ul>
     *   <li>Registers level list</li>
     *   <li>Sets up environment listeners</li>
     *   <li>Attaches update loop</li>
     *   <li>Configures pause keybind</li>
     * </ul>
     */
    public static void init() {
        // Add levels
        LEVELS.add("tutorial");
        LEVELS.add("level1");
        LEVELS.add("level2");

        // Add default game logic for when a level is loaded
        Game.world().addListener(new EnvironmentListener() {
            @Override
            public void initialized(Environment environment) {
                /**
                 * Initializes the camera each time the environment starts.
                 * Locks onto the Player and clamps to map boundaries.
                 */
                Camera camera = new LocationLockCamera(Player.instance());
                camera.setClampToMap(true);
                camera.setZoom(3, 0); // default zoom is 3
                Game.world().setCamera(camera);
            }

            @Override
            public void loaded(Environment environment) {
                /**
                 * Called once the map has fully loaded.
                 * Spawns player and turrets and counts initial crystals.
                 */
                if (environment != null) {
                    // Set crystal amounts
                    remainingCrystals = Game.world().environment().getEntities(Crystal.class).size();

                    // Player spawn
                    Spawnpoint playerSpawn = environment.getSpawnpoint("player-spawn");
                    if (playerSpawn != null) {
                        playerSpawn.spawn(Player.instance());
                    }

                    // Turret spawns
                    for (Spawnpoint spawnpoint : environment.getSpawnpoints()) {
                        if (spawnpoint.getName().equals("turret-spawn")) {
                            Turret turret = new Turret();
                            double minRot = spawnpoint.getProperties().getDoubleValue("minRotation");
                            double maxRot = spawnpoint.getProperties().getDoubleValue("maxRotation");
                            turret.setMinRotation(minRot);
                            turret.setMaxRotation(maxRot);
                            spawnpoint.spawn(turret);
                        }
                    }
                }
            }
        });

        // Attach main update to LITI game loop
        Game.loop().attach(GameLogic::update);

        // Pause with Esc key
        Input.keyboard().onKeyReleased(KeyEvent.VK_ESCAPE, e -> {
            if (STATE == GameState.PAUSE || STATE == GameState.INGAME) {
                pauseGame();
            }
        });
    }
    // ----------- Main Game Loop -----------

    /**
     * Main game update loop, attached to LITIengine.
     * <p>
     * This method:
     * <ul>
     *   <li>Runs collision handling</li>
     *   <li>Updates countdown timer</li>
     *   <li>Triggers level restarts when time expires</li>
     *   <li>Checks win condition when final level is passed</li>
     * </ul>
     */
    private static void update() {
        if (Game.world().environment() == null || STATE != GameState.INGAME) {
            return;
        }

        // Handle collisions
        handleCollisions();

        // Countdown Timer
        if ((!Game.world().environment().getMap().getName().equals("tutorial")) && STATE != GameState.PAUSE) {
            long now = System.currentTimeMillis();
            if (now - lastTimeUpdate >= 1000) {
                remainingTime = Math.max(remainingTime - 1, 0);
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

    /**
     * Toggles between PAUSE and INGAME states.
     * <p>
     * Pausing sets time scale to zero, freezing all update-driven entities.
     * Unpausing restores time scale to normal.
     */
    private static void pauseGame() {
        if (STATE == GameState.INGAME) {
            Game.loop().setTimeScale(0);
            STATE = GameState.PAUSE;
        } else {
            Game.loop().setTimeScale(1);
            STATE = GameState.INGAME;
        }
    }

    /**
     * Handles ending the game.
     * <ul>
     *   <li>Sets WIN state</li>
     *   <li>Detaches update loop</li>
     *   <li>Fades out screen</li>
     *   <li>Plays victory sound</li>
     *   <li>Transitions to win screen</li>
     * </ul>
     */
    private static void endGame() {
        STATE = GameState.WIN;
        Game.loop().detach(GameLogic::update);
        Game.window().getRenderComponent().fadeOut(1000);
        Game.audio().playSound("level-up").setVolume(0.5f);
        Game.loop().perform(1000, () -> Game.screens().display("WIN-SCREEN"));
    }

    // ----------- Collision -----------

    /**
     * Central collision management function.
     * <p>
     * Loops through all entities in the environment and:
     * <ul>
     *   <li>Checks if they intersect with the player</li>
     *   <li>Handles enemy LOS (line-of-sight) detection</li>
     *   <li>Delegates collision responses to helper methods</li>
     * </ul>
     */
    private static void handleCollisions() {
        if (Game.world().environment() == null) {
            return;
        }

        for (IEntity entity : Game.world().environment().getEntities()) {
            boolean isColliding = isCollidingWithPlayer(entity);

            // Enemies
            if (entity instanceof Enemy) {
                if (((Enemy) entity).playerInLos() || isColliding) {
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

    /**
     * Resolves specific interactions when the player collides with an entity.
     * Handles:
     * <ul>
     *   <li>Button presses</li>
     *   <li>Teleporter activation</li>
     *   <li>Crystal pickups</li>
     *   <li>Power-up pickups</li>
     *   <li>Punishment effects</li>
     * </ul>
     *
     * @param entity the entity colliding with the player
     */
    private static void collisionHelper(IEntity entity) {
        // Button
        if (entity instanceof Button) {
            if (!((Button) entity).pressed()) {
                ((Button) entity).pressButton();
                Game.audio().playSound("button").setVolume(0.5f);
            }
        }

        // Teleporter
        if (entity instanceof Teleporter) {
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

        // Crystal
        if (entity instanceof Crystal) {
            Game.audio().playSound("crystal-pickup").setVolume(0.5f);
            Game.world().environment().remove(entity);
            remainingCrystals = Game.world().environment().getEntities(Crystal.class).size();
        }

        // Powerups
        if (entity instanceof Powerup) {
            applyPowerUpEffect((Powerup) entity);
            Game.world().environment().remove(entity);
        }

        // Punishments
        if (entity instanceof Punishment) {
            applyPunishmentEffect((Punishment) entity);
        }
    }

    /**
     * Determines whether the player avoids enemy collision effects due to:
     * <ul>
     *   <li>Invisibility</li>
     *   <li>Invulnerability</li>
     *   <li>Alien Charm (consumed on use)</li>
     * </ul>
     *
     * @return true if protected, false otherwise
     */
    private static boolean isPlayerProtected() {
        Player p = Player.instance();
        if (p.isInvulnerable()) return true;
        if (p.isInvisible())    return true;

        if (p.hasAlienCharm()) {
            p.setHasAlienCharm(false);
            p.setInvulnerable(true);
            Game.loop().perform(CHARM_INVULNERABILITY_DURATION,
                    () -> p.setInvulnerable(false));
            return true;
        }

        return false;
    }

    /**
     * Returns whether the given entity’s bounding box intersects the player.
     *
     * @param entity any world entity
     * @return true if colliding, false otherwise
     */
    private static boolean isCollidingWithPlayer(IEntity entity) {
        return Player.instance().getCollisionBox().intersects(entity.getBoundingBox());
    }

    // ----------- Level Handling -----------

    /**
     * Loads the current level:
     * <ul>
     *   <li>Sets LOADING state</li>
     *   <li>Resets player power-up state</li>
     *   <li>Handles paused enemies if TIMESTOP was active</li>
     *   <li>Fades screen</li>
     *   <li>Loads environment</li>
     *   <li>Stops player movement</li>
     *   <li>Begins INGAME state</li>
     * </ul>
     */
    public static void loadLevel() {
        STATE = GameState.LOADING;
        InGameScreen.pickNewBackground();

        Player.instance().resetPowerUps();

        if (isTimeStopped) {
            isTimeStopped = false;
            pauseEnemies(false);
        }

        Game.window().getRenderComponent().fadeIn(1000);
        Game.world().loadEnvironment(LEVELS.get(currentLevelIndex));
        remainingTime = TIMER_MAX;

        Player.instance().stopMovement();
        STATE = GameState.INGAME;
    }

    /**
     * Restarts the current level:
     * <ul>
     *   <li>Plays game-over sound</li>
     *   <li>Resets the environment</li>
     *   <li>Reloads the same level</li>
     * </ul>
     */
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
     * Applies the gameplay effect of a collected power-up.
     * <p>
     * Handles:
     * <ul>
     *   <li>TIMESTOP — freezes all enemies temporarily</li>
     *   <li>JETPACK — boosts player speed temporarily</li>
     *   <li>INVISIBILITY — player cannot be seen by enemies</li>
     *   <li>ALIEN_CHARM — single-use protection against alien detection</li>
     * </ul>
     *
     * @param powerup the power-up entity picked up by the player
     */
    private static void applyPowerUpEffect(Powerup powerup) {
        if (Game.world().environment() == null) {
            return;
        }

        PowerUpType type = powerup.getType();
        if (type == null) return;

        Game.audio().playSound("powerup-pickup").setVolume(0.5f);

        Player p = Player.instance();

        switch (type) {
            case TIMESTOP -> {
                if (!isTimeStopped) {
                    isTimeStopped = true;
                    pauseEnemies(true);

                    Game.loop().perform(TIMESTOP_DURATION, () -> {
                        isTimeStopped = false;
                        pauseEnemies(false);
                    });
                }
            }

            case JETPACK -> {
                p.getVelocity().setBaseValue(JETPACK_VELOCITY);
                Game.loop().perform(JETPACK_DURATION, () -> {
                    if (p.getVelocity().get() == JETPACK_VELOCITY) {
                        p.getVelocity().setBaseValue(Player.getPlayerDefaultVelocity());
                    }
                });
            }

            case INVISIBILITY -> {
                p.setInvisible(true);
                Game.loop().perform(INVISIBILITY_DURATION, () -> p.setInvisible(false));
            }

            case ALIEN_CHARM -> p.setHasAlienCharm(true);
        }
    }

    /**
     * Pauses or unpauses all relevant enemies.
     * <p>
     * Used during the TIMESTOP power-up. This method:
     * <ul>
     *   <li>Freezes/unfreezes alien animations</li>
     *   <li>Stops/starts alien movement</li>
     *   <li>Freezes/unfreezes turret rotation speed</li>
     * </ul>
     *
     * @param paused true to pause all enemies, false to resume them
     */
    private static void pauseEnemies(boolean paused) {
        if (Game.world().environment() == null) {
            return;
        }

        // Pause/unpause all Aliens
        for (Alien alien : Game.world().environment().getEntities(Alien.class)) {
            if (paused) alien.animations().getCurrent().pause();
            else        alien.animations().getCurrent().unpause();

            alien.setTurnOnMove(!paused);
            alien.getVelocity().setBaseValue(paused ? 0 : Alien.getDefaultVelocity());
        }

        // Pause/unpause all Turrets
        for (Turret turret : Game.world().environment().getEntities(Turret.class)) {
            turret.setDegPerSec(paused ? 0 : Turret.getDefaultDegPerSec());
        }
    }

    /**
     * Applies the effects of various punishment zones (Slime, Radiation, Laser).
     * <p>
     * Slime:
     * <ul><li>Slows the player until they exit slime tiles</li></ul>
     * Radiation:
     * <ul><li>Starts a countdown that kills the player if fully exposed</li></ul>
     * Laser:
     * <ul><li>Instantly restarts the level</li></ul>
     *
     * @param punishment the punishment entity the player has collided with
     */
    private static void applyPunishmentEffect(Punishment punishment) {
        if (Game.world().environment() == null) {
            return;
        }

        PunishmentType type = punishment.getPunishmentType();
        if (type == null) return;

        switch (type) {
            case SLIME -> {
                inSlime = true;
                Player.instance().getVelocity().setBaseValue(
                        inSlime ? 20 : Player.getPlayerDefaultVelocity());

                Game.loop().perform(500, () -> {
                    if (Game.world().environment().getEntities(Slime.class)
                            .stream()
                            .noneMatch(GameLogic::isCollidingWithPlayer)) {
                        inSlime = false;
                        Player.instance().getVelocity().setBaseValue(
                                Player.getPlayerDefaultVelocity());
                    }
                });
            }

            case RADIATION -> {
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

    /**
     * @return the index of the current level being played
     */
    public static int getCurrentLevelIndex() {
        return currentLevelIndex;
    }

    /**
     * Sets which level index should be considered “current.”
     *
     * @param currentLevelIndex the new index
     */
    public static void setCurrentLevelIndex(int currentLevelIndex) {
        GameLogic.currentLevelIndex = currentLevelIndex;
    }

    /**
     * For testing purposes, forces collision detection logic to run.
     */
    public static void testHandleCollisions() { handleCollisions(); }

    /**
     * Sets the time remaining on the game timer.
     *
     * @param time new remaining time value
     */
    public static void setRemainingTime(int time) { remainingTime = time; }

    /**
     * @return the currently loaded environment
     */
    public static Environment getEnvironment() { return Game.world().environment(); }

    /**
     * Test helper: directly applies a punishment effect without player collision.
     *
     * @param p punishment instance to apply
     */
    public static void TestApplyPunishment(Punishment p) {
        GameLogic.applyPunishmentEffect(p);
    }

    /**
     * Test helper: directly applies a power-up effect without player collision.
     *
     * @param pu the power-up instance to apply
     */
    public static void TestApplyPowerup(Powerup pu) {
        GameLogic.applyPowerUpEffect(pu);
    }

    /**
     * @return whether the player is currently slowed by slime
     */
    public static boolean isInSlime() {
        return inSlime;
    }

    /**
     * @return whether the TIMESTOP effect is currently active
     */
    public static boolean isTimeStopped() {
        return isTimeStopped;
    }

    /**
     * Sets whether the TIMESTOP effect is considered active.
     *
     * @param value new timestop state
     */
    public static void setTimeStopped(boolean value) {
        isTimeStopped = value;
    }

    /**
     * Test helper: directly invokes the update loop.
     */
    public static void testUpdate() {
        update();
    }

    /**
     * @return number of crystals left to collect in current level
     */
    public static int getRemainingCrystals() { return remainingCrystals; }

    /**
     * Sets crystal count directly.
     *
     * @param value new count
     */
    public static void setRemainingCrystals(int value) { remainingCrystals = value; }

    /**
     * Decreases crystal count by one.
     */
    public static void decrementCrystalCount() {
        remainingCrystals--;
    }

    /**
     * @return the remaining time on the level timer
     */
    public static int getRemainingTime() { return remainingTime; }

    /**
     * @return current game state
     */
    public static GameState getState() {
        return STATE;
    }

    /**
     * Sets the global game state.
     *
     * @param state the new game state
     */
    public static void setState(GameState state) {
        GameLogic.STATE = state;
    }
}

