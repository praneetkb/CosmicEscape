package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.AnimationInfo;
import de.gurkenlabs.litiengine.entities.Prop;

@AnimationInfo(spritePrefix = "prop-crystal")
public class Crystal extends Prop implements IUpdateable {

    public Crystal() {
        super("crystal");
    }

    @Override
    public void update() { }
}
