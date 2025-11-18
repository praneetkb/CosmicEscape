package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.Prop;

public abstract class Punishment extends Prop implements IUpdateable {

    public Punishment(String spritesheetName) {
        super(spritesheetName);
    }

    public abstract PunishmentType getPunishmentType();

    public abstract void update();
}
