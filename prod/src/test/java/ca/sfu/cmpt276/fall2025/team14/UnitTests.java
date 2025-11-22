package ca.sfu.cmpt276.fall2025.team14;

import ca.sfu.cmpt276.fall2025.team14.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/*

unit tests for features -
these tests check single components in isolation without any dependencies.
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

    // laser intial state
    @Test
    public void testLaserInitialState() {
        Lasers laser = new Lasers();
        assertNotNull(laser, "Laser should be on");

        // update to attach button
        laser.update();
        assertNotNull(laser.getButton(), "Laser button should be attached after update");
    }

    // laser button initial state - should start unpressed
    @Test
    public void testLaserButtonInitialState() {
        Lasers laser = new Lasers();
        laser.update(); // attach button
        Button button = laser.getButton();

        assertFalse(button.isPressed(), "Laser button should start unpressed");
    }

    // laser button toggle (pressed and unpressed)
    @Test
    public void testLaserButtonPressRelease() {
        Lasers laser = new Lasers();
        laser.update(); // attach button
        Button button = laser.getButton();

        // press button
        button.pressButton();
        assertTrue(button.isPressed(), "Laser button should be pressed after pressButton()");

        // release button
        button.releaseButton();
        assertFalse(button.isPressed(), "Laser button should return to unpressed after releaseButton()");
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

    // turret - check if it has vision attached
    @Test
    public void testVisionAttached() {
        Turret turret = new Turret();

        assertNotNull(turret.getVision());
    }

    // math of the rays for the turret - checks that the LOS line actually moves when update is called
    @Test
    public void testVisionSyncUpdatesRays() {
        Turret turret = new Turret();
        turret.setCurrentDegree(90);

        turret.update();

        Line2D center = turret.getLosCenter();

        assertNotEquals(center.getX1(), center.getX2());
        assertNotEquals(center.getY1(), center.getY2());
    }

    // when player is caught by turret - found in LOS
    @Test
    public void testPlayerDetectedInLOS() {
        Turret turret = new Turret();
        Player player = Player.instance();

        // place turret at (0,0)
        turret.setLocation(0, 0);

        // place player within LOS in front of turret
        player.setLocation(20, 0);

        turret.setCurrentDegree(0);  // facing right
        turret.update();

        assertTrue(turret.playerInLos());
    }

    // player is not caught by turret
    @Test
    public void testPlayerNotInLOS() {
        Turret turret = new Turret();
        Player player = Player.instance();

        turret.setLocation(0, 0);
        player.setLocation(200, 200); // far away

        turret.update();

        assertFalse(turret.playerInLos());
    }

    // check if alien has vision attached
    @Test
    public void testAlienVisionAttached() {
        Alien alien = new Alien();
        assertNotNull(alien.getVision());
    }

    // when player is caught by alien - found in LOS
    @Test
    public void testPlayerDetectedInLOS() {
        Alien alien = new Alien();
        Player player = Player.instance();

        alien.setLocation(0, 0);
        player.setLocation(5, 0); // inside bounding box area

        // ensure vision sync happens
        alien.update();

        assertTrue(alien.playerInLos());
    }

    // player not caught by alien
    @Test
    public void testPlayerNotInLOS() {
        Alien alien = new Alien();
        Player player = Player.instance();

        alien.setLocation(0, 0);
        player.setLocation(500, 500); // far away

        alien.update();

        assertFalse(alien.playerInLos());
    }

    // alien collision
    @Test
    public void testCanCollideWithPlayer() {
        Alien alien = new Alien();

        Player player = Player.instance();
        player.setCollisionType(Collision.STATIC);

        assertTrue(alien.canCollideWith(player));
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
        Radiation.decrementTimer();

        assertEquals(0, Radiation.getCountdownTimer());
    }

    // slime collision - should slow down player
    @Test
    public void testSlimeCollision() {
        Player p = Player.instance();
        Slime slime = new Slime();

        GameLogic.applyPunishmentEffect(slime);

        // player should be slowed
        assertEquals(20, p.getVelocity().getBaseValue());
        assertTrue(GameLogic.isInSlime());
    }

    // laser collision - should restart level
    @Test
    public void testLaserCollision() {
        Lasers laser = new Lasers();

        GameLogic.applyPunishmentEffect(laser);

        // check that the game state has reset to INGAME (after restart)
        assertEquals(GameLogic.GameState.INGAME, GameLogic.getState());
    }

    // powerups
}