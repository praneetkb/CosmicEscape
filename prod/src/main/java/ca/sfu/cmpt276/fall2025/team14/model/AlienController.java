package ca.sfu.cmpt276.fall2025.team14.model;

import ca.sfu.cmpt276.fall2025.team14.utils.FollowPathState;
import de.gurkenlabs.litiengine.entities.behavior.StateController;
import java.awt.geom.Path2D;

public class AlienController extends StateController<Alien> {

    private final FollowPathState followPathState;

    public AlienController(Alien entity, Path2D path) {

        super(entity);
        this.followPathState = new FollowPathState(entity, path);

        setState(followPathState);
    }

}