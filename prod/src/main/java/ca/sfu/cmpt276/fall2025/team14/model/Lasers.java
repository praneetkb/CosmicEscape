package ca.sfu.cmpt276.fall2025.team14.model;

import ca.sfu.cmpt276.fall2025.team14.utils.ButtonAttacher;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.AnimationInfo;

@AnimationInfo(spritePrefix = "prop-lasers")
public class Lasers extends Punishment{

    private Button button;

    public Lasers() {
        super("prop-lasers");
    }

    @Override
    public PunishmentType getPunishmentType() {
        return PunishmentType.LASER;
    }

    @Override
    public void update() {
        // Attaching button in update
        if (button == null) {
            button = ButtonAttacher.attach(this);
        }
        // Remove from environment once button is pressed
        if (button.pressed()) {
            Game.world().environment().remove(this);
        }
    }

    // getter for testing
    public Button getButton() {
        return button;
    }
}
