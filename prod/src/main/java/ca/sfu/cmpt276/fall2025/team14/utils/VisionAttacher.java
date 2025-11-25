package ca.sfu.cmpt276.fall2025.team14.utils;

import ca.sfu.cmpt276.fall2025.team14.model.*;
import de.gurkenlabs.litiengine.Direction;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.Rotation;
import java.awt.geom.Point2D;

public final class VisionAttacher {

    private VisionAttacher() {}

    public static void attach(Enemy enemy, Vision vision) {
        Game.world().environment().add(vision);
        vision.setAttachedEnemy(enemy);
    }

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
