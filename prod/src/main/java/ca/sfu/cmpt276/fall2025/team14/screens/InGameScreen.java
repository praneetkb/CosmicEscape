package ca.sfu.cmpt276.fall2025.team14.screens;

import ca.sfu.cmpt276.fall2025.team14.app.GameLogic;
import ca.sfu.cmpt276.fall2025.team14.model.Radiation;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.graphics.ShapeRenderer;
import de.gurkenlabs.litiengine.gui.screens.GameScreen;
import de.gurkenlabs.litiengine.resources.Resources;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * The main gameplay screen used to render the world, background,
 * HUD components, and radiation hazard effects. Handles scaling,
 * background selection, updating game state visuals, and pause display.
 */
public class InGameScreen extends GameScreen implements IUpdateable {
    //Screen name
     /** Internal screen name identifier. */
    public final String NAME = "INGAME-SCREEN";
    // Background list for random backgrounds
    /** Collection of loaded background images. */
    private static final ArrayList<BufferedImage> backgrounds = new ArrayList<>();
    /** Cached scaled background image. */
    private static BufferedImage bgScaled;
    /** Index of currently selected background. */
    private static int chosenBgIndex = -1;
    /** Last known width and height used for scaling. */
    private static int lastW = -1, lastH = -1;
    /** Heads-up display instance. */
    private GameHUD hud;
    
    /**
     * Creates a new in-game screen and loads available background images.
     * A random background is selected once during construction.
     */
    public InGameScreen() {
        super("INGAME-SCREEN");
        Game.loop().attach(this);
        // Load bg0...bg4 once
        for (int i = 0; i < 5; i++) {
            var sheet = Resources.spritesheets().get("bg" + i);
            if (sheet != null) {
                backgrounds.add(sheet.getImage());
            }
        }
        // Pick random once per screen instance
        if (!backgrounds.isEmpty()) {
            chosenBgIndex = Game.random().nextInt(backgrounds.size());
        }
    }
    
    /**
     * Initializes display components, including the in-game HUD.
     */
    @Override
    protected void initializeComponents() {
        super.initializeComponents();
        hud = new GameHUD();
        this.getComponents().add(hud);
    }
   
    /**
     * Renders the gameplay world, scaled background, overlays,
     * and radiation effects when active. Also draws HUD elements.
     *
     * @param g the graphics context to render with
     */    
    @Override
    public void render(Graphics2D g) {
        // Set width and height based on current window size
        int width = (int) Game.window().getResolution().getWidth();
        int height = (int) Game.window().getResolution().getHeight();
        // Re-scale on resolution change
        if (bgScaled == null || width != lastW || height != lastH) {
            lastW = width; lastH = height;
            if (chosenBgIndex >= 0 && chosenBgIndex < backgrounds.size()) {
                BufferedImage bg = backgrounds.get(chosenBgIndex);
                bgScaled = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = bgScaled.createGraphics();
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2.drawImage(bg, 0, 0, width, height, null);
                g2.dispose();
            }
        }
        // Draw background (if available)
        if (bgScaled != null) {
            g.drawImage(bgScaled, 0, 0, null);
        }
        // Dark overlay
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, width, height);
        // Draw world and gui
        super.render(g);
        // Green radiation overlay
        if (Radiation.isCountdownStarted()) {
            long r = Game.loop().getTicks() * 3 % 200L;
            int alpha = (int) (r <= 100 ? r : 200 - r);
            g.setColor(new Color(0, 100, 0, alpha));
            ShapeRenderer.render(g, new Rectangle2D.Double(0, 0, width, height));
        }
        if (GameLogic.getState() == GameLogic.GameState.PAUSE) {
            displayPause(g);
        }
        if (GameLogic.getState() == GameLogic.GameState.WIN) {
            endGame();
        }
    }
   
    /**
     * Updates the screen each tick. Currently empty but required for the
     * interface and future extension of gameplay updates.
     */
    @Override
    public void update() { }
    
    /**
     * Ends the game by disabling screen input and detaching update callbacks.
     * Used when transitioning away from gameplay.
     */
    private void endGame() {
        // Disable components
        setEnabled(false);
        Game.loop().detach(this);
    }
   
    /**
     * Renders the pause overlay, displaying a centered pause message
     * and flashing prompt for continuing.
     *
     * @param g graphics context used for drawing
     */
    private void displayPause(Graphics2D g) {
        // Set width and height based on current window size
        int width = (int) Game.window().getResolution().getWidth();
        int height = (int) Game.window().getResolution().getHeight();
        // Dark overlay
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, width, height);
        // Calculate font size based on default of 1920x1080
        double s  = Math.min(width / 1920.0, height / 1080.0);
        float pt  = Math.max(8f, (float) (100f * s));
        // Set scaling font under logo
        String pause = "Paused";
        String prompt = "Press ESC to continue";
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setFont(Resources.fonts().get("fonts/monogram-extended-italic.ttf").deriveFont(Font.ITALIC, pt));
        FontMetrics fm = g.getFontMetrics();
        // Pause
        int pauseTextW = fm.stringWidth(pause);
        int pauseTextH = fm.getAscent();
        int pauseX = width/2 - pauseTextW / 2;
        int pauseY = height/2;
        // Prompt
        int promptTextW = fm.stringWidth(prompt);
        int promptTextH = fm.getAscent();
        int promptX = width/2 - promptTextW / 2;
        int promptY = height/2 + pauseTextH;
        // Animate text to flash
        int period = 120;        // total ticks in a cycle
        int onWindow = 90;       // ticks to stay visible
        int t = (int) (Game.loop().getTicks() % period);
        if (t < onWindow) {
            g.setColor(new Color(2, 34, 75, 255));
            g.drawString(pause, pauseX + 2, pauseY + 2);
            g.drawString(prompt, promptX + 2, promptY + pauseTextH + 2);
            g.setColor(Color.WHITE);
            g.drawString(pause, pauseX, pauseY);
            g.drawString(prompt, promptX, promptY + promptTextH);
        }
    }
    
    /**
     * Selects a new random background and resets scaling.
     * Called when refreshing level visuals.
     */
    public static void pickNewBackground() {
        if (!backgrounds.isEmpty()) {
            chosenBgIndex = Game.random().nextInt(backgrounds.size());
            lastW = lastH = -1;
        }
    }
}
