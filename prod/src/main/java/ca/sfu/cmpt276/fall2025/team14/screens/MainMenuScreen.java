package ca.sfu.cmpt276.fall2025.team14.screens;

import ca.sfu.cmpt276.fall2025.team14.app.GameLogic;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.graphics.ImageRenderer;
import de.gurkenlabs.litiengine.gui.screens.GameScreen;
import de.gurkenlabs.litiengine.input.Input;
import de.gurkenlabs.litiengine.resources.Resources;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Main menu screen containing the title screen, transition animation,
 * scrolling background, logo, and interactive buttons for starting
 * the game or exiting. Handles transitions between the title prompt
 * and the visible Play/Exit menu.
 */
public class MainMenuScreen extends GameScreen implements IUpdateable {
    //Screen name
    /** Internal screen identifier name. */
    public final String NAME = "MAIN-MENU";
    // Scrolling background index
    /** Index of the currently displayed scrolling background frame. */
    private int bgIndex = 0;

    // Buttons
    /** Play button displayed in the main menu. */
    private MenuButton playButton;
    /** Exit button displayed in the main menu. */
    private MenuButton exitButton;

    // --- Caches to avoid per-frame allocations & scaling  ---
    /** Cached previously used screen dimensions for background scaling. */
    private int lastWBg = -1, lastHBg = -1, lastBgIndex = -1;
    
    /** Base and scaled versions of the background. */
    private BufferedImage bgBase, bgScaled;
    
    /** Cached logo scaling values. */
    private int lastWLogo = -1, lastHLogo = -1;
    private boolean lastShowTitle = true;
    private BufferedImage logoBase, logoScaled;
    private int logoScaledW, logoScaledH, xMargin;

    // --- Transition State ---
    /** Whether the screen is showing the title rather than the menu. */
    private boolean showTitle = true;
    
    /** Whether a transition animation is happening. */
    private boolean transitioning = false;
    
    /** Transition animation timer in milliseconds. */
    private float transMs = 0f;

    /** Time in ms for the flash to brighten. */
    private static final float FLASH_UP_MS = 100f;   // ramp up to white
      
    /** Time in ms for the flash to fade. */
    private static final float FLASH_DOWN_MS = 250f; // fade back to game

    /**
     * Creates a new main menu screen.
     */    
    public MainMenuScreen() {
        super("MAIN-MENU");
    }


    /**
     * Initializes the menu buttons and base logo image.
     */    
    @Override
    protected void initializeComponents() {
        super.initializeComponents();
        //Buttons
        this.playButton = new MenuButton(0, 0, 0, 0, "Play");
        this.exitButton = new MenuButton(0, 0, 0, 0, "Exit");
        this.getComponents().add(playButton);
        this.getComponents().add(exitButton);
        // load base logo
        this.logoBase = Resources.spritesheets().get("logo.png").getImage();
    }

    /**
     * Prepares the main menu by attaching update loops, loading music,
     * setting button click handlers, and controlling transitions from
     * the title screen to the menu.
     */    
    @Override
    public void prepare() {
        super.prepare();
        Game.loop().attach(this);
        // Prepare main menu
        this.getComponents().forEach(c ->
                c.onClicked(e -> Game.audio().playSound("menu-selection").setVolume(0.2f)));
        playButton.onClicked(e -> startGame());
        exitButton.onClicked(e -> System.exit(0));
        playButton.setVisible(false);
        exitButton.setVisible(false);
        // Any input from keyboard or mouse will show main menu
        Input.keyboard().onKeyReleased(e -> startTitleToMenuTransition());
        Input.mouse().onClicked(e -> startTitleToMenuTransition());
        // Music
        Game.audio().playMusic("menu-music").setVolume(0.4f);
        //Fade in
        Game.window().getRenderComponent().fadeIn(500);
    }

