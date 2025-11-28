package ca.sfu.cmpt276.fall2025.team14.screens;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.graphics.ImageRenderer;
import de.gurkenlabs.litiengine.gui.screens.GameScreen;
import de.gurkenlabs.litiengine.input.Input;
import de.gurkenlabs.litiengine.resources.Resources;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;


/**
 * Win screen displayed after the player completes the game.
 * Shows an animated scrolling background, the game logo,
 * and a flashing end message. Also listens for user input
 * to exit the game.
 */
public class WinScreen extends GameScreen implements IUpdateable {

    //Screen name
    /** Name identifier for the win screen. */
    public final String NAME = "WIN-SCREEN";

    // Scrolling background index
    /** Current background animation frame index. */
    private int bgIndex = 0;

    // --- Caches to avoid per-frame allocations & scaling  ---
    /** Cached values for efficiently scaling the background. */
    private int lastWBg = -1, lastHBg = -1, lastBgIndex = -1;

    /** Base and scaled background images. */
    private BufferedImage bgBase, bgScaled;

    /** Cached values for efficiently scaling the logo. */
    private int lastWLogo = -1, lastHLogo = -1;

    /** Tracks when the logo needs to be refreshed. */
    private boolean lastShowTitle = true;

    /** Base and scaled logo images. */
    private BufferedImage logoBase, logoScaled;

    /** Scaled logo width and height. */
    private int logoScaledW, logoScaledH;

    /**
     * Creates a win screen instance.
     */
    public WinScreen() {
        super("WIN-SCREEN");
    }

    /**
     * Initializes screen components including the logo image and
     * the initial background frame.
     */
    @Override
    protected void initializeComponents() {
        super.initializeComponents();
        this.logoBase = Resources.spritesheets().get("logo.png").getImage();
        this.bgBase = Resources.spritesheets().get("scrolling_bg.png").getSprite(bgIndex);
    }

    /**
     * Prepares the win screen by attaching update logic, fading in,
     * and binding ESC to exit the game.
     */
    @Override
    public void prepare() {
        super.prepare();
        Game.loop().attach(this);
        // Fade in
        Game.window().getRenderComponent().fadeIn(500);
        Game.loop().perform(1000, () -> Input.keyboard().onKeyReleased(KeyEvent.VK_ESCAPE, e-> System.exit(0)));
    }

    /**
     * Renders the animated background, scaled logo, and flashing
     * end-game message centered beneath the logo.
     *
     * @param g the graphics context used for drawing
     */
    @Override
    public void render(Graphics2D g) {
        // Set width and height based on current window size
        int w = (int) Game.window().getResolution().getWidth();
        int h = (int) Game.window().getResolution().getHeight();
        // Re-scale on resolution change
        if (w != lastWBg || h != lastHBg || bgIndex != lastBgIndex) {
            lastWBg = w; lastHBg = h; lastBgIndex = bgIndex;
            bgBase = Resources.spritesheets().get("scrolling_bg.png").getSprite(bgIndex);
            bgScaled = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = bgScaled.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(bgBase, 0, 0, w, h, null);
            g2.dispose();
        }
        // Draw background
        g.drawImage(bgScaled, 0, 0, null);
        // --- Logo cache ---
        if (w != lastWLogo || h != lastHLogo || lastShowTitle != true) {
            lastWLogo = w; lastHLogo = h; lastShowTitle = true;
            // Scale logo based on 1920x1080
            int baseLogoW = logoBase.getWidth();
            int baseLogoH = logoBase.getHeight();
            logoScaledW = (baseLogoW * w) / 1920;
            logoScaledH = (baseLogoH * h) / 1080;
            // Get scaled logo
            logoScaled = new BufferedImage(logoScaledW, logoScaledH, BufferedImage.TYPE_INT_ARGB);
            Graphics2D lg = logoScaled.createGraphics();
            lg.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            lg.drawImage(logoBase, 0, 0, logoScaledW, logoScaledH, null);
            lg.dispose();
        }
        // Center logo
        int cx = w / 2 - logoScaledW / 2;
        int cy = logoScaledH / 3;
        ImageRenderer.render(g, logoScaled, cx, cy);
        // Calculate font size based on default of 1920x1080
        double s  = Math.min(w / 1920.0, h / 1080.0);
        float pt  = Math.max(8f, (float) (100f * s));
        // Set scaling font under logo
        String endingMessage = "Thanks for playing!";
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setFont(Resources.fonts().get("fonts/monogram-extended-italic.ttf").deriveFont(Font.ITALIC, pt));
        FontMetrics fm = g.getFontMetrics();
        int textW = fm.stringWidth(endingMessage);
        int textH = fm.getAscent();
        int padY  = (int) Math.round(32 * s);
        int tx = w / 2 - textW / 2;
        int ty = cy + logoScaledH + padY + textH;
        // Animate text to flash
        int period = 120;        // total ticks in a cycle
        int onWindow = 90;       // ticks to stay visible
        int t = (int) (Game.loop().getTicks() % period);
        if (t < onWindow) {
            g.setColor(new Color(2, 34, 75, 255));
            g.drawString(endingMessage, tx + 2, ty + 2);
            g.setColor(Color.WHITE);
            g.drawString(endingMessage, tx, ty);
        }
    }

    /**
     * Update method required by {@link IUpdateable}.  
     * Currently unused but kept for future expansion.
     */
    @Override
    public void update() {

    }
}
