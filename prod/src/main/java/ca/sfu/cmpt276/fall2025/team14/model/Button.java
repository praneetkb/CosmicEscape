package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.Prop;
import de.gurkenlabs.litiengine.graphics.animation.IEntityAnimationController;

/**
 * Represents a generic button prop that can be pressed or released.
 * Buttons can optionally be linked to a {@link Door} for testing or gameplay,
 * allowing the button to open or close the door when interacted with.
 */
public class Button extends Prop implements IUpdateable {

    private boolean isPressed = false;
    private Door linkedDoor; // added for unit tests

    /**
     * Constructs a new Button using the specified spritesheet name.
     *
     * @param spritesheetName the name of the spritesheet for this button
     */
    public Button(String spritesheetName) {
        super("");
    }

    /**
     * Updates the button each tick.
     * When pressed, the button disables its collision.
     */
    @Override
    public void update() {
        if (isPressed) {
            this.setCollision(false);
        }
    }

    // connect door and button - added for unit tests
    public void setLinkedDoor(Door door) {
        this.linkedDoor = door;
    }

    /**
     * Links this button to a door, typically for unit testing.
     *
     * @param door the door to associate with this button
     */
    public void setLinkedDoor(Door door) {
        this.linkedDoor = door;
    }

    /**
     * Presses the button, disables its collision,
     * and opens the linked door if one is set.
     */
    public void pressButton() {
        isPressed = true;
        setCollision(false);

        if (linkedDoor != null) {
            linkedDoor.open();
        }
    }

    // added for unit tests
    public void releaseButton() {
        isPressed = false;

        if (linkedDoor != null) {
            linkedDoor.close();
        }
        if (linkedDoor != null) {
            linkedDoor.open();
        }
    }

    /**
     * Releases the button and closes the linked door if present.
     * (Used primarily for unit tests.)
     */
    public void releaseButton() {
        isPressed = false;
        if (linkedDoor != null) {
            linkedDoor.close();
        }
    }

    public boolean isPressed() {
        return isPressed;
    }

    /**
     * Creates the animation controller responsible for this button's visual behavior.
     *
     * @return a new {@link ButtonAnimationController} instance
     */
    @Override
    protected IEntityAnimationController<?> createAnimationController() {
        return new ButtonAnimationController(this);
    }
}
