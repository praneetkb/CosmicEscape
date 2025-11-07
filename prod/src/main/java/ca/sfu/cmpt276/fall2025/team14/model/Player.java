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

import java.awt.*;

import static de.gurkenlabs.litiengine.Align.CENTER;

@EntityInfo(width = 16, height = 16)
@MovementInfo(velocity = 150)
@CollisionInfo(collisionBoxWidth = 12, collisionBoxHeight = 12, collision = true, align = CENTER, valign = Valign.MIDDLE)

public class Player extends Creature implements IUpdateable {

    private static Player instance;
    private static boolean hasCrystal;

    private IMovementController movementController;

    private boolean isInvisible = false;
    private boolean hasAlienCharm = false;

    // Get default velocity from the annotation
    private final float defaultVelocity = this.getVelocity().get();
    private final float jetpackVelocity = defaultVelocity * 1.5f; // 1.5x speed boost

    // Power-up durations (in milliseconds)
    private final int JETPACK_DURATION = 5000; // 5 seconds
    private final int INVISIBILITY_DURATION = 5000; // 5 seconds
    // Timestop duration is managed in GameLogic
    // Alien Charm is single-use

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

    public void blink() {
        this.setVisible(false);

        // hide player for 200 ms and show again. This is for the first and second enemy collision
        Game.loop().perform(200, () -> {
            // Only show if not invisible
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

    // --- NEW METHODS FOR POWER-UPS ---

    /**
     * Applies the effect of a collected power-up.
     * Called by GameLogic when a collision with a Powerup prop occurs.
     * @param type The type of power-up collected.
     */
    public void applyPowerUp(PowerUpType type) {
        if (type == null) return;

        switch (type) {
            case JETPACK:
                // Apply speed boost
                this.getVelocity().setBaseValue(this.jetpackVelocity);
                
                // Set timer to remove boost
                Game.loop().perform(JETPACK_DURATION, () -> {
                    // Check if another jetpack was picked up in the meantime
                    // Only reset to default if the current value is still the jetpack velocity
                    if (this.getVelocity().get() == this.jetpackVelocity) {
                        this.getVelocity().setBaseValue(this.defaultVelocity);
                    }
                });
                break;
            case INVISIBILITY:
                this.isInvisible = true;
                this.setVisible(false); // Make player model invisible

                // Set timer to become visible again
                Game.loop().perform(INVISIBILITY_DURATION, () -> {
                    this.isInvisible = false;
                    this.setVisible(true);
                });
                break;
            case TIMESTOP:
                // This is handled globally by GameLogic, but we could add a player visual cue here
                break;
            case ALIEN_CHARM:
                this.hasAlienCharm = true;
                // You could add a visual indicator to the HUD here
                break;
        }
    }

    /**
     * Checks if the player is currently invisible.
     * @return true if invisible, false otherwise.
     */
    public boolean isInvisible() {
        return this.isInvisible;
    }

    /**
     * Checks if the player is currently holding an Alien Charm.
     * @return true if holding a charm, false otherwise.
     */
    public boolean hasAlienCharm() {
        return this.hasAlienCharm;
    }

    /**
     * Consumes the Alien Charm. Called when the player is caught by an enemy.
     */
    public void useAlienCharm() {
        this.hasAlienCharm = false;
        // Add a visual cue, reuse blink to show the charm was used
        this.blink();
    }

    /**
     * Resets all active power-up states.
     * Called by GameLogic when the level restarts.
     */
    public void resetPowerUps() {
        // Reset velocity
        this.getVelocity().setBaseValue(this.defaultVelocity);
        
        // Reset visibility
        this.isInvisible = false;
        this.setVisible(true);

        // Reset charm
        this.hasAlienCharm = false;
        
        // Active timers in Game.loop() are cleared automatically when the environment is reloaded.
    }

}