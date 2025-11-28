package ca.sfu.cmpt276.fall2025.team14.unit;

import ca.sfu.cmpt276.fall2025.team14.model.Enemy;
import ca.sfu.cmpt276.fall2025.team14.model.Turret;
import ca.sfu.cmpt276.fall2025.team14.model.Vision;
import ca.sfu.cmpt276.fall2025.team14.model.Button;
import ca.sfu.cmpt276.fall2025.team14.utils.ButtonAttacher;
import ca.sfu.cmpt276.fall2025.team14.utils.VisionAttacher;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.Prop;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class AttacherTest {

    // -------- VisionAttacher --------
    @Test
    void testVisionAttacherStaticMethods() {
        Turret dummyEnemy = new Turret();
        Vision dummyVision = new Vision();

        // attach vision to enemy
        assertDoesNotThrow(() -> VisionAttacher.attach(dummyEnemy, dummyVision));

        // sync alien vision
        Creature dummyCreature = new Creature("dummy");
        assertDoesNotThrow(() -> VisionAttacher.syncAlienVision(dummyCreature, dummyVision));

        // sync turret vision (safe with dummy values)
        assertDoesNotThrow(() -> VisionAttacher.syncTurretVision(dummyEnemy, dummyVision, 45.0));
    }

    // -------- ButtonAttacher --------
    @Test
    void testButtonAttacherStaticAttach() {
        Prop dummyProp = new Prop("test");
        // attach returns a Button (might be null if not in environment, that's fine)
        assertDoesNotThrow(() -> ButtonAttacher.attach(dummyProp));
    }
}
