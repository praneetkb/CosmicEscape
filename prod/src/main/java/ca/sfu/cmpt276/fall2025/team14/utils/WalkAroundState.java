package ca.sfu.cmpt276.fall2025.team14.utils;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.behavior.EntityState;

public class WalkAroundState extends EntityState<Creature> {
    private static final int ANGLE_CHANGE_INTERVAL = 3000;
    private int angle;
    private long lastAngleChange;

    public WalkAroundState(final Creature entity) {
        super("WALK_AROUND", entity, Game.world().environment());
    }

    @Override
    public void perform() {
        if (getEntity().isDead()) {
            return;
        }

        final long currentTick = Game.loop().getTicks();

        if (angle == 0 || Game.time().since(lastAngleChange) > ANGLE_CHANGE_INTERVAL) {
            this.angle = Game.random().nextInt(360);
            this.lastAngleChange = currentTick;
        }

        Game.physics().move(getEntity(), angle, getEntity().getTickVelocity());
    }
}