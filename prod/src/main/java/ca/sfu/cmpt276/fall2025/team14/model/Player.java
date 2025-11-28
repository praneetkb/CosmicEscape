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

@EntityInfo(width = 16, height = 16)
@MovementInfo(velocity = 60)
@CollisionInfo(collisionBoxWidth = 12, collisionBoxHeight = 12, collision = true, align = CENTER, valign = Valign.MIDDLE_DOWN)

public class Player extends Creature implements IUpdateable {

    /**
     * Singleton static instance of player that game engine will call on
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

    protected Player() {
        super("player");
    }

    @Override
    public void update() { }

    public static Player getInstance() {
        if (instance == null) {
            instance = new Player();
        }
        return instance;
    }

    @Override
    protected IEntityAnimationController<?> createAnimationController() {
        return new PlayerAnimationController(this, true);
    }

    @Override
    protected IMovementController createMovementController() {// overriding to define player's controller
        // setup movement controller
        return new KeyboardEntityController<>(this);
    }

    public void stopMovement() {
        // Initial stop
        getVelocity().setBaseValue(0f);
        // Reset velocity after delay
        Game.loop().perform(1000, () -> getVelocity().setBaseValue(PLAYER_DEFAULT_VELOCITY));
    }

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
     * Checks if the player is currently invulnerable. // <-- ADDED
     *
     * @return true if the player is invulnerable, false otherwise.
     */
    public boolean isInvulnerable() { // <-- ADDED
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

    public static Player instance() {
        return instance;
    }

    public static float getPlayerDefaultVelocity() { return PLAYER_DEFAULT_VELOCITY; }

    public void setInvisible(boolean invisible) { isInvisible = invisible; }

    public void setHasAlienCharm(boolean hasAlienCharm) { this.hasAlienCharm = hasAlienCharm; }

    public void setInvulnerable(boolean invulnerable) { isInvulnerable = invulnerable; }
}