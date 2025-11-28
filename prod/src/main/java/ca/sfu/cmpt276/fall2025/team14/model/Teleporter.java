package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.AnimationInfo;
import de.gurkenlabs.litiengine.entities.Prop;
import de.gurkenlabs.litiengine.graphics.animation.IEntityAnimationController;

/**
 * Represents a teleporter prop in the environment.
 * Teleporters are collidable and use a custom animation controller.
 */
@AnimationInfo(spritePrefix = "prop-teleporter")
public class Teleporter extends Prop implements IUpdateable {

    // initial state closed - added for unit test
    private boolean isOpen = false;

    /**
     * Constructs a new Teleporter using the "teleporter" spritesheet
     * and enables collision for interaction.
     */
    public Teleporter() {
        super("teleporter");
        this.setCollision(true); // initially closed so has collision
    }

    // added for unit test
    // true if the teleporter is open, false otherwise
    public boolean isOpen() {
        return this.isOpen;
    }

    // conditions to open teleporter - added for unit test
    public void tryOpen(int remainingCrystals, int timeRemaining) {
        if (remainingCrystals == 0 && timeRemaining > 0) {
            openTeleporter();
        }
    }

    // logic for teleporter - added for unit test
    private void openTeleporter() {
        this.isOpen = true;
        this.setCollision(false);  // allows player to enter now
    }

    /**
     * Called every update tick.
     * Teleporters have no per-frame internal logic.
     */
    @Override
    public void update() { }

    /**
     * Creates the animation controller that manages teleporter animations.
     *
     * @return a new {@link TeleporterAnimationController} instance
     */
    @Override
    protected IEntityAnimationController<?> createAnimationController() {
        return new TeleporterAnimationController(this);
    }
}
