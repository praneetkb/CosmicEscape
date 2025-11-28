package ca.sfu.cmpt276.fall2025.team14.unit;

import ca.sfu.cmpt276.fall2025.team14.model.Alien;
import ca.sfu.cmpt276.fall2025.team14.model.AlienController;
import ca.sfu.cmpt276.fall2025.team14.utils.FollowPathState;

import org.junit.jupiter.api.Test;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AlienControllerTest {

    @Test
    public void testControllerInitializesPathState() {
        Alien alien = new Alien();
        Path2D path = new Path2D.Double();
        path.moveTo(0, 0);
        path.lineTo(10, 10);

        List<Point2D> points = List.of(
                new Point2D.Double(0, 0),
                new Point2D.Double(10, 10)
        );

        AlienController controller = new AlienController(alien, path, points);

        // AlienController sets its initial state to FollowPathState
        assertNotNull(controller.getCurrentState());
        assertTrue(controller.getCurrentState() instanceof FollowPathState);
    }
}

