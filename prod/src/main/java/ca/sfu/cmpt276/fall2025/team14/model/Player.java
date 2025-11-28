package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.Valign;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.EntityInfo;
import de.gurkenlabs.litiengine.entities.MovementInfo;
import de.gurkenlabs.litiengine.graphics.animation.IEntityAnimationController;
import de.gurkenlabs.litiengine.input.KeyboardEntityController;
import de.gurkenlabs.litiengine.physics.IMovementController;
import static de.gurkenlabs.litiengine.Align.CENTER;

/**
 * Represents the player character controlled by the user. The player is a {@link Creature}
 * that supports movement, animation control, visibility/invulnerability states, and
 * game-loop updates. This class follows a singleton design to ensure only one player exists.
 */
@EntityInfo(width = 16, height = 16)
@MovementInfo(velocity = 60)
@CollisionInfo(collisionBoxWidth = 12, collisionBoxHeight = 12, collision = true, align = CENTER, valign = Valign.MIDDLE_DOWN)
public class Player extends Creature implements IUpdateable {

    /**
     * Singleton static instance of player that game engine will call on.
     */
    private static Player instance;

    /**
     * The player's default movement speed, read from the {@link MovementInfo} annotation.
     */
    private static final float PLAYER_DEFAULT_VELOCITY = 60;

    /**
     * Flag indicating if the player is currently under the effect of the Invisibility power-up.
     */
    private boolean isInvisible = false;

    /**
     * Flag indicating if the player is currently holding an Alien Charm.
     */
    private boolean hasAlienCharm = false;

    /**
     * Flag indicating if the player is currently invulnerable (e.g., after using Alien Charm).
     */
    private boolean isInvulnerable = false;

    /**
     * Constructs a new Player with the default "player" spritesheet.
     * Protected to enforce singleton usage via {@link #getInstance()}.
     */
    protected Player() {
        super("player");
    }

    /**
     * Called every game tick by the engine. The Player currently has no
     * internal update logic, but implements {@link IUpdateable} for consistency.
     */
    @Override
    public void update() { }

    /**
     * Returns the singleton instance of the player, creating it if it does not yet exist.
     *
     * @return the global {@link Player} instance
     */
    public static Player getInstance() {
        if (instance == null) {
            instance = new Player();
        }
        return instance;
    }

    /**
     * Creates the player's animation controller responsible for managing sprite animations.
     *
     * @return a new {@link PlayerAnimationController}
     */
    @Override
    protected IEntityAnimationController<?> createAnimationController() {
        return new PlayerAnimationController(this, true);
    }

    /**
     * Creates the movement controller for the player, enabling keyboard-based movement.
     *
     * @return a {@link KeyboardEntityController} that handles directional input
     */
    @Override
    protected IMovementController createMovementController() {// overriding to define player's controller
        // setup movement controller
        return new KeyboardEntityController<>(this);
    }

    /**
     * Temporarily stops the player's movement by setting velocity to zero,
     * then restoring the default velocity after a timed delay.
     */
    public void stopMovement() {
        // Initial stop
        getVelocity().setBaseValue(0f);
        // Reset velocity after delay
        Game.loop().perform(1000, () -> getVelocity().setBaseValue(PLAYER_DEFAULT_VELOCITY));
    }

    /**
     * Gets the movement controller currently assigned to the player.
     *
     * @return the player's {@link IMovementController}
     */
    public IMovementController getMovementController() {
        return this.movement();
    }

    /**
     * Checks if the player is currently invisible.
     *
     * @return true if the Invisibility power-up is active, false otherwise.
     */
    public boolean isInvisible() {
        return this.isInvisible;
    }

    /**
     * Checks if the player is currently holding an Alien Charm.
     *
     * @return true if the player has an Alien Charm, false otherwise.
     */
    public boolean hasAlienCharm() {
        return this.hasAlienCharm;
    }

    /**
     * Checks if the player is currently invulnerable.
     *
     * @return true if the player is invulnerable, false otherwise.
     */
    public boolean isInvulnerable() {
        return this.isInvulnerable;
    }

    /**
     * Resets all active power-up effects on the player.
     * This is called by {@link ca.sfu.cmpt276.fall2025.team14.app.GameLogic}
     * when the level is restarted.
     */
    public void resetPowerUps() {
        // Reset velocity to default
        getVelocity().setBaseValue(PLAYER_DEFAULT_VELOCITY);

        // Reset visibility
        isInvisible = false;

        // Reset invulnerability
        isInvulnerable = false;

        // Reset charm
        hasAlienCharm = false;
    }

    /**
     * Returns the existing player instance without creating a new one.
     *
     * @return the current {@link Player} instance, or null if uninitialized
     */
    public static Player instance() {
        return instance;
    }

    /**
     * Returns the constant default player velocity.
     *
     * @return the default movement speed
     */
    public static float getPlayerDefaultVelocity() { return PLAYER_DEFAULT_VELOCITY; }

    /**
     * Sets whether the player is invisible.
     *
     * @param invisible true to make the player invisible
     */
    public void setInvisible(boolean invisible) { isInvisible = invisible; }

    /**
     * Sets whether the player currently has an Alien Charm.
     *
     * @param hasAlienCharm true if the player has the charm
     */
    public void setHasAlienCharm(boolean hasAlienCharm) { this.hasAlienCharm = hasAlienCharm; }

    /**
     * Sets whether the player is invulnerable.
     *
     * @param invulnerable true to make the player invulnerable
     */
    public void setInvulnerable(boolean invulnerable) { isInvulnerable = invulnerable; }
}

