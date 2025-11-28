package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.Valign;
import de.gurkenlabs.litiengine.entities.*;
import de.gurkenlabs.litiengine.graphics.animation.IEntityAnimationController;

import static de.gurkenlabs.litiengine.Align.CENTER;

/**
 * Represents a radiation-based punishment that uses a shared static countdown
 * timer across all radiation tiles. The countdown ticks globally, ensuring that
 * walking across multiple radiation tiles does not reset the timer.
 */
@EntityInfo(width = 16, height = 32)
@AnimationInfo(spritePrefix = "prop-vision")
@CollisionInfo(collisionBoxWidth = 14, collisionBoxHeight = 30, collision = false, align = CENTER, valign = Valign.MIDDLE)
public class Vision extends Prop implements IUpdateable {

    private static final double VISION_DEFAULT_WIDTH = 16;
    private static final double VISION_DEFAULT_HEIGHT = 32;

    private Enemy attachedEnemy;

    /**
     * Creates a new Vision prop with its default sprite and disabled collision.
     */
    public Vision() {
        super("vision");
        this.setCollision(false);
    }

    /**
     * Called once per update tick. Vision does not implement its own update
     * behavior, but this method must be present due to {@link IUpdateable}.
     */
    @Override
    public void update() { }

    /**
     * Creates the animation controller responsible for handling all animations
     * associated with this vision prop.
     *
     * @return a new {@link VisionAnimationController} for this vision object
     */
    @Override
    protected IEntityAnimationController<?> createAnimationController() {
        return new VisionAnimationController(this);
    }

    /**
     * Returns the default width of the vision prop before scaling or rotation.
     *
     * @return the default vision width
     */
    public static double getDefaultWidth() { return VISION_DEFAULT_WIDTH; }

    /**
     * Returns the default height of the vision prop before scaling or rotation.
     *
     * @return the default vision height
     */
    public static double getDefaultHeight() { return VISION_DEFAULT_HEIGHT; }

    /**
     * Returns the enemy currently associated with this vision instance.
     *
     * @return the attached enemy, or null if none is set
     */
    public Enemy getAttachedEnemy() { return this.attachedEnemy; }

    /**
     * Associates this vision with the specified enemy.
     *
     * @param enemy the enemy to attach to this vision object
     */
    public void setAttachedEnemy(Enemy enemy) { this.attachedEnemy = enemy; }
}

