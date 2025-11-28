package ca.sfu.cmpt276.fall2025.team14.utils;

import ca.sfu.cmpt276.fall2025.team14.model.Alien;
import ca.sfu.cmpt276.fall2025.team14.model.AlienController;
import de.gurkenlabs.litiengine.entities.IEntity;
import de.gurkenlabs.litiengine.environment.Environment;
import de.gurkenlabs.litiengine.environment.MapObjectLoader;
import de.gurkenlabs.litiengine.environment.tilemap.IMapObject;
import de.gurkenlabs.litiengine.environment.tilemap.MapUtilities;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Loads map objects of type "PATH" and constructs an {@link Alien} that follows
 * the path defined by the polyline in the map object. The loader converts the
 * polyline into a {@link Path2D}, extracts all points, optionally reverses or
 * closes the path, and sets up the alien with an {@link AlienController}.
 */
public class PathMapObjectLoader extends MapObjectLoader {

    /**
     * Creates a new loader for map objects of type "PATH".
     */
    public PathMapObjectLoader() {
        super("PATH");
    }

    /**
     * Loads an alien path from the given map object. If the object is a valid
     * "alien-path" polyline, the method creates a path, extracts its points,
     * determines whether it should close or reverse, initializes an {@link Alien},
     * and attaches an {@link AlienController} to handle movement along the path.
     *
     * @param environment the environment into which entities are being loaded
     * @param mapObject the map object defining the polyline path
     * @return a collection containing the created alien, or an empty collection if invalid
     */
    @Override
    public Collection<IEntity> load(Environment environment, IMapObject mapObject) {
        // Get path
        Collection<IEntity> entities = new ArrayList<>();
        if (!mapObject.getType().equals("PATH")
                || !mapObject.getName().equals("alien-path")
                || mapObject.getPolyline() == null
                || mapObject.getPolyline().getPoints().isEmpty()) {
            return entities;
        }
        // Convert the mapObject's polyline to a Path2D object.
        final Path2D path = MapUtilities.convertPolyshapeToPath(mapObject);
        if (path == null) {
            return entities;
        }
        // Get all points along path and reverse
        List<Point2D> points = new ArrayList<>();
        double[] arr = new double[6];
        for(PathIterator it = path.getPathIterator(null); !it.isDone(); it.next())
        {
            it.currentSegment(arr);
            points.add(new Point2D.Double(arr[0], arr[1]));
        }
        // Close path if pixel distance < 5
        if (points.getFirst().distance(points.getLast()) < 5) {
            points.add(points.getFirst());
        } else {
            points = points.reversed();
        }
        // Get starting point
        final Point2D start = new Point2D.Double(points.getLast().getX(), points.getLast().getY());
        // Initialize new alien on path
        Alien alien = new Alien();
        alien.setLocation(start);
        alien.setMapId(mapObject.getId());
        alien.addController(new AlienController(alien, path, points));
        entities.add(alien);

        return entities;
    }
}

