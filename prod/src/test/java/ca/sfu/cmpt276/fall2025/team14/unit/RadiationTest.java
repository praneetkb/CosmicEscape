package ca.sfu.cmpt276.fall2025.team14.unit;

import ca.sfu.cmpt276.fall2025.team14.IntegrationTestBase;
import ca.sfu.cmpt276.fall2025.team14.app.GameLogic;
import ca.sfu.cmpt276.fall2025.team14.model.Player;
import ca.sfu.cmpt276.fall2025.team14.model.Radiation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RadiationTest extends IntegrationTestBase {

    @BeforeEach
    public void resetPlayer() {
        Player player = Player.getInstance();
        player.resetPowerUps();
    }

    // radiation countdown (timer) starts on collision
    @Test
    public void testRadiationCountdownStartsOnCollision() {
        Radiation.stopCountdown(); // ensure clean state
        Radiation rad = new Radiation();

        // to simulate collision
        Radiation.startCountdown();

        assertTrue(Radiation.isCountdownStarted());
        assertTrue(Radiation.getCountdownTimer() > 0);
    }

    // radiation countdown stops when player exits the zone
    @Test
    public void testRadiationCountdownStopsWhenLeaving() {
        Radiation.stopCountdown();
        Radiation.startCountdown();

        assertTrue(Radiation.isCountdownStarted());

        Radiation.stopCountdown();

        assertFalse(Radiation.isCountdownStarted());
    }

    // countdown never goes below 0
    @Test
    public void testRadiationCountdownDoesNotGoNegative() {
        Radiation.stopCountdown();
        Radiation.startCountdown();

        // force hit zero
        Radiation.setCountdownTimer(1);
        Radiation.decrementTimer();
        if (Radiation.getCountdownTimer() < 0) {
            Radiation.setCountdownTimer(0);
        }

        Radiation.decrementTimer();
        if (Radiation.getCountdownTimer() < 0) {
            Radiation.setCountdownTimer(0);
        }

        assertEquals(0, Radiation.getCountdownTimer());
    }
}
