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
        assertFalse(d.isOpen());
    }

    // door collision toggle (open and close)
    @Test
    public void testDoorToggle() {
        Door d = new Door();
        d.open();
        assertTrue(d.isOpen()); // should be opened after calling open()
        d.close();
        assertFalse(d.isOpen()); // should be closed
    }

    // door button state - initial and toggle (pressed and unpressed)
    @Test
    public void testButtonState() {
        Button b = new Button("dummy");
        assertFalse(b.isPressed()); // should start unpressed
        b.pressButton();
        assertTrue(b.isPressed()); // should be pressed
        b.releaseButton();
        assertFalse(b.isPressed()); // should return to unpressed
    }

    // door button collision change
    @Test
    public void testButtonCollision() {
        Button b = new Button("dummy");
        b.pressButton();
        b.update();
        assertFalse(b.hasCollision()); // when pressed, button collision should turn off
    }

    // laser intial state
    @Test
    public void testLaserInitialState() {
        Lasers laser = new Lasers();
        assertNotNull(laser); // should be on

        // update - to attach button
        laser.update();
        assertNotNull(laser.getButton());
    }

    // laser button initial state - should start unpressed
    @Test
    public void testLaserButtonInitialState() {
        Lasers laser = new Lasers();
        laser.update(); // attach button
        Button button = laser.getButton();

        assertFalse(button.isPressed());
    }

    // laser button toggle (pressed and unpressed)
    @Test
    public void testLaserButtonPressRelease() {
        Lasers laser = new Lasers();
        laser.update(); // attach button
        Button button = laser.getButton();

        // press button
        button.pressButton();
        assertTrue(button.isPressed());

        // release button
        button.releaseButton();
        assertFalse(button.isPressed());
    }

    // player singleton
    @Test
    public void testPlayerSingleton() {
        Player p1 = Player.getInstance();
        Player p2 = Player.getInstance();
        assertSame(p1, p2); // must always return same instance
    }

    // crystal initial state and collection
    @Test
    public void testCrystalCollection() {
        Crystal c = new Crystal();
        assertFalse(c.isCollected()); // should start as not collected

        c.collect();
        assertTrue(c.isCollected()); // should be collected
    }

    // teleporter - initial state and collision
    @Test
    public void testTeleporterState() {
        Teleporter t = new Teleporter();
        assertFalse(t.isOpen()); // should be closed at the start
        assertTrue(t.hasCollision()); //  should have collision when closed
    }

    // teleporter condition 1 to open - check crystals
    @Test
    public void testTeleporterCrystalsRemaining() {
        Teleporter t = new Teleporter();

        t.tryOpen(remainingCrystals = 3, timeRemaining = 1); // any value > 0 would work

        assertFalse(t.isOpen()); // must stay closed if crystals still remain
        assertTrue(t.hasCollision()); // collision must remain on when closed
    }

    // teleporter condition 2 to open - within time limit
    @Test
    public void testTeleporterTimeOver() {
        Teleporter t = new Teleporter();

        t.tryOpen(0, 0); // no time left

        assertFalse(t.isOpen()); // must stay closed if time is over
        assertTrue(t.hasCollision()); // collision must remain on when closed
    }

    // teleporter - both conditions met
    @Test
    public void testTeleporterConditionsMet() {
        Teleporter t = new Teleporter();

        t.tryOpen(remainingCrystals = 0, timeRemaining = 1); // any value > 0 would work

        assertTrue(t.isOpen()); // should open when all crystals are collected and time remains
        assertFalse(t.hasCollision()); // collision must be off when teleporter opens
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

    // invisibility powerup on player
    @Test
    public void testPlayerBecomesInvisible() {
        Player player = Player.instance();
        player.resetPowerUps(); // make sure player starts visible

        assertFalse(player.isInvisible();

        // simulate effect
        player.setInvisible(true);
        assertTrue(player.isInvisible()); // player should be invisible after collecting the powerup

        // reset
        player.resetPowerUps();
        assertFalse(player.isInvisible()); // player should be visible after reset
    }

    // jetpack powerup on player
    @Test
    public void testJetpack() {
        Player player = Player.instance();
        player.resetPowerUps(); // ensure default speed of player
        float defaultSpeed = Player.getPlayerDefaultVelocity();

        assertEquals(defaultSpeed, player.getVelocity().get());

        // simulate effect
        float boostedSpeed = defaultSpeed * 1.5f; // same as GameLogic
        player.getVelocity().setBaseValue(boostedSpeed);
        assertEquals(boostedSpeed, player.getVelocity().get()); // player speed should increase

        // Reset
        player.resetPowerUps();
        assertEquals(defaultSpeed, player.getVelocity().get()); // default after reset
    }

    // timestop power up
    @Test
    public void testTimestop() {
        // timestop should not be active initially
        assertFalse(GameLogic.isTimeStopped());
        Timestop timestop = new Timestop();
        GameLogic.applyPowerUpEffect(timestop);

        // should be active after collecting
        assertTrue(GameLogic.isTimeStopped());

        // should be deactivated
        GameLogic.isTimeStopped(false);
        assertFalse(GameLogic.isTimeStopped());
    }

    // alien charm powerup
    @Test
    public void testAlienCharm() {
        Player player = Player.instance();

        // ensure player does not have alien charm initially
        player.setHasAlienCharm(false);
        assertFalse(player.hasAlienCharm());
        AlienCharm charm = new AlienCharm();
        GameLogic.applyPowerUpEffect(charm);
        assertTrue(player.hasAlienCharm());
        player.setHasAlienCharm(false);
        assertFalse(player.hasAlienCharm());
    }
}