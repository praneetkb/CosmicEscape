package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.Prop;
import de.gurkenlabs.litiengine.graphics.animation.IEntityAnimationController;
import de.gurkenlabs.litiengine.resources.Resources;

public class Button extends Prop implements IUpdateable {

    private boolean pressed = false;

    public Button(String spritesheetName) {
        //for debugging
        super("");
        // for debugging - to check if spritesheet is found
//        System.out.println("Sprite name: " + this.getSpritesheetName());
//        System.out.println("Sprite exists: " + Resources.spritesheets().contains(this.getSpritesheetName()));
    }

    @Override
    public void update() {
        if (pressed) {
            setCollision(false);
        }
    }

    public void pressButton() {
        pressed = true;
    }

    public boolean isPressed() {
        return pressed;
    }

    @Override
    protected IEntityAnimationController<?> createAnimationController() {

        return new ButtonAnimationController(this);
    }
}
