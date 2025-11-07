package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.Valign;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.EntityInfo;

import static de.gurkenlabs.litiengine.Align.CENTER;

import ca.sfu.cmpt276.fall2025.team14.app.GameLogic;
import de.gurkenlabs.litiengine.Game;

@EntityInfo(width = 16, height = 16)
@CollisionInfo(collisionBoxWidth = 16, collisionBoxHeight = 16, collision = true, align = CENTER, valign = Valign.MIDDLE)

public class Turret extends Creature implements IUpdateable {

    private double rotationSpeed = 90; // degrees per second
    private double minRotation = -45;  // min angle from start rotation
    private double maxRotation = 45;   // max angle from start rotation
    private boolean rotatingClockwise = true; // current rotation direction
    private double initialRotation;

    /**
     * Flag to control the turret's rotation.
     * Can be set to false by GameLogic to pause the turret during a TIMESTOP power-up.
     */
    private boolean isRotating = true; // Flag to pause rotation for TIMESTOP

    public Turret() {
        super("turret");
        this.initialRotation = 0;
        this.setAngle(initialRotation);
    }

    /**
     * The main update loop for the Turret.
     * This has been modified to:
     * 1. Respect the `isRotating` flag (for the TIMESTOP power-up).
     * 2. Check for Player power-ups (INVISIBILITY, ALIEN_CHARM) before restarting the level.
     */
    @Override
    public void update() {

        if (this.isRotating) {
            // get time passed since last frame in seconds
            double deltaTime = Game.loop().getDeltaTime() / 1000.0;
            double deltaRotation = rotationSpeed * deltaTime;

            // rotate turret back and forth between min and max angles
            if (rotatingClockwise) {
                this.setAngle(this.getAngle() + deltaRotation);
                if (this.getAngle() >= initialRotation + maxRotation) {
                    this.setAngle(initialRotation + maxRotation);
                    rotatingClockwise = false;
                }
            } else {
                this.setAngle(this.getAngle() - deltaRotation);
                if (this.getAngle() <= initialRotation + minRotation) {
                    this.setAngle(initialRotation + minRotation);
                    rotatingClockwise = true;
                }
            }
        }

        // This check is also in GameLogic, but modifying it here provides defence-in-depth
        // and respects the original structure.
        Player player = Player.instance();
        if (this.getCollisionBox().intersects(player.getCollisionBox())) {
            
            if (player.isInvisible()) {
                // Player is invisible, do nothing
            } else if (player.hasAlienCharm()) {
                // Player has a charm, consume it and blink
                player.useAlienCharm();
            } else {
                // Player is caught
                // check for collision with player. If yes then instant death and restart (Original Comment)
                GameLogic.restartLevel();
            }
        }
    }

    /**
     * Called by GameLogic to pause or unpause the turret's rotation.
     * @param rotating true to resume rotation, false to pause.
     */
    public void setRotating(boolean rotating) {
        this.isRotating = rotating;
    }
}