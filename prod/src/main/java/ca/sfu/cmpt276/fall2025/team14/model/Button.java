package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.Prop;
import de.gurkenlabs.litiengine.graphics.animation.IEntityAnimationController;

public class Button extends Prop implements IUpdateable {

    private boolean isPressed = false;
    private Door linkedDoor; // added for unit tests

    public Button(String spritesheetName) {
        super("");
    }

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
    }

    public boolean pressed() {
        return isPressed;
    }

    @Override
    protected IEntityAnimationController<?> createAnimationController() {
        return new ButtonAnimationController(this);
    }
}
