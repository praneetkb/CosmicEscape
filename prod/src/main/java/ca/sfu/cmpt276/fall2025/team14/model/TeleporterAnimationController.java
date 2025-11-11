package ca.sfu.cmpt276.fall2025.team14.model;

import ca.sfu.cmpt276.fall2025.team14.app.GameLogic;
import de.gurkenlabs.litiengine.graphics.animation.PropAnimationController;
import de.gurkenlabs.litiengine.resources.Resources;

import java.awt.image.BufferedImage;

public class TeleporterAnimationController extends PropAnimationController<Teleporter> {

    public TeleporterAnimationController(Teleporter prop) {

        super(prop);
    }

    @Override
    public BufferedImage getCurrentImage() {
        BufferedImage image = null;
        if(!(GameLogic.getRemainingCrystals() == 0)) {
            image = Resources.spritesheets().get("prop-teleporter").getSprite(0);
        } else {
            image = Resources.spritesheets().get("prop-teleporter").getSprite(1);
        }
        return image;
    }
}
