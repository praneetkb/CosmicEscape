package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.graphics.animation.PropAnimationController;
import de.gurkenlabs.litiengine.resources.Resources;

import java.awt.image.BufferedImage;

public class ButtonAnimationController extends PropAnimationController<Button> {

    public ButtonAnimationController(Button prop) { super(prop); }

    @Override
    public BufferedImage getCurrentImage() {
        BufferedImage image = null;
        String prefix = "prop-";
        String extension = (this.getEntity().getClass() == DoorButton.class) ? "button_door" : "button_laser";
        String pressed = prefix+extension+"_pressed.png";
        String unpressed = prefix+extension+"_unpressed.png";

        if (this.getEntity().isPressed()) {
            image = Resources.spritesheets().get(pressed).getImage();
        } else {
            image = Resources.spritesheets().get(unpressed).getImage();
        }
        return image;
    }


}
