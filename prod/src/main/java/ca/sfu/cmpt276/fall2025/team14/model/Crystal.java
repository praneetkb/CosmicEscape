package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.AnimationInfo;
import de.gurkenlabs.litiengine.entities.Prop;

/**
 * A static crystal prop with no interactive behavior.
 */
@AnimationInfo(spritePrefix = "prop-crystal")
public class Crystal extends Prop implements IUpdateable {

    /**
     * Creates a new crystal prop.
     */
    public Crystal() {
        super("crystal");
    }

    /**
     * Called each frame; currently performs no actions.
     */
    @Override
    public void update() { }
}

