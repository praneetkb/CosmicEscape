package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.Valign;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.entities.EntityInfo;
import de.gurkenlabs.litiengine.entities.Prop;

import static de.gurkenlabs.litiengine.Align.CENTER;

/**
 * This is now an ABSTRACT base class for all power-ups.
 * It handles the collision info and update loop.
 * Each specific power-up (e.g., Jetpack) will extend this.
 */
@EntityInfo(width = 16, height = 16)
@CollisionInfo(collisionBoxWidth = 16, collisionBoxHeight = 16, collision = true, align = CENTER, valign = Valign.MIDDLE)
public abstract class Powerup extends Prop implements IUpdateable {

    /**
     * Constructor for a power-up.
     * @param spriteName The sprite prefix (e.g., "prop-jetpack").
     */
    public Powerup(String spriteName) {

        super(spriteName);
    }

    /**
     * Each power-up subclass must implement this to identify itself.
     * @return The specific PowerUpType.
     */
    public abstract PowerUpType getType();

    @Override
    public void update() {
        // Power-ups themselves don't need update logic,
        // GameLogic will handle collision and effects.
    }
}