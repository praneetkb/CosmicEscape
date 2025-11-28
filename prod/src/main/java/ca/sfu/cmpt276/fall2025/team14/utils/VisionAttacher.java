package ca.sfu.cmpt276.fall2025.team14.utils;

import ca.sfu.cmpt276.fall2025.team14.model.*;
import de.gurkenlabs.litiengine.Direction;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.Rotation;
import java.awt.geom.Point2D;

/**
 * Utility class for attaching and synchronizing {@link Vision} components
 * with different types of enemies, such as {@link Enemy}, {@link Alien}, or {@link Turret}.
 * Handles positioning, rotation, and size adjustments based on the entity type.
 */
public final class VisionAttacher {

    private VisionAttacher() {}

    /**
     * Attaches the specified {@link Vision} to an {@link Enemy} and registers it
     * within the current game environment.
     *
     * @param enemy the enemy to attach the vision to
     * @param vision the vision object that will be linked to the enemy
     */
    public static void attach(Enemy enemy, Vision vision) {
        Game.world().environment().add(vision);
        vision.setAttachedEnemy(enemy);
    }

    /**
     * Synchronizes an alien's vision with the enemy's current position and facing
     * direction. Adjusts size, collision box, coordinates, and rotation based on
     * the enemy's orientation.
     *
     * @param enemy the creature whose vision area should be synchronized
     * @param vision the vision object to update
     */
    public static void syncAlienVision(Creature enemy, Vision vision) {
        // Get enemy and vision information
        Direction d = enemy.getFacingDirection();
        double enemyX = enemy.getX(), enemyY = enemy.getY(), enemyW = enemy.getWidth(), enemyH = enemy.getHeight();
        double visionW = Vision.getDefaultWidth(),  visionH = Vision.getDefaultHeight();
        // Swap sizes for left or right since sprite is 16x32
        if (d == Direction.LEFT || d == Direction.RIGHT) {
            vision.setSize(visionH, visionW);
            vision.setCollisionBoxHeight(visionW - 2);
            vision.setCollisionBoxWidth(visionH - 2);
        } else {
            vision.setSize(visionW, visionH);
            vision.setCollisionBoxHeight(visionH - 2);
            vision.setCollisionBoxWidth(visionW - 2);
        }
        // Set location based on enemy
        switch (d) {
            case UP ->    vision.setLocation(enemyX + (enemyW - visionW) / 2, enemyY - visionH);
            case DOWN ->  vision.setLocation(enemyX + (enemyW - visionW) / 2, enemyY + enemyH);
            case LEFT ->  vision.setLocation(enemyX - visionH, enemyY + (enemyH - visionW) / 2);
            case RIGHT -> vision.setLocation(enemyX + enemyW,enemyY + (enemyH - visionW) / 2);
        }
        // Set rotation based on direction enemy is facing
        switch (d) {
            case UP ->    vision.setSpriteRotation(Rotation.NONE);
            case RIGHT -> vision.setSpriteRotation(Rotation.ROTATE_90);
            case DOWN ->  vision.setSpriteRotation(Rotation.ROTATE_180);
            case LEFT ->  vision.setSpriteRotation(Rotation.ROTATE_270);
        }
    }

    /**
     * Synchronizes a turret's vision area based on the turret's center and the
     * specified upward angle. Computes the correct offset using trigonometric
     * rotation around a fixed radius.
     *
     * @param turret the turret whose vision should be updated
     * @param vision the vision to reposition
     * @param degUp the upward-facing angle in degrees
     */
    public static void syncTurretVision(Turret turret, Vision vision, double degUp) {
        // Radius from turret center
        double r = 24.0;
        // Convert Java AWT degree based
        double aRight = degUp - 90.0;
        // convert to radians
        double rad = Math.toRadians(aRight);
        Point2D c = turret.getCenter();
        double cx = c.getX() + r * Math.cos(rad);
        double cy = c.getY() + r * Math.sin(rad);
        // Set location
        vision.setLocation(cx - vision.getWidth() / 2.0, cy - vision.getHeight() / 2.0);
    }

}

