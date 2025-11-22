package ca.sfu.cmpt276.fall2025.team14.model;

import ca.sfu.cmpt276.fall2025.team14.utils.VisionAttacher;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.Valign;
import de.gurkenlabs.litiengine.entities.*;
import de.gurkenlabs.litiengine.physics.Collision;
import java.awt.geom.Rectangle2D;
import static de.gurkenlabs.litiengine.Align.CENTER;

@EntityInfo(width = 16, height = 16)
@MovementInfo(velocity = 20)
@CollisionInfo(collisionBoxWidth = 12, collisionBoxHeight = 12, collision = true, align = CENTER, valign = Valign.MIDDLE)
public class Alien extends Enemy {

    private final static float ALIEN_DEFAULT_VELOCITY = 20;
    private final Vision vision;

    public Alien() {
        super("alien");
        // Attach vision
        vision = new Vision();
        Game.world().environment().add(vision);
        VisionAttacher.attach(this, vision);
        VisionAttacher.syncAlienVision(this, vision);
    }

    @Override
    public boolean canCollideWith(ICollisionEntity otherEntity) {
        // Can only collide with player
        if (!(otherEntity instanceof Player)) return false;
        return otherEntity.getCollisionType() == Collision.STATIC || super.canCollideWith(otherEntity);
    }

    @Override
    public void update() {
        // Sync vision with current facing direction
        VisionAttacher.syncAlienVision(this, vision);
    }

    @Override
    public boolean playerInLos() {
        Rectangle2D playerCB = Player.instance().getCollisionBox();
        return playerCB.intersects(vision.getBoundingBox());
    }

    public Vision getVision() {
        return vision;
    }

    public static float getDefaultVelocity() {

        return ALIEN_DEFAULT_VELOCITY;
    }
}