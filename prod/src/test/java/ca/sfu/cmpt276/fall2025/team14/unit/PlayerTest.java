package ca.sfu.cmpt276.fall2025.team14.unit;

import ca.sfu.cmpt276.fall2025.team14.IntegrationTestBase;
import ca.sfu.cmpt276.fall2025.team14.app.GameLogic;
import ca.sfu.cmpt276.fall2025.team14.model.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest extends IntegrationTestBase {

    // test player singleton
    @Test
    public void testPlayerSingleton() {
        Player p1 = Player.getInstance();
        Player p2 = Player.getInstance();
        assertSame(p1, p2); // must always return same instance
    }

    @Test
    public void testPlayerProtection() {
        Player p = Player.instance();

        // Invisible player
        p.setInvisible(true);
        assertTrue(GameLogic.testIsPlayerProtected());
        p.setInvisible(false);

        // Invulnerable player
        p.setInvulnerable(true);
        assertTrue(GameLogic.testIsPlayerProtected());
        p.setInvulnerable(false);

        // Alien Charm
        p.setHasAlienCharm(true);
        assertTrue(GameLogic.testIsPlayerProtected());
        assertFalse(p.hasAlienCharm()); // charm reset
    }

    @Test
    public void testPlayerProtectionMultiple() {
        Player p = Player.getInstance();

        // Invisible
        p.setInvisible(true);
        assertTrue(GameLogic.testIsPlayerProtected());
        p.setInvisible(false);

        // Invulnerable
        p.setInvulnerable(true);
        assertTrue(GameLogic.testIsPlayerProtected());
        p.setInvulnerable(false);

        // Alien Charm multiple calls
        p.setHasAlienCharm(true);
        assertTrue(GameLogic.testIsPlayerProtected());
        assertFalse(p.hasAlienCharm()); // charm reset
    }

}
