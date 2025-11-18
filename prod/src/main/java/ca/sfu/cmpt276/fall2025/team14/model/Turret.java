package ca.sfu.cmpt276.fall2025.team14.model;

import ca.sfu.cmpt276.fall2025.team14.utils.VisionAttacher;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.Valign;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.EntityInfo;

import static de.gurkenlabs.litiengine.Align.CENTER;

import ca.sfu.cmpt276.fall2025.team14.app.GameLogic;
import de.gurkenlabs.litiengine.Game;

/**
 * The {@code Turret} class represents a stationary enemy in the game that can rotate
 * within a limited angle range and detect players using its {@link Vision} component.
 * <p>
 * This class extends {@link Creature} and implements {@link IUpdateable}, allowing
 * it to perform continuous updates at every tick of the game.
 * </p>
 *
 * <p>
 * The turret can rotate back and forth between a minimum and maximum angle.
 * If the player is caught, a collision with the {@link Player} occurs and the game restarts via {@link GameLogic#restartLevel()}.
 * </p>
 *
 */

@EntityInfo(width = 16, height = 16)
@CollisionInfo(collisionBoxWidth = 14, collisionBoxHeight = 14, collision = true, align = CENTER, valign = Valign.MIDDLE)

public class Turret extends Creature implements IUpdateable {

    /** The vision area attached to the turret. */
    private static Vision vision = new Vision();

    /** The rotation speed of the turret in degrees per second. */
    private double rotationSpeed = 90; // degrees per second

    /** The minimum rotation offset (in degrees) relative to the turret’s initial rotation. */
    private double minRotation = -45;  // min angle from start rotation

    /** The maximum rotation offset (in degrees) relative to the turret’s initial rotation. */
    private double maxRotation = 45;   // max angle from start rotation

    /** Indicates whether the turret is currently rotating clockwise. */
    private boolean rotatingClockwise = true; // current rotation direction

    /** Stores the turret’s initial rotation angle when spawned. */
    private double initialRotation;

    /**
     * Constructs a new {@code Turret} and attaches a vision to it.
     */
    public Turret() {
        super("turret");
        // Attach vision
        VisionAttacher.attach(this, vision);
    }

    @Override
    public void update() {
        // Sync vision with current facing direction
        VisionAttacher.syncVision(this, vision);

//        // TO DO: ROTATE TURRET
//        // get time passed since last frame in seconds
//        double deltaTime = Game.loop().getDeltaTime() / 1000.0;
//        double deltaRotation = rotationSpeed * deltaTime;
//
//        // rotate turret back and forth between min and max angles
//        if (rotatingClockwise) {
//            this.setAngle(this.getAngle() + deltaRotation);
//            if (this.getAngle() >= initialRotation + maxRotation) {
//                this.setAngle(initialRotation + maxRotation);
//                rotatingClockwise = false;
//            }
//        } else {
//            this.setAngle(this.getAngle() - deltaRotation);
//            if (this.getAngle() <= initialRotation + minRotation) {
//                this.setAngle(initialRotation + minRotation);
//                rotatingClockwise = true;
//            }
//        }
//
//        // check for collision with player. If yes then instant death and restart
//        Player player = Player.instance();
//        if (this.getCollisionBox().intersects(player.getCollisionBox())) {
//            GameLogic.restartLevel();
//        }
    }
}
