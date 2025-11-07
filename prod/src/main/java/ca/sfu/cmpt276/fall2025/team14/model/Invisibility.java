package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.entities.AnimationInfo;

/**
 * A specific power-up that grants invisibility.
 * The LITIengine PropMapObjectLoader will create this class
 * when it finds a Prop in Tiled with the type "invisibility".
 */
@AnimationInfo(spritePrefix = "prop-invisibility")
public class Invisibility extends Powerup {

    /**
     * Default constructor required by PropMapObjectLoader.
     */
    public Invisibility() {
        super("prop-invisibility");
    }

    @Override
    public PowerUpType getType() {
        return PowerUpType.INVISIBILITY;
    }
}