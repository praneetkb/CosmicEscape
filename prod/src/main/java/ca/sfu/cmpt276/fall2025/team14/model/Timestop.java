package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.entities.AnimationInfo;

/**
 * Represents the Timestop power-up, which freezes all enemies.
 * Uses the "prop-stopwatch" sprite.
 * <p>
 * This class is automatically loaded by the {@link de.gurkenlabs.litiengine.environment.PropMapObjectLoader}
 * when a Prop with the type "timestop" is found in the Tiled map.
 * </p>
 */
@AnimationInfo(spritePrefix = "prop-stopwatch")
public class Timestop extends Powerup {

    /**
     * Default constructor.
     * This is required by the {@link de.gurkenlabs.litiengine.environment.PropMapObjectLoader}
     * to create an instance of this class.
     */
    public Timestop() {
        super("prop-stopwatch");
    }

    /**
     * {@inheritDoc}
     *
     * @return {@link PowerUpType#TIMESTOP}
     */
    @Override
    public PowerUpType getType() {
        return PowerUpType.TIMESTOP;
    }
}