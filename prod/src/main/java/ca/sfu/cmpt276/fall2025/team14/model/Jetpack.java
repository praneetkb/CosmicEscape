package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.entities.AnimationInfo;

/**
 * A specific power-up that grants a speed boost.
 * The LITIengine PropMapObjectLoader will create this class
 * when it finds a Prop in Tiled with the type "jetpack".
 */
@AnimationInfo(spritePrefix = "prop-jetpack")
public class Jetpack extends Powerup {

    /**
     * Default constructor required by PropMapObjectLoader.
     */
    public Jetpack() {
        super("prop-jetpack");
    }

    @Override
    public PowerUpType getType() {
        return PowerUpType.JETPACK;
    }
}