package ca.sfu.cmpt276.fall2025.team14.utils;

import ca.sfu.cmpt276.fall2025.team14.model.Alien;
import ca.sfu.cmpt276.fall2025.team14.model.AlienController;
import de.gurkenlabs.litiengine.entities.IEntity;
import de.gurkenlabs.litiengine.environment.Environment;
import de.gurkenlabs.litiengine.environment.MapObjectLoader;
import de.gurkenlabs.litiengine.environment.tilemap.IMapObject;
import de.gurkenlabs.litiengine.environment.tilemap.MapUtilities;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;

public class PathMapObjectLoader extends MapObjectLoader {

    public PathMapObjectLoader() {

        super("PATH");
    }

    @Override
    public Collection<IEntity> load(Environment environment, IMapObject mapObject) {

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

        final Point2D start = new Point2D.Double(mapObject.getLocation().getX(), mapObject.getLocation().getY());

        // Either initialize a new Rat and add it to the entity list, or access an existing instance.
        Alien alien = new Alien();
        alien.setLocation(start);
        alien.setMapId(mapObject.getId());
        // Add a behavior controller to the rat.
        alien.addController(new AlienController(alien, path));
        entities.add(alien);

        return entities;
    }
}
