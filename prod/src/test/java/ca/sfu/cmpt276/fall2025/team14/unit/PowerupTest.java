package ca.sfu.cmpt276.fall2025.team14.unit;

import ca.sfu.cmpt276.fall2025.team14.app.GameLogic;
import ca.sfu.cmpt276.fall2025.team14.model.AlienCharm;
import ca.sfu.cmpt276.fall2025.team14.model.Player;
import ca.sfu.cmpt276.fall2025.team14.model.Powerup;
import ca.sfu.cmpt276.fall2025.team14.model.PowerUpType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PowerupTest {

    // test all powerups
    @Test
    public void testPowerupEffects() {
        Player player = Player.instance();

        // Alien Charm
        Powerup charm = new AlienCharm();
        GameLogic.TestApplyPowerup(charm);
        assertTrue(player.hasAlienCharm());

        // TimeStop
        Powerup timeStop = new Powerup("timestop") {
            @Override
            public PowerUpType getType() { return PowerUpType.TIMESTOP; }
        };
        GameLogic.TestApplyPowerup(timeStop);
        assertTrue(GameLogic.isTimeStopped());

        // Jetpack
        Powerup jetpack = new Powerup("jetpack") {
            @Override
            public PowerUpType getType() { return PowerUpType.JETPACK; }
        };
        GameLogic.TestApplyPowerup(jetpack);

        // Invisibility
        Powerup invis = new Powerup("invis") {
            @Override
            public PowerUpType getType() { return PowerUpType.INVISIBILITY; }
        };
        GameLogic.TestApplyPowerup(invis);
        assertTrue(player.isInvisible());
    }

    // for unknown
    @Test
    public void testApplyUnknownPowerup() {
        Powerup unknown = new Powerup("unknown") {
            @Override
            public PowerUpType getType() { return null; }
        };
        GameLogic.TestApplyPowerup(unknown); // should not throw
    }

    // minimal concrete subclass for testing abstract Powerup
    static class TestPowerup extends Powerup {
        public TestPowerup() {
            super("test");
        }

        @Override
        public PowerUpType getType() {
            return PowerUpType.JETPACK; // dummy
        }
    }

    @Test
    void testConstructorAndUpdate() {
        TestPowerup p = new TestPowerup();
        assertDoesNotThrow(p::update);
    }
}
