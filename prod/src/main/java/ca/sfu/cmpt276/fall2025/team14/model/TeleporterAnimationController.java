package ca.sfu.cmpt276.fall2025.team14.model;

import ca.sfu.cmpt276.fall2025.team14.app.GameLogic;
import de.gurkenlabs.litiengine.graphics.animation.PropAnimationController;
import de.gurkenlabs.litiengine.resources.Resources;
import java.awt.image.BufferedImage;

/**
 * Animation controller for {@link Teleporter} entities.  
 * Selects between an open and closed sprite depending on the number of
 * remaining crystals in the game logic.
 */
public class TeleporterAnimationController extends PropAnimationController<Teleporter> {

    /**
     * Constructs a TeleporterAnimationController for the given teleporter prop.
     *
     * @param prop the teleporter entity whose animation controller is being created
     */
    public TeleporterAnimationController(Teleporter prop) {
        super(prop);
    }

    /**
     * Returns the current sprite image for the teleporter.  
     * The teleporter appears closed while crystals remain, and open once all
     * required crystals have been collected.
     *
     * @return the appropriate teleporter sprite based on game state
     */
    @Override
    public BufferedImage getCurrentImage() {
        BufferedImage image = null;
        BufferedImage closed = Resources.spritesheets().get("prop-teleporter").getSprite(0);
        BufferedImage open = Resources.spritesheets().get("prop-teleporter").getSprite(1);
        image = GameLogic.getRemainingCrystals() != 0 ? closed : open;
        return image;
    }
}

