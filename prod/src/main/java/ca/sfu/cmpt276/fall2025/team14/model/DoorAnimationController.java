package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.graphics.animation.PropAnimationController;
import de.gurkenlabs.litiengine.resources.Resources;
import java.awt.image.BufferedImage;

/**
 * Animation controller for {@link Door} entities.
 * Handles door animation frames, switching among closed, opening, and open states
 * based on the linked button's pressed state.
 */
public class DoorAnimationController extends PropAnimationController<Door> {

    private final int frameCount = 12;
    private int tick = 0;
    private int index = 0;

    /**
     * Constructs a DoorAnimationController for the specified door entity.
     *
     * @param prop the door whose animations are controlled by this instance
     */
    public DoorAnimationController(Door prop) {
        super(prop);
    }

    /**
     * Returns the appropriate image for the door based on its current state.
     * The controller animates the door opening frame by frame when its button is pressed,
     * and switches to a fully open sprite once the final frame is reached.
     *
     * @return the current sprite image for the door
     */
    @Override
    public BufferedImage getCurrentImage() {
        BufferedImage image = null;
        Door d = this.getEntity();

        // default to closed
        if(d.getButton() == null) {
            return image = Resources.spritesheets().get("prop-door_closed").getImage();
        }

        // Set sprite based on state
        if (!d.getButton().pressed()) {
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
