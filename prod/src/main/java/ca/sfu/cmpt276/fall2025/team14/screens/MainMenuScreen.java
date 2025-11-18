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

public class MainMenuScreen extends GameScreen implements IUpdateable {
    //Screen name
    public final String NAME = "MAIN-MENU";
    // Scrolling background index
    private int bgIndex = 0;

    // Buttons
    private MenuButton playButton;
    private MenuButton exitButton;

    // --- Caches to avoid per-frame allocations & scaling  ---
    private int lastWBg = -1, lastHBg = -1, lastBgIndex = -1;
    private BufferedImage bgBase, bgScaled;
    private int lastWLogo = -1, lastHLogo = -1;
    private boolean lastShowTitle = true;
    private BufferedImage logoBase, logoScaled;
    private int logoScaledW, logoScaledH, xMargin;

    // --- Transition State ---
    private boolean showTitle = true;
    private boolean transitioning = false;
    private float transMs = 0f;
    private static final float FLASH_UP_MS = 100f;   // ramp up to white
    private static final float FLASH_DOWN_MS = 250f; // fade back to game

    public MainMenuScreen() {
        super("MAIN-MENU");
    }

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

    @Override
    public void suspend() {
        super.suspend();
        Game.loop().detach(this);
    }

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

    private void startTitleToMenuTransition() {
        if (transitioning || !showTitle) return;
        transitioning = true;
        transMs = 0f;
    }

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
