package ca.sfu.cmpt276.fall2025.team14;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.Button;
import de.gurkenlabs.litiengine.gui.Label;
import de.gurkenlabs.litiengine.gui.screens.Screen;

import java.awt.*;

/**
 * Settings screen for audio volumes and a quick back action.
 * Uses simple +/- controls to avoid engine-specific slider dependencies.
 */
public class SettingsScreen extends Screen {
  public static final String NAME = "SETTINGS";

  private Label musicLabel;
  private Button musicDown;
  private Button musicUp;

  private Label sfxLabel;
  private Button sfxDown;
  private Button sfxUp;

  private Button backBtn;

  public SettingsScreen() {
    super(NAME);
  }

  @Override
  protected void initializeComponents() {
    final Dimension res = Game.window().getResolution();
    final int centerX = res.width / 2;
    final int yStart = res.height / 3;

    musicLabel = new Label(centerX - 200, yStart, 400, 40, () -> "Music Volume: " + percent(GameAudio.get().getMusicVolume()));
    musicDown = new Button(centerX - 210, yStart + 48, 100, 36, "-");
    musicUp   = new Button(centerX + 110, yStart + 48, 100, 36, "+");

    sfxLabel = new Label(centerX - 200, yStart + 120, 400, 40, () -> "SFX Volume: " + percent(GameAudio.get().getSfxVolume()));
    sfxDown = new Button(centerX - 210, yStart + 168, 100, 36, "-");
    sfxUp   = new Button(centerX + 110, yStart + 168, 100, 36, "+");

    backBtn = new Button(centerX - 130, yStart + 250, 260, 44, "Back");

    musicDown.onClicked(e -> adjustMusic(-0.1f));
    musicUp.onClicked(e -> adjustMusic(+0.1f));
    sfxDown.onClicked(e -> adjustSfx(-0.1f));
    sfxUp.onClicked(e -> adjustSfx(+0.1f));
    backBtn.onClicked(e -> Game.screens().display(MainMenuScreen.NAME));

    this.getComponents().add(musicLabel);
    this.getComponents().add(musicDown);
    this.getComponents().add(musicUp);
    this.getComponents().add(sfxLabel);
    this.getComponents().add(sfxDown);
    this.getComponents().add(sfxUp);
    this.getComponents().add(backBtn);
  }

  private void adjustMusic(float delta) {
    float v = clamp01(GameAudio.get().getMusicVolume() + delta);
    GameAudio.get().setMusicVolume(v);
  }

  private void adjustSfx(float delta) {
    float v = clamp01(GameAudio.get().getSfxVolume() + delta);
    GameAudio.get().setSfxVolume(v);
  }

  private static float clamp01(float v) {
    return Math.max(0f, Math.min(1f, v));
  }

  private static String percent(float v) {
    return Math.round(v * 100) + "%";
  }

  @Override
  public void render(Graphics2D g) {
    super.render(g);
    final Dimension res = Game.window().getResolution();
    g.setColor(new Color(8, 10, 16));
    g.fillRect(0, 0, res.width, res.height);

    g.setColor(Color.WHITE);
    g.setFont(g.getFont().deriveFont(Font.BOLD, 36f));
    String title = "Settings";
    int tw = g.getFontMetrics().stringWidth(title);
    g.drawString(title, (res.width - tw) / 2, res.height / 4);
  }
}

