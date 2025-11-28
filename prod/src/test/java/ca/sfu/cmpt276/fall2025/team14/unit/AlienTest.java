package ca.sfu.cmpt276.fall2025.team14.unit;

import ca.sfu.cmpt276.fall2025.team14.IntegrationTestBase;
import ca.sfu.cmpt276.fall2025.team14.app.GameLogic;
import ca.sfu.cmpt276.fall2025.team14.model.Alien;
import ca.sfu.cmpt276.fall2025.team14.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static de.gurkenlabs.litiengine.physics.Collision.STATIC;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AlienTest extends IntegrationTestBase {

    @BeforeEach
    public void resetPlayer() {
        Player player = Player.getInstance();
        player.setLocation(0, 0);
        player.resetPowerUps();
    }


    // check if alien has vision attached
    @Test
    public void testAlienVisionAttached() {
        Alien alien = new Alien();
        assertNotNull(alien.getVision());
    }

    // when player is caught by alien - found in LOS
    @Test
    public void testPlayerDetectedInLOSAlien() {
        Alien alien = new Alien();
        Player player = Player.getInstance();

        alien.setLocation(0, 0);
        player.setLocation(5, 0); // inside bounding box area

        // ensure vision sync happens
        alien.update();

        assertTrue(alien.playerInLos());
    }

    // player not caught by alien
    @Test
    public void testPlayerNotInLOSAlien() {
        Alien alien = new Alien();
        Player player = Player.getInstance();

        alien.setLocation(0, 0);
        player.setLocation(500, 500); // far away

        alien.update();

        assertFalse(alien.playerInLos());
    }

    // alien collision
    @Test
    public void testCanCollideWithPlayer() {
        Alien alien = new Alien();

        Player player = Player.getInstance();
        player.setCollisionType(STATIC);

        assertTrue(alien.canCollideWith(player));
    }
}
