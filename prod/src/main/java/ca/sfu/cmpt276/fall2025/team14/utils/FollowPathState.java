package ca.sfu.cmpt276.fall2025.team14.utils;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.behavior.EntityNavigator;
import de.gurkenlabs.litiengine.entities.behavior.EntityState;

import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;

public class FollowPathState extends EntityState<Creature> {

    private EntityNavigator navigator;
    private final Path2D path;
    private Path2D reversePath;
    private boolean started;

    public FollowPathState(final Creature entity, final Path2D path) {
        super("FOLLOW_PATH", entity, Game.world().environment());
        this.path = path;

        // Add an EntityNavigator that can move our entity along paths. Since we want to determine the path manually, pass null as the PathFinder parameter to the constructor.
        this.navigator = new EntityNavigator(entity, null);
    }

    @Override
    public void perform() {
        // let the EntityNavigator start navigating.
        if (!started) {
            navigator.navigate(path);
            this.started = true;
        }
    }

    public EntityNavigator getNavigator() {
        return navigator;
    }
}