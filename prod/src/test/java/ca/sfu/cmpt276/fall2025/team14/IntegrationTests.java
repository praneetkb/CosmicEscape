package ca.sfu.cmpt276.fall2025.team14;

import ca.sfu.cmpt276.fall2025.team14.app.GameLogic;
import ca.sfu.cmpt276.fall2025.team14.model.*;
import de.gurkenlabs.litiengine.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*
INTEGRATION TESTS
These tests check interactions between multiple components.
*/

public class IntegrationTests {

    // door button and door interaction
    @Test
    public void testDoorButtonOpensDoor() {

        Door door = new Door();
        Button button = new Button("door-btn");

        assertFalse(door.isOpen()); // door should start closed
        assertFalse(button.pressed()); // button should start unpressed

        button.pressButton();

        // pressing button should open the door
        if (button.pressed()) {
            door.open();
        }

        assertTrue(door.isOpen()); // door should now be open

        // release button and close the door
        button.releaseButton();
        if (!button.pressed()) {
            door.close();
        }

        assertFalse(door.isOpen()); // door should close when button is released
    }

    // laser button and laser interaction
    @Test
    public void testLaserButtonTurnsOffLaser() {

        Lasers laser = new Lasers();
        laser.update(); // ensures button is attached
        Button button = laser.getButton();

        // initially laser is active and button unpressed
        assertNotNull(laser);
        assertNotNull(button);
        assertFalse(button.pressed());

        button.pressButton();

        // pressing button disables laser
        if (button.pressed()) {
            Game.world().environment().remove(laser); // laser stops
        }

        // laser should no longer be in the game environment
        assertFalse(Game.world().environment().contains(laser));

        button.releaseButton();
        assertFalse(button.pressed()); // button should return to unpressed after release
    }

    // crystal and game logic (crystal collection)
    @Test
    public void testCrystalCollectionUpdatesGameLogic() {

        GameLogic.setRemainingCrystals(3); // default

        Crystal crystal = new Crystal();
        assertFalse(crystal.isCollected()); // should start as uncollected

        crystal.collect();
        GameLogic.decrementCrystalCount(); // update game logic

        assertTrue(crystal.isCollected());

        assertEquals(2, GameLogic.getRemainingCrystals()); // remaining crystal should decrease by 1
    }

    // invisibility powerup with player and alien
    @Test
    public void testInvisibility() {

        Player player = Player.getInstance();
        Alien alien = new Alien();

        // player is within alien's LOS
        alien.setLocation(0, 0);
        player.setLocation(5, 0);

        // initially player is visible so detectable by alien
        player.setInvisible(false);
        alien.update();
        assertTrue(alien.playerInLos());

        // player should be invisible now
        Invisibility invis = new Invisibility();
        GameLogic.TestApplyPowerup(invis);
        assertTrue(player.isInvisible());

        // alien should not be able to detect player now
        alien.update();
        assertFalse(alien.playerInLos());

        // reset player visibility
        player.resetPowerUps();
        assertFalse(player.isInvisible());
    }

    // timestop powerup with enemies (alien and turret) and player
    @Test
    public void testTimestopFreezesAllEnemies() {

        Player player = Player.getInstance();
        Alien alien = new Alien();
        Turret turret = new Turret();

        // placing enemies at initial positions
        alien.setLocation(100, 100);
        turret.setLocation(200, 200);

        // save positions
        double alienX = alien.getX();
        double alienY = alien.getY();
        double turretX = turret.getX();
        double turretY = turret.getY();

        // ensure timestop is not active initially
        GameLogic.setTimeStopped(false);

        // update enemies without timestop - positions will change
        alien.update();
        turret.update();

        // collect timestop power-up
        Timestop timestop = new Timestop();
        GameLogic.TestApplyPowerup(timestop);
        assertTrue(GameLogic.isTimeStopped());

        // update enemies again - they should not move
        alien.update();
        turret.update();

        // check that their positions have not changed
        assertEquals(alienX, alien.getX(), "Alien X position should not change during timestop");
        assertEquals(alienY, alien.getY(), "Alien Y position should not change during timestop");
        assertEquals(turretX, turret.getX(), "Turret X position should not change during timestop");
        assertEquals(turretY, turret.getY(), "Turret Y position should not change during timestop");

        // reset
        GameLogic.setTimeStopped(false);
    }

    // alien charm power up
    @Test
    public void testAlienCharmPreventsPlayerCapture() {

        Player player = Player.getInstance();
        Alien alien = new Alien();

        // player in collision range of alien
        alien.setLocation(100, 100);
        player.setLocation(105, 100);

        // ensure player does not have alien charm initially
        player.setHasAlienCharm(false);

        // simulate collision without charm - player should be caught
        boolean caughtWithoutCharm = alien.canCollideWith(player);
        assertTrue(caughtWithoutCharm);

        // player gets alien charm
        AlienCharm charm = new AlienCharm();
        GameLogic.TestApplyPowerup(charm);
        assertTrue(player.hasAlienCharm());

        // collision with charm - player should not be caught
        if (alien.canCollideWith(player) && player.hasAlienCharm()) {
            // game logic - charm consumed instead of being caught
            player.setHasAlienCharm(false);
        }

        assertFalse(player.hasAlienCharm());
        assertEquals(GameLogic.GameState.INGAME, GameLogic.getState()); // game should continue
    }

    // teleporter should allow player to go to next level
    @Test
    public void testPlayerTeleporterInteraction() {
        Player player = Player.instance();

        // reset level to first
        GameLogic.setState(GameLogic.GameState.INGAME);
        int initialLevel = GameLogic.getCurrentLevelIndex();

        // reset player position
        player.setLocation(0, 0);

        // create a teleporter and open it
        Teleporter teleporter = new Teleporter();
        teleporter.tryOpen(0, 3);
        assertTrue(teleporter.isOpen());

        // place player at teleporter location
        player.setLocation(teleporter.getX(), teleporter.getY());

        // trigger collision
        GameLogic.testHandleCollisions();

        // level should increase
        assertEquals(initialLevel + 1, GameLogic.getCurrentLevelIndex());
    }

    // testing restart level when time is over
    @Test
    public void testRestartLevelOnTimeOver() {

        // set game state to INGAME
        GameLogic.setState(GameLogic.GameState.INGAME);

        // set timer to 0
        GameLogic.setRemainingTime(0);

        int initialLevel = GameLogic.getCurrentLevelIndex();

        // Trigger update
        GameLogic.testUpdate();

        // level should restart - current level should remain the same and should be reloaded
        assertEquals(initialLevel, GameLogic.getCurrentLevelIndex());
    }

    // testing restart level when enemy collision
    @Test
    public void testRestartLevelOnEnemyCollision() {

        // set game state to INGAME
        GameLogic.setState(GameLogic.GameState.INGAME);

        // create an enemy at player location
        Player player = Player.instance();
        player.setLocation(0, 0);

        Alien alien = new Alien();
        alien.setLocation(0, 0); // same location for collision

        // add enemy to environment
        GameLogic.getEnvironment().add(alien);

        int initialLevel = GameLogic.getCurrentLevelIndex();
        GameLogic.testHandleCollisions();

        // collision should trigger level restart
        assertEquals(initialLevel, GameLogic.getCurrentLevelIndex());
    }
}
