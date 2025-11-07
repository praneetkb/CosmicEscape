package ca.sfu.cmpt276.fall2025.team14;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.Button;
import de.gurkenlabs.litiengine.gui.GuiComponent;
import de.gurkenlabs.litiengine.gui.screens.Screen;

import java.awt.*;

/**
 * The game's entry screen with Play, Settings, and Quit actions.
 * Integrates with LITIENGINE's ScreenManager.
 */
public class MainMenuScreen extends Screen {
  public static final String NAME = "MAIN_MENU";

  private Button playBtn;
  private Button settingsBtn;
  private Button quitBtn;

  public MainMenuScreen() {
    super(NAME);
  }

  @Override
  protected void initializeComponents() {
    final Dimension res = Game.window().getResolution();
    final int centerX = res.width / 2;
    final int baseY = res.height / 2 - 80;
    final int w = 260;
    final int h = 44;
    final int gap = 16;

    this.playBtn = new Button(centerX - w / 2, baseY, w, h, "Play");
    this.settingsBtn = new Button(centerX - w / 2, baseY + h + gap, w, h, "Settings");
    this.quitBtn = new Button(centerX - w / 2, baseY + 2 * (h + gap), w, h, "Quit");

    this.playBtn.onClicked(e -> Game.screens().display(InGameScreen.NAME));
    this.settingsBtn.onClicked(e -> Game.screens().display(SettingsScreen.NAME));
    this.quitBtn.onClicked(e -> Game.terminate());

    this.getComponents().add(this.playBtn);
    this.getComponents().add(this.settingsBtn);
    this.getComponents().add(this.quitBtn);
  }

  @Override
  public void render(Graphics2D g) {
    super.render(g);
    final Dimension res = Game.window().getResolution();

    // background
    g.setColor(new Color(10, 12, 20));
    g.fillRect(0, 0, res.width, res.height);

    // title
    g.setColor(Color.WHITE);
    g.setFont(g.getFont().deriveFont(Font.BOLD, 48f));
    String title = "Cosmic Escape";
    int tw = g.getFontMetrics().stringWidth(title);
    g.drawString(title, (res.width - tw) / 2, res.height / 3);
  }
}

