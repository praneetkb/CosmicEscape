package ca.sfu.cmpt276.fall2025.team14.utils;

import ca.sfu.cmpt276.fall2025.team14.model.Vision;
import de.gurkenlabs.litiengine.Direction;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.Rotation;

public final class VisionAttacher {

    private VisionAttacher() {}

    public static void attach(Creature enemy, Vision vision) {
        Game.world().environment().add(vision);
        syncVision(enemy, vision);
    }

    public static void syncVision(Creature enemy, Vision vision) {
        final Direction d = enemy.getFacingDirection();
        final double enemyX = enemy.getX(), enemyY = enemy.getY(), enemyW = enemy.getWidth(), enemyH = enemy.getHeight();
        final double visionW = Vision.DEFAULT_WIDTH, visionH = Vision.DEFAULT_HEIGHT;
        // Swap sizes for left or right since sprite is 16x32
        if (d == Direction.LEFT || d == Direction.RIGHT) {
            vision.setSize(visionH, visionW);
        } else {
            vision.setSize(visionW, visionH);
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
}
