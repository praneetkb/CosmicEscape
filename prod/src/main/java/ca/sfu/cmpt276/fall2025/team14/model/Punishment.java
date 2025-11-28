package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.Prop;

/**
 * Represents a punishable environmental object that updates over time
 * and has an associated {@link PunishmentType}. This class serves as the
 * base for all specific punishment implementations.
 */
public abstract class Punishment extends Prop implements IUpdateable {

    /**
     * Constructs a punishment object using the specified spritesheet name.
     *
     * @param spritesheetName the name of the spritesheet to be used for rendering
     */
    public Punishment(String spritesheetName) {
        super(spritesheetName);
    }

    /**
     * Returns the type of punishment represented by this object.
     *
     * @return the associated {@link PunishmentType}
     */
    public abstract PunishmentType getPunishmentType();

    /**
     * Updates the punishment object's behavior each tick.
     */
    public abstract void update();
}

