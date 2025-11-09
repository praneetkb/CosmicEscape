package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.Valign;
import de.gurkenlabs.litiengine.entities.*;
import static de.gurkenlabs.litiengine.Align.CENTER;

@EntityInfo(width = 16, height = 32)
@AnimationInfo(spritePrefix = "vision")
@CollisionInfo(collisionBoxWidth = 14, collisionBoxHeight = 30, collision = true, align = CENTER, valign = Valign.MIDDLE)
public class Vision extends Prop implements IUpdateable {

    public static final double DEFAULT_WIDTH = 16;
    public static final double DEFAULT_HEIGHT = 32;

    public Vision() {
        super("vision");
    }

    @Override
    public void update() {

    }
}
