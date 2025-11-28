package ca.sfu.cmpt276.fall2025.team14.model;

import ca.sfu.cmpt276.fall2025.team14.utils.VisionAttacher;
import de.gurkenlabs.litiengine.Direction;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.Valign;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.entities.EntityInfo;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
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
@CollisionInfo(collisionBoxWidth = 12, collisionBoxHeight =12, collision = true, align = CENTER, valign = Valign.MIDDLE)
public class Turret extends Enemy {

    /**
     * The vision area attached to the turret.
     */
    private final Vision vision;
    // Rotation config (UP-based degrees)
    private static final double DEFAULT_DEG_PER_SEC = 30;
    private double degPerSec = DEFAULT_DEG_PER_SEC;
    private double minRotation = 1;
    private double maxRotation = 360;
    private boolean clockwise = true;
    private Direction lastDir = Direction.UP;
    private double hysteresisDeg = 12.0;

    // Runtime state
    private double currentDegree = minRotation;

    // LOS rays
    private static final double EDGE_OFFSET_DEG = 14.0;
    private static final double LOS_LEN = 10.0 + 32.0;
    private final Line2D.Double losCenter = new Line2D.Double();
    private final Line2D.Double losLeft = new Line2D.Double();
    private final Line2D.Double losRight = new Line2D.Double();

    /**
     * Constructs a new {@code Turret} and attaches a vision to it.
     */
    public Turret() {
        super("turret");
        // Attach vision
        vision = new Vision();
        VisionAttacher.attach(this, vision);
    }

    @Override
    public void update() {
        // Frame-rate independent sweep on a linear axis
        final double dt = Game.loop().getDeltaTime() / 1000.0;
        double step = degPerSec * dt * (clockwise ? +1.0 : -1.0);
        double next = currentDegree + step;
        if (next > maxRotation) {
            double o = next - maxRotation;
            next = maxRotation - o;
            clockwise = false;
        }
        if (next < minRotation) {
            double o = minRotation - next;
            next = minRotation + o;
            clockwise = true;
        }
        currentDegree = next;
        // Sync vision sprite to current rotation
        VisionAttacher.syncTurretVision(this, vision, currentDegree);
        // Calculate base degree rotation for rays
        final Point2D c = this.getCenter();
        final double theta = Math.toRadians(currentDegree - 90.0);
        final double ux = Math.cos(theta), uy = Math.sin(theta);
        final double off = Math.toRadians(EDGE_OFFSET_DEG);
        // center ray
        final double sx = c.getX(), sy = c.getY();
        double ex = sx + ux * LOS_LEN, ey = sy + uy * LOS_LEN;
        losCenter.setLine(sx, sy, ex, ey);
        // left edge (+14°)
        double uxL = Math.cos(theta + off), uyL = Math.sin(theta + off);
        double exL = sx + uxL * LOS_LEN, eyL = sy + uyL * LOS_LEN;
        losLeft.setLine(sx, sy, exL, eyL);
        // right edge (-14°)
        double uxR = Math.cos(theta - off), uyR = Math.sin(theta - off);
        double exR = sx + uxR * LOS_LEN, eyR = sy + uyR * LOS_LEN;
        losRight.setLine(sx, sy, exR, eyR);
        // Set sprite rotation based on degree
        setDirection();
    }

    private void setDirection() {
        // Normalize degrees
        double deg = currentDegree;
        while (deg < 0) deg += 360.0;
        while (deg >= 360.0) deg -= 360.0;
        // Quadrants
        final double h = hysteresisDeg;
        final double U_R = 45.0, R_D = 135.0, D_L = 225.0, L_U = 315.0;
        // Get direction
        Direction next = lastDir;
        final boolean inRIGHT = (deg >= U_R + h && deg < R_D - h);
        final boolean inDOWN = (deg >= R_D + h && deg < D_L - h);
        final boolean inLEFT = (deg >= D_L + h && deg < L_U - h);
        final boolean inUP = (deg >= L_U + h || deg <= U_R - h);
        switch (lastDir) {
            case UP -> next = inRIGHT ? Direction.RIGHT : inLEFT ? Direction.LEFT : Direction.UP;
            case RIGHT -> next = inDOWN ? Direction.DOWN : inUP ? Direction.UP : Direction.RIGHT;
            case DOWN -> next = inLEFT ? Direction.LEFT : inRIGHT ? Direction.RIGHT : Direction.DOWN;
            case LEFT -> next = inUP ? Direction.UP : inDOWN ? Direction.DOWN : Direction.LEFT;
        }
        // Set direction
        if (next != lastDir) {
            setFacingDirection(next);
            lastDir = next;
        }
    }

    @Override
    public boolean playerInLos() {
        Rectangle2D playerCB = Player.instance().getCollisionBox();
        return losCenter.intersects(playerCB) || losLeft.intersects(playerCB) || losRight.intersects(playerCB);
    }

    public static double getDefaultDegPerSec() {
        return DEFAULT_DEG_PER_SEC;
    }

    public Vision getVision() {
        return vision;
    }

    public Line2D.Double getLosCenter() {
        return losCenter;
    }

    public double getDegPerSec() {
        return degPerSec;
    }

    public void setDegPerSec(double degPerSec) {
        this.degPerSec = degPerSec;
    }

    public double getMinRotation() {
        return minRotation;
    }

    public void setMinRotation(double minRotation) {
        this.minRotation = minRotation;
    }

    public double getMaxRotation() {
        return maxRotation;
    }

    public void setMaxRotation(double maxRotation) {
        this.maxRotation = maxRotation;
    }

    public double getCurrentDegree() {
        return currentDegree;
    }

    public void setCurrentDegree(double currentDegree) {
        this.currentDegree = currentDegree;
    }
}
