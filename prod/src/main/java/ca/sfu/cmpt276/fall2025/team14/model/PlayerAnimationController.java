package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.graphics.animation.CreatureAnimationController;
import de.gurkenlabs.litiengine.util.Imaging;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PlayerAnimationController extends CreatureAnimationController<Player> {

    public PlayerAnimationController(Player player, boolean useFlippedSpritesAsFallback) {
        super(player, useFlippedSpritesAsFallback);
    }

    @Override
    public BufferedImage getCurrentImage() {
        BufferedImage image = super.getCurrentImage();
        Player p = this.getEntity();
        // Reduce alpha when invisible or invulnerable
        if (p.isInvisible() || p.isInvulnerable()) {
            image = Imaging.setAlpha(image, 0.5f);
        }
        // Green border for charm
        if (p.hasAlienCharm()) {
            image = Imaging.borderAlpha(image, Color.GREEN, false);
        }
        // Red border for jetpack
        if (p.getVelocity().get() > Player.getPlayerDefaultVelocity()) {
            image = Imaging.borderAlpha(image, Color.RED, false);
        }
        return image;
    }
}
