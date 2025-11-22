package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.Valign;
import de.gurkenlabs.litiengine.entities.AnimationInfo;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import static de.gurkenlabs.litiengine.Align.CENTER;

@AnimationInfo(spritePrefix = "prop-button_laser_unpressed")
@CollisionInfo(collision = true, collisionBoxWidth = 14, collisionBoxHeight = 14, align = CENTER, valign = Valign.MIDDLE)
public class LaserButton extends Button{

    public LaserButton() {
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
