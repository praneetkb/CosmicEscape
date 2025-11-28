package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.animation.PropAnimationController;
import de.gurkenlabs.litiengine.resources.Resources;
import de.gurkenlabs.litiengine.util.Imaging;
import java.awt.image.BufferedImage;

/**
 * Animation controller for {@link Vision} props.  
 * Handles animation playback and dynamically rotates the vision sprite
 * when attached to a {@link Turret}, while defaulting to standard animations
 * for {@link Alien}-based vision.
 */
public class VisionAnimationController extends PropAnimationController<Vision> {

    Turret enemy;

    /**
     * Constructs a VisionAnimationController for the given vision prop and
     * attaches it to the game loop to receive update ticks.
     *
     * @param prop the vision entity whose animations this controller manages
     */
    public VisionAnimationController(Vision prop) {
        super(prop);
        Game.loop().attach(this);
    }

    /**
     * Retrieves the current rendered image for the vision prop.  
     * If attached to an {@link Alien}, standard animation behavior is used.
     * If attached to a {@link Turret}, the base sprite is rotated based on
     * the turret's current facing angle before rendering.
     *
     * @return the current frame image for the vision prop
     */
    @Override
    public BufferedImage getCurrentImage() {
        // Return if Alien
        if (getEntity().getAttachedEnemy() instanceof Alien) {
            return super.getCurrentImage();
        }
        // Set enemy to turret and rotate image
        if (getEntity().getAttachedEnemy() instanceof Turret && enemy == null) {
            enemy = (Turret) getEntity().getAttachedEnemy();
        }
        BufferedImage image = Resources.spritesheets().get("prop-vision").getImage();
        image = Imaging.rotate(image, Math.toRadians(enemy.getCurrentDegree()));
        return image;
    }

    /**
     * Ensures that a default animation ("intact") is always playing
     * if no animation is currently active for the vision prop.
     */
    @Override
    public void update() {
        if (getEntity().animations().getCurrent() == null) {
            getEntity().animations().play("intact");
        }
    }
}

