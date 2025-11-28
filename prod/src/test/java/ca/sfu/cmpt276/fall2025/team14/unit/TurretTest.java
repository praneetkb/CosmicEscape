package ca.sfu.cmpt276.fall2025.team14.unit;

import ca.sfu.cmpt276.fall2025.team14.IntegrationTestBase;
import ca.sfu.cmpt276.fall2025.team14.app.GameLogic;
import ca.sfu.cmpt276.fall2025.team14.model.Player;
import ca.sfu.cmpt276.fall2025.team14.model.Turret;
import org.junit.jupiter.api.Test;

import java.awt.geom.Line2D;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TurretTest extends IntegrationTestBase {

    // turret - check if it has vision attached
    @Test
    public void testVisionAttached() {
        Turret turret = new Turret();

        assertNotNull(turret.getVision());
    }

    // math of the rays for the turret - checks that the LOS line actually moves when update is called
    @Test
    public void testVisionSyncUpdatesRays() {
        Turret turret = new Turret();
        turret.setCurrentDegree(90);

        turret.update();

        Line2D center = turret.getLosCenter();

        assertNotEquals(center.getX1(), center.getX2());
        assertNotEquals(center.getY1(), center.getY2());
    }

    // when player is caught by turret - found in LOS
    @Test
    public void testPlayerDetectedInLOSTurret() {
        Turret turret = new Turret();
        Player player = Player.getInstance();

        // place turret at (0,0)
        turret.setLocation(0, 0);

        // place player within LOS in front of turret
        player.setLocation(20, 0);

        turret.setCurrentDegree(90);  // facing right
        turret.update();

        assertTrue(turret.playerInLos());
    }

    // player is not caught by turret
    @Test
    public void testPlayerNotInLOSTurret() {
        Turret turret = new Turret();
        Player player = Player.getInstance();

        turret.setLocation(0, 0);
        player.setLocation(200, 200); // far away

        turret.update();

        assertFalse(turret.playerInLos());
    }
}
