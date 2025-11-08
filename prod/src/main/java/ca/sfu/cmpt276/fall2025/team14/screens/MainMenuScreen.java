package ca.sfu.cmpt276.fall2025.team14.screens;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.screens.GameScreen;
import de.gurkenlabs.litiengine.input.Input;
import de.gurkenlabs.litiengine.resources.Resources;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MainMenuScreen extends GameScreen {

    private final String title = "Welcome to Cosmic Escape";
    private Font mainFont;

    public MainMenuScreen() {
        super("MainMenu");
    }

    @Override
    public void prepare() {

        // load custom font
        if (mainFont == null) {
            try {
                mainFont = Font.createFont(
                        Font.TRUETYPE_FONT,
                        Resources.getLocation("fonts/monogram-extended-italic.ttf").openStream()
                ).deriveFont(32f);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(mainFont);
            } catch (Exception e) {
                System.err.println("Failed to load custom font: " + e.getMessage());
                mainFont = new Font("Monospaced", Font.BOLD, 32);
            }
        }

        Input.keyboard().onKeyPressed(this::onKeyPressed);
    }

    @Override
    public void render(Graphics2D g) {
        int width = (int) Game.window().getResolution().getWidth();
        int height = (int) Game.window().getResolution().getHeight();

        // background image
        Image bg = Resources.images().get("sprites/background.png");
        if (bg != null) {
            g.drawImage(bg, 0, 0, width, height, null);
        }

        // overlay
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, width, height);

        // for "title" text
        g.setColor(Color.WHITE);
        g.setFont(mainFont.deriveFont(48f));
        int titleWidth = g.getFontMetrics().stringWidth(title);
        g.drawString(title, (width - titleWidth) / 2, height / 3);

        // for "start the game" text
        String startText = "Press ENTER to Start";
        g.setFont(mainFont.deriveFont(28f));
        int startWidth = g.getFontMetrics().stringWidth(startText);
        g.drawString(startText, (width - startWidth) / 2, height / 2);
    }

    protected void onKeyPressed(KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.VK_ENTER || event.getKeyCode() == KeyEvent.VK_SPACE) {
            // Game.audio().playSound(""); --> WILL ADD AUDIO LATER
            startGame();
        }
    }

    private void startGame() {
        Game.screens().display("INGAME-SCREEN");
    }
}
