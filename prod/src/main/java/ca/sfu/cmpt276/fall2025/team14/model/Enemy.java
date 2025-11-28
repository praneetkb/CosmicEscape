package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.Creature;

/**
 * Base class for all enemy creatures in the game.  
 * Provides update capability and a placeholder method for line-of-sight checks.
 */
public class Enemy extends Creature implements IUpdateable {

    /**
     * Creates a new Enemy using the specified spritesheet name.
     *
     * @param spritesheetName the spritesheet used to render the enemy
     */
    public Enemy(String spritesheetName) {

        super(spritesheetName);
    }

    /**
     * Called once per update tick.  
     * Enemies do not implement internal update behavior by default.
     */
    @Override
    public void update() { }

    /**
     * Determines whether the player is currently within the enemy's line of sight.  
     * This base implementation always returns false and should be overridden by subclasses.
     *
     * @return false by default, until implemented by child classes
     */
    public boolean playerInLos() { return false; }
}

