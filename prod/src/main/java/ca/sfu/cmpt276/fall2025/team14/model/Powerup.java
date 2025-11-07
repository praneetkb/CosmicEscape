package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.Valign;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.entities.EntityInfo;
import de.gurkenlabs.litiengine.entities.Prop;

import static de.gurkenlabs.litiengine.Align.CENTER;

/**
 * This is the abstract base class for all power-up items in the game.
 * It provides the common collision setup and requires subclasses to specify
 * their {@link PowerUpType}.
 * Subclasses (e.g., {@link Jetpack}) will be loaded automatically by LITIengine
 * based on the "type" property set in the Tiled map editor.
 */
@EntityInfo(width = 16, height = 16)
@CollisionInfo(collisionBoxWidth = 16, collisionBoxHeight = 16, collision = true, align = CENTER, valign = Valign.MIDDLE)
public abstract class Powerup extends Prop implements IUpdateable {

    /**
     * Constructs a new Powerup.
     *
     * @param spriteName The sprite prefix (e.g., "prop-jetpack") for this prop.
     */
    public Powerup(String spriteName) {
        super(spriteName);
    }

    /**
     * Gets the specific type of this power-up.
     * Subclasses must implement this to identify themselves.
     *
     * @return The {@link PowerUpType} of this instance.
     */
    public abstract PowerUpType getType();

    /**
     * The main update loop for the power-up.
     * This is intentionally left empty, as power-ups are static items
     * and all collision logic is handled by {@link ca.sfu.cmpt276.fall2025.team14.app.GameLogic}.
     */
    @Override
    public void update() {
        // Power-ups themselves don't need update logic,
        // GameLogic will handle collision and effects.
    }
}