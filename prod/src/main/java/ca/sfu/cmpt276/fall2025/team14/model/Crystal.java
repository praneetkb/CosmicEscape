package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.AnimationInfo;
import de.gurkenlabs.litiengine.entities.Prop;

/**
 * A static crystal prop with no interactive behavior.
 */
@AnimationInfo(spritePrefix = "prop-crystal")
public class Crystal extends Prop implements IUpdateable {

    private boolean collected = false;

    /**
     * Creates a new crystal prop.
     */
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

    /**
     * Called each frame; currently performs no actions.
     */
    @Override
    public void update() { }
}
