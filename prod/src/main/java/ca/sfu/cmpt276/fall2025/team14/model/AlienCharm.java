package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.entities.AnimationInfo;

/**
 * A specific power-up that grants a one-time save.
 * The LITIengine PropMapObjectLoader will create this class
 * when it finds a Prop in Tiled with the type "aliencharm".
 */
@AnimationInfo(spritePrefix = "prop-charm")
public class AlienCharm extends Powerup {

    /**
     * Default constructor required by PropMapObjectLoader.
     */
    public AlienCharm() {
        super("prop-charm");
    }

    @Override
    public PowerUpType getType() {
        return PowerUpType.ALIEN_CHARM;
    }
}