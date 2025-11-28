package ca.sfu.cmpt276.fall2025.team14.screens;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.gui.ImageComponent;
import de.gurkenlabs.litiengine.resources.Resources;
import de.gurkenlabs.litiengine.util.Imaging;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A scalable animated menu button used on the main menu screen.
 * The button adjusts its size based on window resolution, updates
 * its font dynamically, and plays a hover animation with sounds.
 */
public class MenuButton extends ImageComponent implements IUpdateable {
    
    /** Base font size at the reference resolution. */
    private static final float BASE_FONT_PT = 120f;

    /** Current animation frame index. */
    private int index = 0;           // current sprite index
    
    /** Tracks whether the cursor was hovering in the previous update. */
    private boolean wasHovered = false;
   
    /** Internal throttle counter for animation speed control. */
    private int step = 0;            // simple throttle
    
    /** Number of update ticks between animation frame changes. */
    private final int stepEvery = 6; // smaller = faster animation

    /** Reference design width for proportional scaling. */
    private static final int DESIGN_W = 1920;
   
    /** Reference design height for proportional scaling. */
    private static final int DESIGN_H = 1080;
      
    /** Base button width at reference resolution. */
    private static final int BTN_W0   = 950;   // button size at 1920x1080
    
    /** Base button height at reference resolution. */
    private static final int BTN_H0   = 284;

    /**
     * Creates a new menu button with the specified position, size,
     * and displayed text. Automatically attaches to the game loop.
     *
     * @param x      the x-coordinate of the button
     * @param y      the y-coordinate of the button
     * @param width  initial width (later overridden by scaling)
     * @param height initial height (later overridden by scaling)
     * @param text   button label
     */
    public MenuButton(double x, double y, double width, double height, String text) {
        super(x, y, width, height, text);
        this.setText(text);
        Game.loop().attach(this);
    }

    /**
     * Returns the current button image, scaled to the correct size
     * for the window resolution. Also updates font scaling and
     * selects the correct animation frame based on hover state.
     *
     * @return the scaled button sprite for the current frame
     */
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

    /**
     * Updates the button animation state. Handles hover detection,
     * hover-sound playback, and advancing animation frames while
     * throttling speed for smooth visual transitions.
     */
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
