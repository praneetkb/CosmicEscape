package ca.sfu.cmpt276.fall2025.team14.model;

import ca.sfu.cmpt276.fall2025.team14.utils.ButtonAttacher;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.AnimationInfo;

/**
 * Represents a laser-based punishment hazard that disappears once its
 * associated {@link Button} is pressed. Lasers attach their button on the
 * first update tick and remove themselves from the environment when disabled.
 */
@AnimationInfo(spritePrefix = "prop-lasers")
public class Lasers extends Punishment{

    private Button button;
    private boolean active = true; // track laser state

    /**
     * Constructs a new Lasers punishment using the "prop-lasers" spritesheet.
     */
    public Lasers() {
        super("prop-lasers");
    }

    /**
     * Returns the punishment type for lasers.
     *
     * @return {@link PunishmentType#LASER}
     */
    @Override
    public PunishmentType getPunishmentType() {
        return PunishmentType.LASER;
    }

    /**
     * Updates the laser hazard each tick.
     * On first update, attaches the corresponding button.
     * When the button is pressed, the lasers remove themselves from the environment.
     */
    @Override
    public void update() {
        // Attaching button in update
        if (button == null) {
            button = ButtonAttacher.attach(this);
        }
        // Remove from environment once button is pressed
        if (button.isPressed()) {
            Game.world().environment().remove(this);
            active = false; // mark inactive - for tests
        }
    }

    /**
     * Returns the button associated with this laser hazard.
     *
     * @return the attached {@link Button}
     */
    public Button getButton() {
        return button;
    }

    //getters for testing
    public void setButton(Button button) {
        this.button = button;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
