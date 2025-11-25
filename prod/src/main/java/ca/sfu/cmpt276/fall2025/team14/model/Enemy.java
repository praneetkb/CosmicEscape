package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.Creature;

public class Enemy extends Creature implements IUpdateable {

    /**
     * @param spritesheetName
     */
    public Enemy(String spritesheetName) {

        super(spritesheetName);
    }

    /**
     *
     */
    @Override
    public void update() { }

    public boolean playerInLos() { return false; }
}
