package ca.sfu.cmpt276.fall2025.team14.model;

import ca.sfu.cmpt276.fall2025.team14.utils.VisionAttacher;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.Valign;
import de.gurkenlabs.litiengine.entities.*;

import static de.gurkenlabs.litiengine.Align.CENTER;

@EntityInfo(width = 16, height = 16)
@MovementInfo(velocity = 20)
@CollisionInfo(collisionBoxWidth = 14, collisionBoxHeight = 14, collision = true, align = CENTER, valign = Valign.MIDDLE)
public class Alien extends Creature implements IUpdateable {

    private Vision vision = new Vision();

    public Alien() {
        super("alien");
        // Attach vision
        VisionAttacher.attach(this, vision);
    }

    @Override
    public void update() {
        // Sync vision with current facing direction
        VisionAttacher.syncVision(this, vision);
    }
}