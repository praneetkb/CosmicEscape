package ca.sfu.cmpt276.fall2025.team14.screens;

import ca.sfu.cmpt276.fall2025.team14.app.GameLogic;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.GuiComponent;
import de.gurkenlabs.litiengine.graphics.TextRenderer;
import de.gurkenlabs.litiengine.resources.Resources;
import de.gurkenlabs.litiengine.util.Imaging;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameHUD extends GuiComponent {

    private static final float BASE_FONT_PT = 48f;
    private static final int DESIGN_W = 1920;
    private static final int DESIGN_H = 1080;
    private static final int HUD_W0   = 672;   // 96
    private static final int HUD_H0   = 98;   //14

    public GameHUD() {
        super(0, 0);
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);
        // for text smoothing
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        // Set width and height based on current window size
        int width = (int) Game.window().getResolution().getWidth();
        int height = (int) Game.window().getResolution().getHeight();
        int scaledW = (int) ((long) HUD_W0 * width / DESIGN_W);
        int scaledH = (int) ((long) HUD_H0 * height / DESIGN_H);
        // Set scaling font
        double s = Math.min(width / (double) DESIGN_W, height / (double) DESIGN_H);
        float pt = Math.max(8f, (float) (BASE_FONT_PT * s)); // clamp small
        g.setColor(Color.WHITE);
        setFont(Resources.fonts().get("fonts/monogram-extended-italic.ttf").deriveFont(pt));
        // Set hud background
        BufferedImage hudBg = Resources.spritesheets().get("hud").getImage();
        hudBg = Imaging.scale(hudBg, scaledW, scaledH);
        g.drawImage(hudBg, (int) (width - (hudBg.getWidth() * 0.75)), 0, scaledW, scaledH, null);
        // HUD displays remaining time and crystals
        String timeText = "Time: " + GameLogic.getRemainingTime() + "s";
        String crystalText = "Crystals: " + GameLogic.getRemainingCrystals();
        // Place within HUD based on HUD size
        TextRenderer.render(g, timeText, (int) (width - (hudBg.getWidth() * 0.65)), (double) hudBg.getHeight() / 2);
        TextRenderer.render(g, crystalText, (int) (width - (hudBg.getWidth() * 0.33)), (double) hudBg.getHeight() / 2);
    }
}