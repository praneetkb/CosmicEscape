package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.Valign;
import de.gurkenlabs.litiengine.entities.AnimationInfo;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.entities.Prop;
import de.gurkenlabs.litiengine.graphics.Spritesheet;
import de.gurkenlabs.litiengine.graphics.animation.Animation;
import de.gurkenlabs.litiengine.graphics.animation.IEntityAnimationController;
import de.gurkenlabs.litiengine.graphics.animation.PropAnimationController;
import de.gurkenlabs.litiengine.resources.Resources;

import static de.gurkenlabs.litiengine.Align.CENTER;


@AnimationInfo(spritePrefix = "prop-door")
public class Door extends Prop implements IUpdateable {

    public Door() {

        super("door");
    }

    @Override
    public void update() {

    }

    @Override
    protected IEntityAnimationController<?> createAnimationController() {
        return new DoorAnimationController(this);
    }
}
