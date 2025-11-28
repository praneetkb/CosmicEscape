package ca.sfu.cmpt276.fall2025.team14.utils;

import ca.sfu.cmpt276.fall2025.team14.model.Button;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.Prop;

/**
 * Utility class responsible for attaching a {@link Button} to a given {@link Prop}
 * based on its configured name in utiLITI.
 */
public class ButtonAttacher {


    /**
     * Attaches and returns the {@link Button} associated with the provided {@link Prop}.
     * The method constructs the expected button name using the entity's name and retrieves
     * the corresponding prop from the current game environment.
     *
     * @param entity the prop for which the corresponding button should be retrieved
     * @return the associated {@link Button}, or null if the prop is not found or cannot be cast
     */
    public static Button attach(Prop entity) {
        // Get button type based on entity name set in utiLITI
        String name = "button-" + entity.getName();
        return (Button) Game.world().environment().getProp(name);
    }
}
