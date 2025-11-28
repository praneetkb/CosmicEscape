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

    /**
     * Constructs a new Teleporter using the "teleporter" spritesheet
     * and enables collision for interaction.
     */
    public Teleporter() {
        super("teleporter");
        this.setCollision(true);
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

