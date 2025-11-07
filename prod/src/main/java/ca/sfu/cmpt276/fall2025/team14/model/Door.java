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
            System.out.println("[Door] opening at " + this.getLocation()); // for debugging
            this.animations().play("open"); // defined in litidata
        }
    }

    public void close() {
        if (open) {
            open = false;
            this.setCollision(true); // block player
            System.out.println("[Door] closing at " + this.getLocation()); // for debugging
            this.animations().play("closed"); // defined in litidata
        }
    }

    @Override
    public void update() {
    }
}
