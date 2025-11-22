package ca.sfu.cmpt276.fall2025.team14.model;

import ca.sfu.cmpt276.fall2025.team14.utils.ButtonAttacher;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.AnimationInfo;
import de.gurkenlabs.litiengine.entities.Prop;
import de.gurkenlabs.litiengine.graphics.animation.IEntityAnimationController;

@AnimationInfo(spritePrefix = "prop-door")

public class Door extends Prop implements IUpdateable {

    private boolean isOpen = false;
    private Button button;

    public Door() {
        super("door");
        this.setCollision(true); // door blocks player initially
    }

    // already there before - also used by unit test
    public boolean isOpen() {
        return open;
    }

    // already there before - also used by unit test
    public void open() {
        if (!open) {
            open = true;
            this.setCollision(false); // allow player to pass
            System.out.println("[Door] opening at " + this.getLocation()); // for debugging
        }
    }

    // already there before - also used by unit test
    public void close() {
        if (open) {
            open = false;
            this.setCollision(true); // block player
            System.out.println("[Door] closing at " + this.getLocation()); // for debugging
        }
    }

    @Override
    public void update() {
        // Attaching button in update
        if (button == null) {
            button = ButtonAttacher.attach(this);
        }
    }

    @Override
    protected IEntityAnimationController<?> createAnimationController() {
        // Custom animation controller to handle frame by frame animations for open, opening, closed states
        return new DoorAnimationController(this);
    }

    public Button getButton() {
        return button;
    }
}
