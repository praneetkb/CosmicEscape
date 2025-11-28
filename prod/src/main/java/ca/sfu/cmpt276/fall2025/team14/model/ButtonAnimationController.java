package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.graphics.animation.PropAnimationController;
import de.gurkenlabs.litiengine.resources.Resources;
import java.awt.image.BufferedImage;

/**
 * Animation controller for {@link Button} entities.  
 * Determines which sprite to display based on whether the button is pressed
 * and whether it is a {@link DoorButton} or another type of button.
 */
public class ButtonAnimationController extends PropAnimationController<Button> {

    /**
     * Creates a new animation controller for the specified button.
     *
     * @param prop the button whose animation controller is being created
     */
    public ButtonAnimationController(Button prop) { super(prop); }

    /**
     * Determines the current sprite image for the button.  
     * Selects from door-button or laser-button sprite sheets depending on the
     * button type, and chooses between pressed or unpressed variants.
     *
     * @return the current button sprite image
     */
    @Override
    public BufferedImage getCurrentImage() {
        BufferedImage image = null;
        Button b = this.getEntity();
        // Get door or laser button sprite
        String prefix = "prop-";
        String extension = (this.getEntity().getClass() == DoorButton.class) ? "button_door" : "button_laser";
        String pressed = prefix+extension+"_pressed.png";
        String unpressed = prefix+extension+"_unpressed.png";
        // Set image to pressed/unpressed
        BufferedImage pressedImg = Resources.spritesheets().get(pressed).getImage();
        BufferedImage unpressedImg = Resources.spritesheets().get(unpressed).getImage();
        image = (b.pressed() ?  pressedImg : unpressedImg);
        return image;
    }
}

