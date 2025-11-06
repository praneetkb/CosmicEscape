package ca.sfu.cmpt276.fall2025.team14.utils;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.behavior.EntityNavigator;
import de.gurkenlabs.litiengine.entities.behavior.EntityState;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.List;

public class FollowPathState extends EntityState<Creature> {

    private EntityNavigator navigator;
    private final Path2D path;
    private List<Point2D> points;
    private boolean started;

    public FollowPathState(final Creature entity, final Path2D path, List<Point2D> points) {
        super("FOLLOW_PATH", entity, Game.world().environment());
        this.path = path;
        this.points = points;
        for (Point2D point : points) {
            path.lineTo(point.getX(), point.getY());
        }
        this.navigator = new EntityNavigator(entity, null);
    }

    @Override
    public void perform() {

        if (!started) {
            navigator.navigate(path);
            this.started = true;
        }

        if (!navigator.isNavigating()) {
            navigator.stop();
            navigator.navigate(path);
        }
    }

    public EntityNavigator getNavigator() {
        return navigator;
    }
}