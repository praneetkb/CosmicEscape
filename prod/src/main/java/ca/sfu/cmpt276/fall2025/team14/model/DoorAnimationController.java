package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.Spritesheet;
import de.gurkenlabs.litiengine.graphics.animation.Animation;
import de.gurkenlabs.litiengine.graphics.animation.IEntityAnimationController;
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
        // Set sprite based on state
        if (this.getEntity().hasCollision()) {
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
            this.getEntity().setCollision(false);
        }
        return image;
    }
}
