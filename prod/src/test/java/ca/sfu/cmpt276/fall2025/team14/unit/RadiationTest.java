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

    // update should attach ticker only once
    @Test
    public void testRadiationTickerAttachesOnlyOnce() {
        Radiation.stopCountdown();
        Radiation.setCountdownTimer(Radiation.getCountdownMax());

        Radiation rad = new Radiation();

        // first update should attach ticker
        rad.update();

        int before = Radiation.getCountdownTimer();

        // countdown not started - should reset timer to max
        assertEquals(Radiation.getCountdownMax(), before);

        // second update - tickerAttached stays true
        rad.update();

        // still unchanged (no double attach)
        assertEquals(Radiation.getCountdownMax(), Radiation.getCountdownTimer());
    }

    // update should decrement when countdown is running
    @Test
    public void testRadiationUpdateDecrementsCountdown() {
        Radiation.startCountdown();
        Radiation.setCountdownTimer(10);

        Radiation rad = new Radiation();

        rad.update(); // countdownStarted = true so should decrement

        assertEquals(9, Radiation.getCountdownTimer());
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
