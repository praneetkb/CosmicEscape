package ca.sfu.cmpt276.fall2025.team14.model;

import ca.sfu.cmpt276.fall2025.team14.utils.VisionAttacher;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.Valign;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.EntityInfo;

import static de.gurkenlabs.litiengine.Align.CENTER;

@EntityInfo(width = 16, height = 16)
@CollisionInfo(collisionBoxWidth = 14, collisionBoxHeight = 14, collision = true, align = CENTER, valign = Valign.MIDDLE)
public class Turret extends Creature implements IUpdateable {

    private static Vision vision = new Vision();

    public Turret() {
        super("turret");
        // Attach vision
        VisionAttacher.attach(this, vision);
    }

    @Override
    public void update() {
        // Sync vision with current facing direction
        VisionAttacher.syncVision(this, vision);
    }
}
