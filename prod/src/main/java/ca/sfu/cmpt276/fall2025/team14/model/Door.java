package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.AnimationInfo;
import de.gurkenlabs.litiengine.entities.Prop;

@AnimationInfo(spritePrefix = "prop-door")
public class Door extends Prop implements IUpdateable {

    private boolean open = false;

    public Door() {
        super("prop-door");
        // set initial animation to closed
        this.animations().play("closed");
        this.setCollision(true); // door blocks player initially
    }

    public boolean isOpen() {
        return open;
    }

    public void open() {
        if (!open) {
            open = true;
            this.setCollision(false); // allow player to pass
            this.animations().play("open");
        }
    }

    public void close() {
        if (open) {
            open = false;
            this.setCollision(true); // block player
            this.animations().play("closed"); // back to closed
        }
    }

    @Override
    public void update() {
    }
}
