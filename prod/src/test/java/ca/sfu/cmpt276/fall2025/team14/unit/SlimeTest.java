package ca.sfu.cmpt276.fall2025.team14.unit;

import ca.sfu.cmpt276.fall2025.team14.IntegrationTestBase;
import ca.sfu.cmpt276.fall2025.team14.app.GameLogic;
import ca.sfu.cmpt276.fall2025.team14.model.Player;
import ca.sfu.cmpt276.fall2025.team14.model.Slime;
import ca.sfu.cmpt276.fall2025.team14.model.PunishmentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SlimeTest extends IntegrationTestBase {

    @BeforeEach
    public void resetPlayer() {
        Player player = Player.getInstance();
        player.resetPowerUps();
    }

    @Test
    void testSlimeConstructor() {
        Slime s = new Slime();
        assertNotNull(s);
    }

    @Test
    void testGetPunishmentType() {
        Slime s = new Slime();
        assertEquals(PunishmentType.SLIME, s.getPunishmentType());
    }

    @Test
    void testUpdateDoesNothing() {
        Slime s = new Slime();
        assertDoesNotThrow(s::update);
    }

    // test slime collision - should slow down player
    @Test
    public void testSlimeCollision() {

        Player p = Player.instance();
        Slime slime = new Slime();

        float defaultSpeed = p.getVelocity().getBase(); // read actual default
        GameLogic.TestApplyPunishment(slime);

        // player should be slowed
        assertEquals(20, p.getVelocity().getBase().intValue());;
        assertTrue(GameLogic.isInSlime());
    }
}
