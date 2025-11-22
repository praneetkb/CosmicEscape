package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.AnimationInfo;
import de.gurkenlabs.litiengine.entities.Prop;

@AnimationInfo(spritePrefix = "prop-crystal")

public class Crystal extends Prop implements IUpdateable {

    // initial state - added for unit test
    private boolean collected = false;

    public Crystal() {
        super("crystal");
    }

    // added for unit test
    public boolean isCollected() {
        return collected;
    }

    // added for unit test
    public void collect() {
        if (!collected) {
            collected = true;
            this.setCollision(false);    // player can walk through it now
            this.setVisible(false);      // hide crystal
        }
    }

    @Override
    public void update() { }
}
