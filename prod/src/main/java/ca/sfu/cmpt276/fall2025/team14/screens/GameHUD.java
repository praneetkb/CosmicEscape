package ca.sfu.cmpt276.fall2025.team14.screens;

import ca.sfu.cmpt276.fall2025.team14.app.GameLogic;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.GuiComponent;
import de.gurkenlabs.litiengine.graphics.TextRenderer;
import de.gurkenlabs.litiengine.resources.Resources;

import java.awt.*;

public class GameHUD extends GuiComponent {

    private final Font hudFont;

    public GameHUD() {
        super(0, 0);

        // load font
        Font tempFont;
        try {
            tempFont = Font.createFont(
                    Font.TRUETYPE_FONT,
                    Resources.getLocation("fonts/monogram-extended-italic.ttf").openStream()
            ).deriveFont(28f);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(tempFont);
        } catch (Exception e) {
            System.err.println("Failed to load HUD font: " + e.getMessage());
            tempFont = new Font("Monospaced", Font.BOLD, 28);
        }
        hudFont = tempFont;
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);

        // for text smoothing
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int width = (int) Game.window().getResolution().getWidth();
        int height = (int) Game.window().getResolution().getHeight();

        g.setFont(hudFont);
        g.setColor(Color.WHITE);

        // HUD displays remaining time and crystals
        String timeText = "Time: " + GameLogic.getRemainingTime() + "s";
        String crystalText = "Crystals: " + GameLogic.getRemainingCrystals();

        // place it on top center
        TextRenderer.render(g, timeText, width * 0.43, height * 0.05);
        TextRenderer.render(g, crystalText, width * 0.53, height * 0.05);
    }
}