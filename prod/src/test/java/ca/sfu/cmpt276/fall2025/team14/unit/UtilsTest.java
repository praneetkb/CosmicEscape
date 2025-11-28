package ca.sfu.cmpt276.fall2025.team14.unit;

import ca.sfu.cmpt276.fall2025.team14.utils.PathMapObjectLoader;
import ca.sfu.cmpt276.fall2025.team14.utils.FollowPathState;
import de.gurkenlabs.litiengine.entities.Creature;
import org.junit.jupiter.api.Test;

import java.awt.geom.Path2D;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UtilsTest {

    // PathMapObjectLoader
    @Test
    void testPathMapObjectLoaderConstructor() {
        assertDoesNotThrow(PathMapObjectLoader::new);
    }

    // FollowPathState
    @Test
    void testFollowPathStateConstructorAndGetNavigator() {
        Creature dummy = new Creature("dummy");
        Path2D path = new Path2D.Float();
        FollowPathState state = assertDoesNotThrow(() -> new FollowPathState(dummy, path, List.of()));
        assertNotNull(state.getNavigator());
    }
}
