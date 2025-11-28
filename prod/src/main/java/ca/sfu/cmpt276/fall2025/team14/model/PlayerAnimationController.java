package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.graphics.animation.CreatureAnimationController;
import de.gurkenlabs.litiengine.util.Imaging;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Animation controller for the {@link Player}.  
 * Applies visual effects such as transparency and colored borders based on
 * the player's current status (invisibility, invulnerability, charm, jetpack).
 */
public class PlayerAnimationController extends CreatureAnimationController<Player> {

    /**
     * Creates a new PlayerAnimationController.
     *
     * @param player the player entity this controller manages
     * @param useFlippedSpritesAsFallback whether flipped sprites should be used if others are missing
     */
    public PlayerAnimationController(Player player, boolean useFlippedSpritesAsFallback) {
        super(player, useFlippedSpritesAsFallback);
    }

    /**
     * Retrieves the current animation frame for the player, applying
     * additional image processing based on active status effects:
     * <ul>
     *   <li>Reduced alpha when invisible or invulnerable</li>
     *   <li>Green border when holding an alien charm</li>
     *   <li>Red border when using a jetpack (velocity boosted)</li>
     * </ul>
     *
     * @return the processed sprite image for the current frame
     */
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

