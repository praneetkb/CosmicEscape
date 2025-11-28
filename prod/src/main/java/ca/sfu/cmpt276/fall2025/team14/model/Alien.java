package ca.sfu.cmpt276.fall2025.team14.model;

import ca.sfu.cmpt276.fall2025.team14.utils.VisionAttacher;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.Valign;
import de.gurkenlabs.litiengine.entities.*;
import de.gurkenlabs.litiengine.physics.Collision;
import java.awt.geom.Rectangle2D;
import static de.gurkenlabs.litiengine.Align.CENTER;

/**
 * Represents an Alien enemy in the game world.
 * Aliens include a vision component that tracks the player's presence
 * within their line of sight, and synchronize that vision with movement
 * and facing direction each frame.
 */
@EntityInfo(width = 16, height = 16)
@MovementInfo(velocity = 20)
@CollisionInfo(collisionBoxWidth = 12, collisionBoxHeight = 12, collision = true, align = CENTER, valign = Valign.MIDDLE)
public class Alien extends Enemy {

    private final static float ALIEN_DEFAULT_VELOCITY = 20;
    private final Vision vision;

    /**
     * Constructs a new Alien, initializes its vision component,
     * adds it to the environment, and synchronizes its positioning.
     */
    public Alien() {
        super("alien");
        // Attach vision
        vision = new Vision();
        Game.world().environment().add(vision);
        VisionAttacher.attach(this, vision);
        VisionAttacher.syncAlienVision(this, vision);
    }

    /**
     * Determines collision behavior for the alien.
     * Aliens may collide only with the player, ensuring that
     * other entity types are ignored.
     *
     * @param otherEntity the entity attempting to collide with the alien
     * @return true only if the other entity is the player and collision is valid
     */
    @Override
    public boolean canCollideWith(ICollisionEntity otherEntity) {
        // Can only collide with player
        if (!(otherEntity instanceof Player)) return false;
        return otherEntity.getCollisionType() == Collision.STATIC || super.canCollideWith(otherEntity);
    }

    /**
     * Updates the alien each frame.
     * Ensures the alien's vision remains synchronized with movement
     * and facing direction.
     */
    @Override
    public void update() {
        // Sync vision with current facing direction
        VisionAttacher.syncAlienVision(this, vision);
    }

    /**
     * Checks whether the player is inside the alien's line of sight.
     * This is determined by checking intersection between the player's
     * collision box and the alien’s vision bounding box.
     *
     * @return true if the player is within the alien's vision area
     */
    @Override
    public boolean playerInLos() {
        Rectangle2D playerCB = Player.instance().getCollisionBox();
        return playerCB.intersects(vision.getBoundingBox());
    }

    /**
     * Returns the default velocity for alien movement.
     *
     * @return the default alien velocity
     */
    public static float getDefaultVelocity() {

        return ALIEN_DEFAULT_VELOCITY;
    }

    public Vision getVision() {
        return vision;
    }
}