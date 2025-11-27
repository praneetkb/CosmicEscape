package ca.sfu.cmpt276.fall2025.team14.unit;

import ca.sfu.cmpt276.fall2025.team14.IntegrationTestBase;
import ca.sfu.cmpt276.fall2025.team14.app.GameLogic;
import ca.sfu.cmpt276.fall2025.team14.model.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

public class PlayerTest extends IntegrationTestBase {

    // test player singleton
    @Test
    public void testPlayerSingleton() {
        Player p1 = Player.getInstance();
        Player p2 = Player.getInstance();
        assertSame(p1, p2); // must always return same instance
    }
}
