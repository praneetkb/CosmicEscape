package ca.sfu.cmpt276.fall2025.team14.unit;

import ca.sfu.cmpt276.fall2025.team14.app.CosmicEscape;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class CosmicEscapeTest {

    @Test
    void testConstructor() {
        assertDoesNotThrow(CosmicEscape::new);
    }
}
