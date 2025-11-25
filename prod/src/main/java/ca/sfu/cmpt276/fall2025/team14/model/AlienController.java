package ca.sfu.cmpt276.fall2025.team14.model;

import ca.sfu.cmpt276.fall2025.team14.utils.FollowPathState;
import de.gurkenlabs.litiengine.entities.behavior.StateController;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.List;

/**
 * The {@code AlienController} class manages the behavior of an {@link Alien}
 * entity using the LITIengine {@link StateController} framework.
 * <p>
 * This controller allows an alien to follow a predefined movement path
 * in the game world by assigning it a {@link FollowPathState}.
 * The path and its waypoints are provided during construction.
 * </p>
 *
 * @see Alien
 * @see FollowPathState
 * @see de.gurkenlabs.litiengine.entities.behavior.StateController
 */

public class AlienController extends StateController<Alien> {

    /** The behavior state responsible for making the alien follow a set path. */
    private final FollowPathState followPathState;

    /**
     * Constructs a new {@code AlienController} for the given {@link Alien}.
     * <p>
     * This controller uses a {@link FollowPathState} to make the alien move
     * along the specified {@link Path2D} using the provided list of waypoints.
     * </p>
     *
     * @param entity the alien entity this controller manages
     * @param path the path defining the alien’s movement
     * @param points the list of waypoints along the path
     */
    public AlienController(Alien entity, Path2D path, List<Point2D> points) {
        super(entity);
        this.followPathState = new FollowPathState(entity, path, points);
        setState(followPathState);
    }
}