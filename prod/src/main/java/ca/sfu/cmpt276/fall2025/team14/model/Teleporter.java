package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.AnimationInfo;
import de.gurkenlabs.litiengine.entities.Prop;
import de.gurkenlabs.litiengine.graphics.animation.IEntityAnimationController;

@AnimationInfo(spritePrefix = "prop-teleporter")
public class Teleporter extends Prop implements IUpdateable {

    public Teleporter() {
        super("teleporter");
        this.setCollision(true);
    }

    @Override
    public void update() {
    }

    @Override
    protected IEntityAnimationController<?> createAnimationController() {
        return new TeleporterAnimationController(this);
    }
}
