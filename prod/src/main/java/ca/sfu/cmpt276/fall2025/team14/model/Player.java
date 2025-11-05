package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.Game;
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

    private IMovementController movementController;

    public static Player instance() {
        if (instance == null) {
            instance = new Player();
        }

        return instance;
    }

    private Player() {
        super("player");
    }

    public void stopMovement() {
        this.setVelocity(0);
    }
    

    public void blink() {
        this.setVisible(false);
    
        // hide player for 200 ms and show again. This is for the first and second enemy collision
        Game.loop().perform(200, () -> this.setVisible(true));
    }

    @Override
    public void update() {
    }

    @Override
    protected IMovementController createMovementController() { // overriding to define player's controller 
        // setup movement controller
        this.movementController = new KeyboardEntityController<>(this);
        return this.movementController;
    }

    public IMovementController getMovementController() { // for other classes (like GameLogic) to access 
        return this.movementController;
    }
}
