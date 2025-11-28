package ca.sfu.cmpt276.fall2025.team14.utils;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.behavior.EntityNavigator;
import de.gurkenlabs.litiengine.entities.behavior.EntityState;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.List;

/**
 * Represents a custom entity state that moves a {@link Creature} along a predefined path.
 * The state uses an {@link EntityNavigator} to control navigation and continuously restarts
 * the path when the end is reached.
 */
public class FollowPathState extends EntityState<Creature> {

    private EntityNavigator navigator;
    private final Path2D path;
    private boolean started;

    /**
     * Constructs a new FollowPathState for the given creature and path.
     * The provided points are appended to the path in the order given.
     *
     * @param entity the creature that will follow the path
     * @param path the initial {@link Path2D} for navigation
     * @param points the list of points to extend the path with
     */
    public FollowPathState(final Creature entity, final Path2D path, List<Point2D> points) {
        super("FOLLOW_PATH", entity, Game.world().environment());
        // Initial path
        this.path = path;
        // Add points to path
        for (Point2D point : points) {
            path.lineTo(point.getX(), point.getY());
        }
        this.navigator = new EntityNavigator(entity, null);
    }

    /**
     * Executes the state's behavior. If navigation has not started, the navigator begins
     * moving along the path. When the path completes, navigation restarts from the beginning.
     */
    @Override
    public void perform() {
        // Initiate path finding
        if (!started) {
            navigator.navigate(path);
            this.started = true;
        }
        // Reverse when at end of path
        if (!navigator.isNavigating()) {
            navigator.stop();
            navigator.navigate(path);
        }
    }

    /**
     * Returns the {@link EntityNavigator} used to move the creature along the path.
     *
     * @return the navigator controlling movement
     */
    public EntityNavigator getNavigator() {
        return navigator;
    }
}

