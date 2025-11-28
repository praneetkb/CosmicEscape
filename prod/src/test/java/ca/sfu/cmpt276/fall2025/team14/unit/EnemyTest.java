package ca.sfu.cmpt276.fall2025.team14.unit;

import ca.sfu.cmpt276.fall2025.team14.app.GameLogic;
import ca.sfu.cmpt276.fall2025.team14.model.Alien;
import ca.sfu.cmpt276.fall2025.team14.model.Enemy;
import ca.sfu.cmpt276.fall2025.team14.model.Turret;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EnemyTest {

    @Test
    void testEnemyConstructor() {
        Enemy e = new Enemy("enemy");
        assertNotNull(e);
    }

    @Test
    public void testPauseEnemies() {
        Alien a = new Alien();
        Turret t = new Turret();
        GameLogic.testAddEnemy(a);
        GameLogic.testAddEnemy(t);

        GameLogic.testPauseEnemies(true);
        GameLogic.testPauseEnemies(false);
    }

    @Test
    void testPlayerInLosAlwaysFalse() {
        Enemy e = new Enemy("enemy");
        assertFalse(e.playerInLos());
    }

    @Test
    void testUpdateDoesNothing() {
        Enemy e = new Enemy("enemy");
        assertDoesNotThrow(e::update);
    }
}
