package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.Valign;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.EntityInfo;
import de.gurkenlabs.litiengine.entities.MovementInfo;
import de.gurkenlabs.litiengine.input.KeyboardEntityController;
import de.gurkenlabs.litiengine.physics.IMovementController;
import static de.gurkenlabs.litiengine.Align.CENTER;

@EntityInfo(width = 16, height = 16)
@MovementInfo(velocity = 150)
@CollisionInfo(collisionBoxWidth = 12, collisionBoxHeight = 12, collision = true, align = CENTER, valign = Valign.MIDDLE_DOWN)
public class Player extends Creature implements IUpdateable {

    private static Player instance;
    private static boolean hasCrystal;

    private IMovementController movementController;

    /**
     * Flag indicating if the player is currently under the effect of the Invisibility power-up.
     */
    private boolean isInvisible = false;

    /**
     * Flag indicating if the player is currently holding an Alien Charm.
     */
    private boolean hasAlienCharm = false;
    
    /**
     * Flag indicating if the player is currently invulnerable (e.g., after using Alien Charm). // <-- ADDED
     */
    private boolean isInvulnerable = false; // <-- ADDED

    /**
     * The player's default movement speed, read from the {@link MovementInfo} annotation.
     */
    private final float defaultVelocity = this.getVelocity().get();

    /**
     * The increased movement speed when the Jetpack power-up is active.
     */
    private final float jetpackVelocity = defaultVelocity * 1.5f; // 1.5x speed boost

    /**
     * Duration for the Jetpack power-up in milliseconds.
     */
    private final int JETPACK_DURATION = 10000; // 10 seconds

    /**
     * Duration for the Invisibility power-up in milliseconds.
     */
    private final int INVISIBILITY_DURATION = 5000; // 5 seconds
    
    /**
     * Duration for the temporary invulnerability after using Alien Charm in milliseconds. // <-- ADDED
     */
    private final int CHARM_INVULNERABILITY_DURATION = 2000; //make the play invurnerable for 2 seconds after consuming Alien Charm


    public static Player instance() {
        if (instance == null) {
            instance = new Player();
        }
        return instance;
    }

    private Player() {
        super("player");
    }

    public void stopMovement() {

//        // initial stop
//        this.setVelocity(0);
//
//        //reset velocity after delay
//        Game.loop().perform(500, () -> {
//            this.setVelocity(70);
//        });

    }

    /**
     * Makes the player model "blink" (disappear and reappear).
     * This is an existing method modified to support power-ups.
     */
    public void blink() {
        this.setVisible(false);

        // hide player for 200 ms and show again. This is for the first and second enemy collision
        Game.loop().perform(200, () -> {
            // Only make the player visible again if they are not under the
            // effect of the Invisibility power-up.
            if (!this.isInvisible) {
                this.setVisible(true);
            }
        });
    }

    @Override
    public void update() {
    }

    @Override
    protected IMovementController createMovementController() { // overriding to define player's controller
        // setup movement controller
        this.movementController = new KeyboardEntityController<>(this);
        return this.movementController;
    }

    public IMovementController getMovementController() { // for other classes (like GameLogic) to access
        return this.movementController;
    }

    /**
     * Applies the effect of a collected power-up to the player.
     * This method is called by {@link ca.sfu.cmpt276.fall2025.team14.app.GameLogic}
     * when a player-powerup collision is detected.
     *
     * @param type The {@link PowerUpType} of the collected power-up.
     */
    public void applyPowerUp(PowerUpType type) {
        if (type == null) return;

        switch (type) {
            case JETPACK:
                // Apply speed boost
                this.getVelocity().setBaseValue(this.jetpackVelocity);

                // Set a timer to remove the speed boost after the duration
                Game.loop().perform(JETPACK_DURATION, () -> {
                    // Check if another jetpack was picked up in the meantime.
                    // Only reset to default if the current velocity is still the jetpack velocity.
                    if (this.getVelocity().get() == this.jetpackVelocity) {
                        this.getVelocity().setBaseValue(this.defaultVelocity);
                    }
                });
                break;
            case INVISIBILITY:
                this.isInvisible = true;
                this.setVisible(false); // Make player model invisible

                // Set a timer to become visible again
                Game.loop().perform(INVISIBILITY_DURATION, () -> {
                    this.isInvisible = false;
                    this.setVisible(true);
                });
                break;
            case TIMESTOP:
                // This power-up is handled globally by GameLogic,
                // as it affects all enemies, not just the player.
                break;
            case ALIEN_CHARM:
                this.hasAlienCharm = true;
                // A visual indicator could be added to the HUD here
                break;
        }
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
     * Consumes the player's Alien Charm.
     * This is called by {@link ca.sfu.cmpt276.fall2025.team14.app.GameLogic}
     * when the player is caught by an enemy but has a charm.
     */
    public void useAlienCharm() {
        this.hasAlienCharm = false;
        
        // Apply brief invulnerability to prevent immediate double-kill // <-- ADDED
        this.isInvulnerable = true; // <-- ADDED
        Game.loop().perform(CHARM_INVULNERABILITY_DURATION, () -> { // <-- ADDED
            this.isInvulnerable = false; // <-- ADDED
        }); // <-- ADDED

        // Use the blink effect as a visual cue that the charm was used
        this.blink();
    }

    /**
     * Resets all active power-up effects on the player.
     * This is called by {@link ca.sfu.cmpt276.fall2025.team14.app.GameLogic}
     * when the level is restarted.
     */
    public void resetPowerUps() {
        // Reset velocity to default
        this.getVelocity().setBaseValue(this.defaultVelocity);

        // Reset visibility
        this.isInvisible = false;
        this.setVisible(true);

        // Reset invulnerability // <-- ADDED
        this.isInvulnerable = false; // <-- ADDED

        // Reset charm
        this.hasAlienCharm = false;

        // Note: All active timers in Game.loop() are cleared automatically
        // when the environment is reloaded, so we don't need to manually cancel them.
    }

}