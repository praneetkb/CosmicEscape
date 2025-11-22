package ca.sfu.cmpt276.fall2025.team14.screens;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.gui.ImageComponent;
import de.gurkenlabs.litiengine.resources.Resources;
import de.gurkenlabs.litiengine.util.Imaging;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MenuButton extends ImageComponent implements IUpdateable {

    private static final float BASE_FONT_PT = 120f;

    private int index = 0;           // current sprite index
    private boolean wasHovered = false;
    private int step = 0;            // simple throttle
    private final int stepEvery = 6; // smaller = faster animation

    private static final int DESIGN_W = 1920;
    private static final int DESIGN_H = 1080;
    private static final int BTN_W0   = 950;   // button size at 1920x1080
    private static final int BTN_H0   = 284;

    public MenuButton(double x, double y, double width, double height, String text) {
        super(x, y, width, height, text);
        this.setText(text);
        Game.loop().attach(this);
    }

    @Override
    public BufferedImage getImage() {
        // Set width and height based on current window size
        double winW = Game.window().getWidth();
        double winH = Game.window().getHeight();
        int scaledW = (int) ((long) BTN_W0 * winW / DESIGN_W);
        int scaledH = (int) ((long) BTN_H0 * winH / DESIGN_H);
        this.setWidth(scaledW);
        this.setHeight(scaledH);
        // Set scaling font
        double s = Math.min(winW / (double) DESIGN_W, winH / (double) DESIGN_H);
        float pt = Math.max(8f, (float) (BASE_FONT_PT * s)); // clamp small
        this.setFont(Resources.fonts().get("fonts/monogram.ttf").deriveFont(pt));
        // Get current frame
        int frame = this.isHovered() ? index : 0;
        BufferedImage src = Resources.spritesheets().get("menu-button").getSprite(frame);

        return Imaging.scale(src, scaledW, scaledH);
    }

    @Override
    public void update() {
        if (this.isHovered()) {
            if (!wasHovered) {
                wasHovered = true;
                index = 1;
                step = 0;
                Game.audio().playSound("menu-hover").setVolume(0.5f);
                return;
            }
            // throttle frame stepping
            if (++step % stepEvery != 0) return;
            // Increment index and loop from 5-8
            if (index < 3) {
                index++; // 1 to 3
            } else if (index < 5) {
                index = 5;
            } else {
                index = 5 + ((index - 5 + 1) % 4);
            }
        } else {
            wasHovered = false;
            index = 0;
            step = 0;
        }
    }
}