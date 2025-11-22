package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.Valign;
import de.gurkenlabs.litiengine.entities.AnimationInfo;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import static de.gurkenlabs.litiengine.Align.CENTER;

@AnimationInfo(spritePrefix = "prop-button_door_unpressed")
@CollisionInfo(collision = true, collisionBoxWidth = 20, collisionBoxHeight = 20, align = CENTER, valign = Valign.MIDDLE)
public class DoorButton extends Button{

    public DoorButton() {
        super("button");
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void pressButton() {
        super.pressButton();
    }

    public boolean isPressed() {
        return super.pressed();
    }
}
