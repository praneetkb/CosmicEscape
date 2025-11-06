package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.Valign;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.EntityInfo;
import de.gurkenlabs.litiengine.entities.MovementInfo;

import static de.gurkenlabs.litiengine.Align.CENTER;

@EntityInfo(width = 16, height = 16)
@CollisionInfo(collisionBoxWidth = 16, collisionBoxHeight = 16, collision = true, align = CENTER, valign = Valign.MIDDLE)
public class Turret extends Creature implements IUpdateable {

    public Turret() {

        super("turret");
    }

    @Override
    public void update() {

    }
}
