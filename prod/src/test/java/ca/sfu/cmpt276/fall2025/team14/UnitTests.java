package ca.sfu.cmpt276.fall2025.team14;

import ca.sfu.cmpt276.fall2025.team14.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/*

unit tests for features -
these tests check single components in isolation without any dependencies.

remaining:
laser button - state (pressed, unpressed), collision change
turret - rotation, vision and collision
alien - vision and collision
all punishments logic
all power ups logic

*/

public class UnitTests {

    // door initial state - should be closed
    @Test
    public void testDoorInitialState() {
        Door d = new Door();
        assertFalse(d.isOpen(), "Door should start closed");
    }

    // door collision toggle (open and close)
    @Test
    public void testDoorToggle() {
        Door d = new Door();
        d.open();
        assertTrue(d.isOpen(), "Door should be opened after calling open()");
        d.close();
        assertFalse(d.isOpen(), "Door should be closed after calling close()");
    }

    // door button state - initial and toggle (pressed and unpressed)
    @Test
    public void testButtonState() {
        Button b = new Button("dummy");
        assertFalse(b.isPressed(), "Button should start unpressed");
        b.pressButton();
        assertTrue(b.isPressed(), "Button should be pressed after pressButton()");
        b.releaseButton();
        assertFalse(b.isPressed(), "Button should return to unpressed after releaseButton()");
    }

    // door button collision change
    @Test
    public void testButtonCollision() {
        Button b = new Button("dummy");
        b.pressButton();
        b.update();
        assertFalse(b.hasCollision(), "When pressed, button collision should turn off");
    }

    // player singleton
    @Test
    public void testPlayerSingleton() {
        Player p1 = Player.getInstance();
        Player p2 = Player.getInstance();
        assertSame(p1, p2, "Player.getInstance() must always return same instance");
    }

    // crystal initial state and collection
    @Test
    public void testCrystalCollection() {
        Crystal c = new Crystal();
        assertFalse(c.isCollected(), "Crystal should start as not collected in the beginning");

        c.collect();
        assertTrue(c.isCollected(), "Crystal should be collected when collect() is called");
    }

    // teleporter - initial state and collision
    @Test
    public void testTeleporterState() {
        Teleporter t = new Teleporter();
        assertFalse(t.isOpen(), "Teleporter should be closed at the start");
        assertTrue(t.hasCollision(), "Teleporter should have collision when closed");
    }

    // teleporter condition 1 to open - check crystals
    @Test
    public void testTeleporterCrystalsRemaining() {
        Teleporter t = new Teleporter();

        t.tryOpen(remainingCrystals = 3, timeRemaining = 1); // any value > 0 would work

        assertFalse(t.isOpen(), "Teleporter must stay closed if crystals still remain");
        assertTrue(t.hasCollision(), "Collision must remain on when closed");
    }

    // teleporter condition 2 to open - within time limit
    @Test
    public void testTeleporterTimeOver() {
        Teleporter t = new Teleporter();

        t.tryOpen(0, 0); // no time left

        assertFalse(t.isOpen(), "Teleporter must stay closed if time is over");
        assertTrue(t.hasCollision(), "Collision must remain on when closed");
    }

    // teleporter - both conditions met
    @Test
    public void testTeleporterConditionsMet() {
        Teleporter t = new Teleporter();

        t.tryOpen(remainingCrystals = 0, timeRemaining = 1); // any value > 0 would work

        assertTrue(t.isOpen(), "Teleporter should open when all crystals are collected and time remains");
        assertFalse(t.hasCollision(), "Collision must be off when teleporter opens");
    }
}