    /**
     * Renders the background, title prompt, transition animation,
     * or the Play/Exit menu depending on the screen's state.
     *
     * @param g the graphics context used for drawing
     */
    @Override
    public void render(Graphics2D g) {
        super.render(g);
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
        // Background overlay to darken
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, w, h);
        // Title menu/main menu
        if (showTitle) {
            showTitle(g);
        } else {
            showMenu(g);
        }
        // White flash transition
        if (transitioning) {
            float a;
            if (transMs <= FLASH_UP_MS) {
                a = transMs / FLASH_UP_MS;
            } else {
                float t = (transMs - FLASH_UP_MS) / FLASH_DOWN_MS;
                a = Math.max(0f, 1f - t);
            }
            int alpha = Math.min(255, Math.max(0, (int) (a * 255)));
            g.setColor(new Color(255, 255, 255, alpha));
            g.fillRect(0, 0, w, h);
        }
    }


    /**
     * Updates the animated background and manages the timing of
     * the screen transition between the title and main menu.
     */
    @Override
    public void update() {
        if (Game.loop().getTicks() % 4 == 0) {
            bgIndex = (bgIndex + 1) % 320;
        }
        if (transitioning) {
            transMs += Game.loop().getDeltaTime(); // ms
            // Halfway through flash, switch to main menu so it appears under the fade-out
            if (showTitle && transMs >= FLASH_UP_MS) {
                Game.audio().playSound("menu-selection").setVolume(0.1f);
                showTitle = false;
                playButton.setVisible(true);
                exitButton.setVisible(true);
            }
            if (transMs >= FLASH_UP_MS + FLASH_DOWN_MS) {
                transitioning = false; // done
                transMs = 0f;
            }
        }
    }


    /**
     * Detaches the menu's update loop when suspended.
     */
    @Override
    public void suspend() {
        super.suspend();
        Game.loop().detach(this);
    }


    /**
     * Starts the game by fading out the menu, switching screens,
     * loading the level, and starting music.
     */
    private void startGame() {
        // Disable components
        setEnabled(false);
        // Fade out screen and music
        Game.window().getRenderComponent().fadeOut(1500);
        Game.audio().fadeMusic(1500);
        // Transition to game
        Game.loop().perform(1500, () -> {
            Game.window().getRenderComponent().fadeIn(1500);
            Game.screens().display("INGAME-SCREEN");
            GameLogic.setState(GameLogic.GameState.INGAME);
            GameLogic.loadLevel();
            Game.audio().playMusic("ingame-music").setVolume(0.4f);
        });
    }

    /**
     * Begins the transition from title screen to main menu.
     */
    private void startTitleToMenuTransition() {
        if (transitioning || !showTitle) return;
        transitioning = true;
        transMs = 0f;
    }


    /**
     * Renders the title screen showing the logo and “Press any key”
     * flashing prompt.
     *
     * @param g the graphics context used for drawing
     */
    private void showTitle(Graphics2D g) {
        // Disable buttons
        playButton.setVisible(false);
        exitButton.setVisible(false);
        // Set width and height based on current window size
        int w = (int) Game.window().getResolution().getWidth();
        int h = (int) Game.window().getResolution().getHeight();
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
        String startText = "Press any key to continue";
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setFont(Resources.fonts().get("fonts/monogram-extended-italic.ttf").deriveFont(Font.ITALIC, pt));
        FontMetrics fm = g.getFontMetrics();
        int textW = fm.stringWidth(startText);
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
            g.drawString(startText, tx + 2, ty + 2);
            g.setColor(Color.WHITE);
            g.drawString(startText, tx, ty);
        }
    }

    /**
     * Renders the main menu layout including scaled logo and
     * Play/Exit buttons.
     *
     * @param g the graphics context used for drawing
     */
    private void showMenu(Graphics2D g) {
        // Set width and height based on current window size
        int w = (int) Game.window().getResolution().getWidth();
        int h = (int) Game.window().getResolution().getHeight();
        // --- Logo cache ---
        if (w != lastWLogo || h != lastHLogo || lastShowTitle != false) {
            lastWLogo = w; lastHLogo = h; lastShowTitle = false;
            // Scale logo based on 1920x1080
            int baseLogoW = logoBase.getWidth();
            int baseLogoH = logoBase.getHeight();
            logoScaledW = (baseLogoW * w) / 1920;
            logoScaledH = (baseLogoH * h) / 1080;
            xMargin     = (20 * w) / 1920;
            // Get scaled logo
            logoScaled = new BufferedImage(logoScaledW, logoScaledH, BufferedImage.TYPE_INT_ARGB);
            Graphics2D lg = logoScaled.createGraphics();
            lg.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            lg.drawImage(logoBase, 0, 0, logoScaledW, logoScaledH, null);
            lg.dispose();
        }
        // Draw Menu logo
        ImageRenderer.render(g, logoScaled, xMargin, 0);
        // Buttons
        playButton.setVisible(true);
        exitButton.setVisible(true);
        playButton.setLocation(xMargin, logoScaledH);
        exitButton.setLocation(xMargin, logoScaledH + exitButton.getHeight());
        playButton.render(g);
        exitButton.render(g);
    }
}
