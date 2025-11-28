package ca.sfu.cmpt276.fall2025.team14.unit;

import ca.sfu.cmpt276.fall2025.team14.app.GameLogic;
import ca.sfu.cmpt276.fall2025.team14.model.Teleporter;
import ca.sfu.cmpt276.fall2025.team14.model.TeleporterAnimationController;

import de.gurkenlabs.litiengine.resources.Resources;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

public class TeleporterAnimationControllerTest {

    @Test
    public void testGetCurrentImageClosed() {
        Teleporter t = new Teleporter();
        TeleporterAnimationController controller = new TeleporterAnimationController(t);

        GameLogic.setRemainingCrystals(2);

        BufferedImage expected = Resources.spritesheets().get("prop-teleporter").getSprite(0);
        assertEquals(expected, controller.getCurrentImage());
    }

    @Test
    public void testGetCurrentImageOpen() {
        Teleporter t = new Teleporter();
        TeleporterAnimationController controller = new TeleporterAnimationController(t);

        GameLogic.setRemainingCrystals(0);

        BufferedImage expected = Resources.spritesheets().get("prop-teleporter").getSprite(1);
        assertEquals(expected, controller.getCurrentImage());
    }
}
