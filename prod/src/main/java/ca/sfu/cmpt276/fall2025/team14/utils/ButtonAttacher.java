package ca.sfu.cmpt276.fall2025.team14.utils;

import ca.sfu.cmpt276.fall2025.team14.model.Button;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.Prop;

public class ButtonAttacher {

    public static Button attach(Prop entity) {
        // Get button type based on entity name set in utiLITI
        String name = "button-" + entity.getName();
        return (Button) Game.world().environment().getProp(name);
    }
}
