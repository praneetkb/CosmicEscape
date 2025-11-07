package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.entities.AnimationInfo;

/**
 * A specific power-up that stops time.
 * The LITIengine PropMapObjectLoader will create this class
 * when it finds a Prop in Tiled with the type "timestop".
 */
@AnimationInfo(spritePrefix = "prop-stopwatch")
public class Timestop extends Powerup {

    /**
     * Default constructor required by PropMapObjectLoader.
     */
    public Timestop() {
        super("prop-stopwatch");
    }

    @Override
    public PowerUpType getType() {
        return PowerUpType.TIMESTOP;
    }
}