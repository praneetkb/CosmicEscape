package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.entities.AnimationInfo;

/**
 * Represents a radiation-based punishment that uses a shared static countdown
 * timer across all radiation tiles. The countdown ticks globally, ensuring that
 * walking across multiple radiation tiles does not reset the timer.
 */
@AnimationInfo(spritePrefix = "prop-slime")
public class Slime extends Punishment{

    /**
     * Constructs a new Slime punishment using the "prop-slime" sprite.
     */
    public Slime() {
        super("prop-slime");
    }

    /**
     * Returns the punishment type associated with slime.
     *
     * @return {@link PunishmentType#SLIME}
     */
    @Override
    public PunishmentType getPunishmentType() {
        return PunishmentType.SLIME;
    }

    /**
     * Slime tiles have no per-frame update behavior.
     */
    @Override
    public void update() { }
}

