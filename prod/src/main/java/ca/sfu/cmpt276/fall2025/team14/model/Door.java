package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.AnimationInfo;
import de.gurkenlabs.litiengine.entities.Prop;
import de.gurkenlabs.litiengine.graphics.animation.IEntityAnimationController;

@AnimationInfo(spritePrefix = "prop-door")
public class Door extends Prop implements IUpdateable {

    public Door() {

        super("door");
    }

    @Override
    public void update() {

    }

    // Custom animation controller to handle frame by frame animations for open, opening, closed states
    @Override
    protected IEntityAnimationController<?> createAnimationController() {
        return new DoorAnimationController(this);
    }
}
