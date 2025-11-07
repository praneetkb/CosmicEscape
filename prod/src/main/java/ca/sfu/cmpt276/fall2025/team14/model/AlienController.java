package ca.sfu.cmpt276.fall2025.team14.model;

import ca.sfu.cmpt276.fall2025.team14.utils.FollowPathState;
import de.gurkenlabs.litiengine.entities.behavior.StateController;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.List;

public class AlienController extends StateController<Alien> {

    private final FollowPathState followPathState;

    public AlienController(Alien entity, Path2D path, List<Point2D> points) {
        super(entity);
        this.followPathState = new FollowPathState(entity, path, points);
        setState(followPathState);
    }
}