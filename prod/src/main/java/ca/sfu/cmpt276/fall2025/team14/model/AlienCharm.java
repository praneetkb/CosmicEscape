package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.entities.AnimationInfo;

/**
 * Represents the Alien Charm power-up, a one-time save from being caught.
 * Uses the "prop-charm" sprite.
 * <p>
 * This class is automatically loaded by the {@link de.gurkenlabs.litiengine.environment.PropMapObjectLoader}
 * when a Prop with the type "aliencharm" is found in the Tiled map.
 * </p>
 */
@AnimationInfo(spritePrefix = "prop-charm")
public class AlienCharm extends Powerup {

    /**
     * Default constructor.
     * This is required by the {@link de.gurkenlabs.litiengine.environment.PropMapObjectLoader}
     * to create an instance of this class.
     */
    public AlienCharm() {
        super("prop-charm");
    }

    /**
     * {@inheritDoc}
     *
     * @return {@link PowerUpType#ALIEN_CHARM}
     */
    @Override
    public PowerUpType getType() {
        return PowerUpType.ALIEN_CHARM;
    }
}