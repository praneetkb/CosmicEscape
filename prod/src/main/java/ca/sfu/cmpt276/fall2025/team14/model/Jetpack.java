package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.entities.AnimationInfo;

/**
 * Represents the Jetpack power-up, which grants a speed boost.
 * <p>
 * This class is automatically loaded by the {@link de.gurkenlabs.litiengine.environment.PropMapObjectLoader}
 * when a Prop with the type "jetpack" is found in the Tiled map.
 * </p>
 */
@AnimationInfo(spritePrefix = "prop-jetpack")
public class Jetpack extends Powerup {

    /**
     * Default constructor.
     * This is required by the {@link de.gurkenlabs.litiengine.environment.PropMapObjectLoader}
     * to create an instance of this class.
     */
    public Jetpack() {
        super("prop-jetpack");
    }

    /**
     * {@inheritDoc}
     *
     * @return {@link PowerUpType#JETPACK}
     */
    @Override
    public PowerUpType getType() {
        return PowerUpType.JETPACK;
    }
}