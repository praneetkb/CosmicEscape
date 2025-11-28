package ca.sfu.cmpt276.fall2025.team14.integration;

import ca.sfu.cmpt276.fall2025.team14.IntegrationTestBase;
import ca.sfu.cmpt276.fall2025.team14.app.GameLogic;
import ca.sfu.cmpt276.fall2025.team14.model.Alien;
import ca.sfu.cmpt276.fall2025.team14.model.Player;
import ca.sfu.cmpt276.fall2025.team14.model.Timestop;
import ca.sfu.cmpt276.fall2025.team14.model.Turret;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TimestopIntegrationTest extends IntegrationTestBase {

    @BeforeEach
    public void resetState() {
        Player player = Player.getInstance();
        player.setLocation(0, 0);
        player.resetPowerUps();
    }

    // timestop powerup with enemies (alien and turret) and player
    @Test
    public void testTimestopFreezesAllEnemies() {

        Player player = Player.getInstance();
        Alien alien = new Alien();
        Turret turret = new Turret();

        // placing enemies at initial positions
        alien.setLocation(100, 100);
        turret.setLocation(200, 200);

        // update enemies once
        alien.update();
        turret.update();

        // save positions after first update
        double alienX = alien.getX();
        double alienY = alien.getY();
        double turretX = turret.getX();
        double turretY = turret.getY();

        // collect timestop power-up
        Timestop timestop = new Timestop();
        GameLogic.TestApplyPowerup(timestop);
        assertTrue(GameLogic.isTimeStopped());

        // update enemies again - they should not move (same positions)
        alien.update();
        turret.update();

        // check that their positions have not changed
        assertEquals(alienX, alien.getX(), "Alien X position should not change during timestop");
        assertEquals(alienY, alien.getY(), "Alien Y position should not change during timestop");
        assertEquals(turretX, turret.getX(), "Turret X position should not change during timestop");
        assertEquals(turretY, turret.getY(), "Turret Y position should not change during timestop");

        // reset
        GameLogic.setTimeStopped(false);
    }
}
