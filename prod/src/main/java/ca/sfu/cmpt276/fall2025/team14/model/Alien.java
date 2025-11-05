package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.EntityInfo;
import de.gurkenlabs.litiengine.entities.MovementInfo;
import de.gurkenlabs.litiengine.entities.behavior.IBehaviorController;
import ca.sfu.cmpt276.fall2025.team14.app.GameLogic;
import de.gurkenlabs.litiengine.Game;

@EntityInfo(width = 16, height = 16)
@MovementInfo(velocity = 20)
@CollisionInfo(collisionBoxWidth = 12, collisionBoxHeight = 12, collision = true)

public class Alien extends Creature {

    public Alien() {
        super("alien");

        // detects collision with player 
        this.onCollision(event -> {
            // check all entities involved in the collision
            for (var entity : event.getInvolvedEntities()) {
                if (entity instanceof Player) {
                    GameLogic.handlePlayerHit();
                    Player.instance().blink();
                }
            }
        }); 
    }
}