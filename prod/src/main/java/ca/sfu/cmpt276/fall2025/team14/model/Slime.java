package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.entities.AnimationInfo;

@AnimationInfo(spritePrefix = "prop-slime")
public class Slime extends Punishment{

    public Slime() {
        super("prop-slime");
    }

    @Override
    public PunishmentType getPunishmentType() {
        return PunishmentType.SLIME;
    }

    @Override
    public void update() { }
}
