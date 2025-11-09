package ca.sfu.cmpt276.fall2025.team14.model;

import ca.sfu.cmpt276.fall2025.team14.utils.ButtonAttacher;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.AnimationInfo;
import de.gurkenlabs.litiengine.entities.Prop;
import de.gurkenlabs.litiengine.graphics.animation.IEntityAnimationController;

@AnimationInfo(spritePrefix = "prop-door")
public class Door extends Prop implements IUpdateable {

    private boolean open = false;
    private Button button;

    public Door() {
        super("door");
        this.setCollision(true); // door blocks player initially
    }

    public boolean isOpen() {
        return open;
    }

    public void open() {
        if (!open) {
            open = true;
            this.setCollision(false); // allow player to pass
            System.out.println("[Door] opening at " + this.getLocation()); // for debuggingdefined in litidata
        }
    }

    public void close() {
        if (open) {
            open = false;
            this.setCollision(true); // block player
            System.out.println("[Door] closing at " + this.getLocation()); // for debuggingdefined in litidata
        }
    }

    public Button getButton() { return button; }

    @Override
    public void update() {
        if (button == null) {
            button = ButtonAttacher.attach(this);
        }
    }

    // Custom animation controller to handle frame by frame animations for open, opening, closed states
    @Override
    protected IEntityAnimationController<?> createAnimationController() {
        return new DoorAnimationController(this);
    }
}
