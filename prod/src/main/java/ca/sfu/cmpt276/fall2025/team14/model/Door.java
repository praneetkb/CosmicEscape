package ca.sfu.cmpt276.fall2025.team14.model;

import ca.sfu.cmpt276.fall2025.team14.utils.ButtonAttacher;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.AnimationInfo;
import de.gurkenlabs.litiengine.entities.Prop;
import de.gurkenlabs.litiengine.graphics.animation.IEntityAnimationController;

/**
 * Represents a radiation-based punishment that uses a shared static countdown
 * timer across all radiation tiles. The countdown ticks globally, ensuring that
 * walking across multiple radiation tiles does not reset the timer.
 */
@AnimationInfo(spritePrefix = "prop-door")
public class Door extends Prop implements IUpdateable {

    private boolean isOpen = false;
    private Button button;

    /**
     * Constructs a new Door with collision enabled by default so that it blocks the player.
     */
    public Door() {
        super("door");
        this.setCollision(true); // door blocks player initially
    }

    /**
     * Updates the door each tick.
     * Automatically attaches the corresponding button when first updated.
     */
    @Override
    public void update() {
        // Attaching button in update
        if (button == null) {
            button = ButtonAttacher.attach(this);
        }
    }

    /**
     * Creates the animation controller that manages the door's animations,
     * including open, opening, and closed states.
     *
     * @return the custom {@link DoorAnimationController}
     */
    @Override
    protected IEntityAnimationController<?> createAnimationController() {
        // Custom animation controller to handle frame by frame animations for open, opening, closed states
        return new DoorAnimationController(this);
    }

    /**
     * Returns whether the door is currently open.
     *
     * @return true if open, false if closed
     */
    public boolean isOpen() {
        return isOpen;
    }

    /**
     * Opens the door if it is currently closed and disables collision.
     */
    public void open() {
        if (!isOpen) {
            isOpen = true;
            this.setCollision(false); // allow player to pass
        }
    }

    /**
     * Closes the door if it is currently open and enables collision.
     */
    public void close() {
        if (isOpen) {
            isOpen = false;
            this.setCollision(true); // block player
        }
    }

    /**
     * Returns the button associated with this door, if any.
     *
     * @return the linked {@link Button}, or null if not yet attached
     */
    public Button getButton() { return button; }

    public void setButton(Button b) {
        this.button = b;
    }
}
