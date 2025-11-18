package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.Valign;
import de.gurkenlabs.litiengine.entities.*;
import de.gurkenlabs.litiengine.graphics.animation.IEntityAnimationController;

import static de.gurkenlabs.litiengine.Align.CENTER;

@EntityInfo(width = 16, height = 32)
@AnimationInfo(spritePrefix = "prop-vision")
@CollisionInfo(collisionBoxWidth = 14, collisionBoxHeight = 30, collision = false, align = CENTER, valign = Valign.MIDDLE)
public class Vision extends Prop implements IUpdateable {

    private static final double VISION_DEFAULT_WIDTH = 16;
    private static final double VISION_DEFAULT_HEIGHT = 32;

    private Enemy attachedEnemy;

    public Vision() {
        super("vision");
        this.setCollision(false);
    }

    @Override
    public void update() { }

    @Override
    protected IEntityAnimationController<?> createAnimationController() {
        return new VisionAnimationController(this);
    }

    public static double getDefaultWidth() { return VISION_DEFAULT_WIDTH; }
    public static double getDefaultHeight() { return VISION_DEFAULT_HEIGHT; }
    public Enemy getAttachedEnemy() { return this.attachedEnemy; }
    public void setAttachedEnemy(Enemy enemy) { this.attachedEnemy = enemy; }
}
