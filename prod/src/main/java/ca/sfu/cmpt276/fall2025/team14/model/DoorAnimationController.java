package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.graphics.animation.PropAnimationController;
import de.gurkenlabs.litiengine.resources.Resources;

import java.awt.image.BufferedImage;

public class DoorAnimationController extends PropAnimationController<Door> {

    private final int frameCount = 12;
    private int tick = 0;
    private int index = 0;

    public DoorAnimationController(Door prop) {
        super(prop);
    }

    @Override
    public BufferedImage getCurrentImage() {
        BufferedImage image = null;
        Door d = this.getEntity();
        // default to closed
        if(d.getButton() == null) {
            return image = Resources.spritesheets().get("prop-door_closed").getImage();
        }
        // Set sprite based on state
        if (!d.getButton().isPressed()) {
            // Set animation to closed
            image = Resources.spritesheets().get("prop-door_closed").getImage();
        } else {
            // Get each individual frame in animation and return
            if (tick % frameCount == 0) {
                // Increment index of sprite sheet every 12 frames
                index++;
            }
            image = Resources.spritesheets().get("prop-door").getSprite(index);
            tick++;
        }
        // Final open state
        if (index >= 12) {
            image = Resources.spritesheets().get("prop-door_open").getImage();
            d.setCollision(false);
        }
        return image;
    }
}
