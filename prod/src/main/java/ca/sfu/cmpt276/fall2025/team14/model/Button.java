package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.entities.Prop;
import de.gurkenlabs.litiengine.graphics.ImageRenderer;
import de.gurkenlabs.litiengine.graphics.IRenderable;
import de.gurkenlabs.litiengine.Valign;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import de.gurkenlabs.litiengine.resources.Resources;

import static de.gurkenlabs.litiengine.Align.CENTER;

@CollisionInfo(
        collision = true,
        collisionBoxWidth = 16,
        collisionBoxHeight = 16,
        align = CENTER,
        valign = Valign.MIDDLE
)
public class Button extends Prop implements IUpdateable, IRenderable {

    private boolean pressed = false;
    private final Door linkedDoor;

    private final BufferedImage buttonUpImage;
    private final BufferedImage buttonPressedImage;

    public Button(Door door) {
        super("prop-button");
        this.linkedDoor = door;

        // Load images from resources/sprites/
        this.buttonUpImage = Resources.images().get("sprites/prop-button.png");
        this.buttonPressedImage = Resources.images().get("sprites/prop-button_pressed.png");
    }

    @Override
    public void update() {
        Player player = Player.instance();

        if (player.getCollisionBox().intersects(this.getCollisionBox())) {
            if (!pressed) {
                pressed = true;
                linkedDoor.open();
            }
        } else {
            if (pressed) {
                pressed = false;
                linkedDoor.close();
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        if (pressed) {
            ImageRenderer.render(g, buttonPressedImage, this.getLocation());
        } else {
            ImageRenderer.render(g, buttonUpImage, this.getLocation());
        }
    }

    public boolean isPressed() {
        return pressed;
    }
}
