package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.Valign;
import de.gurkenlabs.litiengine.entities.AnimationInfo;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import static de.gurkenlabs.litiengine.Align.CENTER;

/**
 * Represents a radiation-based punishment that uses a shared static countdown
 * timer across all radiation tiles. The countdown ticks globally, ensuring that
 * walking across multiple radiation tiles does not reset the timer.
 */
@AnimationInfo(spritePrefix = "prop-button_door_unpressed")
@CollisionInfo(collision = true, collisionBoxWidth = 20, collisionBoxHeight = 20, align = CENTER, valign = Valign.MIDDLE)
public class DoorButton extends Button{

    /**
     * Creates a new DoorButton using the base "button" spritesheet.
     */
    public DoorButton() {
        super("button");
    }

    /**
     * Updates the door button each tick by delegating to the superclass implementation.
     */
    @Override
    public void update() {
        super.update();
    }

    /**
     * Handles button press behavior for door buttons, using the standard
     * {@link Button#pressButton()} logic.
     */
    @Override
    public void pressButton() {
        super.pressButton();
    }

    /**
     * Returns whether the button is currently pressed by delegating to the base Button logic.
     *
     * @return true if the button is pressed, false otherwise
     */
    @Override
    public boolean isPressed() {
        return super.isPressed();
    }
}
