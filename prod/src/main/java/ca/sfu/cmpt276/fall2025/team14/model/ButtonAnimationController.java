package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.graphics.animation.PropAnimationController;
import de.gurkenlabs.litiengine.resources.Resources;
import java.awt.image.BufferedImage;

public class ButtonAnimationController extends PropAnimationController<Button> {

    public ButtonAnimationController(Button prop) { super(prop); }

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
