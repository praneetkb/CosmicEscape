package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.Prop;
import de.gurkenlabs.litiengine.graphics.animation.IEntityAnimationController;

public class Button extends Prop implements IUpdateable {

    private boolean isPressed = false;

    public Button(String spritesheetName) {
        super("");
    }

    @Override
    public void update() { }

    public void pressButton() {
        isPressed = true;
        setCollision(false);
    }

    public boolean isPressed() {
        return isPressed;
    }

    @Override
    protected IEntityAnimationController<?> createAnimationController() {
        return new ButtonAnimationController(this);
    }
}
