package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.entities.AnimationInfo;

/**
 * Represents the Invisibility power-up, which makes the player invisible to enemies.
 * <p>
 * This class is automatically loaded by the {@link de.gurkenlabs.litiengine.environment.PropMapObjectLoader}
 * when a Prop with the type "invisibility" is found in the Tiled map.
 * </p>
 */
@AnimationInfo(spritePrefix = "prop-invisibility")
public class Invisibility extends Powerup {

    /**
     * Default constructor.
     * This is required by the {@link de.gurkenlabs.litiengine.environment.PropMapObjectLoader}
     * to create an instance of this class.
     */
    public Invisibility() {
        super("prop-invisibility");
    }

    /**
     * {@inheritDoc}
     *
     * @return {@link PowerUpType#INVISIBILITY}
     */
    @Override
    public PowerUpType getType() {
        return PowerUpType.INVISIBILITY;
    }
}