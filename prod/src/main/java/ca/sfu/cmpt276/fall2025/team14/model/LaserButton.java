package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.Valign;
import de.gurkenlabs.litiengine.entities.AnimationInfo;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import static de.gurkenlabs.litiengine.Align.CENTER;

/**
 * A specialized {@link Button} used for laser mechanisms.  
 * Inherits all interaction behavior from the base {@link Button} class
 * while providing its own sprite and collision configuration.
 */
@AnimationInfo(spritePrefix = "prop-button_laser_unpressed")
@CollisionInfo(collision = true, collisionBoxWidth = 14, collisionBoxHeight = 14, align = CENTER, valign = Valign.MIDDLE)
public class LaserButton extends Button{

    /**
     * Constructs a new LaserButton using the laser button sprite.
     */
    public LaserButton() {
        super("prop-button_laser_unpressed");
    }

    /**
     * Updates the laser button each tick by delegating to the superclass.
     */
    @Override
    public void update() {
        super.update();
    }

    /**
     * Presses the laser button and triggers the base button logic.
     */
    @Override
    public void pressButton() {
        super.pressButton();
    }

    /**
     * Returns whether the laser button is currently pressed.
     *
     * @return true if the button is pressed, false otherwise
     */
    @Override
    public boolean isPressed() {
        return super.isPressed();
    }
}

