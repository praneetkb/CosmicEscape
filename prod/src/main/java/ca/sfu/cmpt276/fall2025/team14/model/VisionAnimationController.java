package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.animation.PropAnimationController;
import de.gurkenlabs.litiengine.resources.Resources;
import de.gurkenlabs.litiengine.util.Imaging;
import java.awt.image.BufferedImage;

public class VisionAnimationController extends PropAnimationController<Vision> {

    Turret enemy;

    public VisionAnimationController(Vision prop) {
        super(prop);
        Game.loop().attach(this);
    }

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

    @Override
    public void update() {
        if (getEntity().animations().getCurrent() == null) {
            getEntity().animations().play("intact");
        }
    }
}
