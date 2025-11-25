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

    public boolean isOpen() {
        return isOpen;
    }

    public void open() {
        if (!isOpen) {
            isOpen = true;
            this.setCollision(false); // allow player to pass
        }
    }

    public void close() {
        if (isOpen) {
            isOpen = false;
            this.setCollision(true); // block player
        }
    }

    public Button getButton() { return button; }
}
