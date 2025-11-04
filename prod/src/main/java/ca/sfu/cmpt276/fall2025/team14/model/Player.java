package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.EntityInfo;
import de.gurkenlabs.litiengine.entities.MovementInfo;
import de.gurkenlabs.litiengine.input.KeyboardEntityController;
import de.gurkenlabs.litiengine.physics.IMovementController;

@EntityInfo(width = 16, height = 16)
@MovementInfo(velocity = 70)
@CollisionInfo(collisionBoxWidth = 12, collisionBoxHeight = 12, collision = true)
public class Player extends Creature implements IUpdateable {
    private static Player instance;

    public static Player instance() {
        if (instance == null) {
            instance = new Player();
        }

        return instance;
    }

    private Player() {
        super("player");
    }

    @Override
    public void update() {
    }

    @Override
    protected IMovementController createMovementController() {
        // setup movement controller
        return new KeyboardEntityController<>(this);
    }
}
