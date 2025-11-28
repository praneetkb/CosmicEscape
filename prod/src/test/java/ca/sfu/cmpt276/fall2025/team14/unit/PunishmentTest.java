package ca.sfu.cmpt276.fall2025.team14.unit;

import ca.sfu.cmpt276.fall2025.team14.app.GameLogic;
import ca.sfu.cmpt276.fall2025.team14.model.Punishment;
import ca.sfu.cmpt276.fall2025.team14.model.Radiation;
import ca.sfu.cmpt276.fall2025.team14.model.Slime;
import ca.sfu.cmpt276.fall2025.team14.model.Lasers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PunishmentTest {

    @Test
    public void testPunishmentEffects() {

        // slime
        Punishment slime = new Slime();
        GameLogic.TestApplyPunishment(slime);
        assertTrue(GameLogic.isInSlime());

        // lasers
        Punishment laser = new Lasers();
        GameLogic.TestApplyPunishment(laser); // will call restartLevel safely

        // radiation
        Punishment radiation = new Radiation();
        GameLogic.TestApplyPunishment(radiation);
    }
}
