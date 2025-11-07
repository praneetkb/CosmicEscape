package ca.sfu.cmpt276.fall2025.team14;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.GuiComponent;

import java.awt.*;

/**
 * Lightweight HUD overlay for score, timer, and health.
 * This component is purely visual; it queries the Game/Player state each frame.
 *
 * Expected data sources (per UML):
 * - Score & timer from the Game model
 * - Health from Player
 *
 * Attach this to your InGameScreen or render it in that screen's render pass.
 */
public class GameHud extends GuiComponent {

  private final GameModelFacade model;

  /**
   * @param model A thin facade that exposes score, elapsed time, and player health.
   *              Keeps HUD decoupled from the full game logic.
   */
  public GameHud(GameModelFacade model) {
    super(0, 0, Game.window().getResolution().width, 60);
    this.model = model;
  }

  @Override
  public void render(Graphics2D g) {
    // translucent bar
    g.setComposite(AlphaComposite.SrcOver.derive(0.85f));
    g.setColor(new Color(15, 15, 25));
    g.fillRect(0, 0, (int) this.getWidth(), (int) this.getHeight());
    g.setComposite(AlphaComposite.SrcOver);

    g.setColor(Color.WHITE);
    g.setFont(g.getFont().deriveFont(Font.BOLD, 18f));

    String score = "Score: " + model.getScore();
    String time  = "Time: " + formatSeconds(model.getRemainingSeconds());
    String hp    = "Health: " + model.getHealth();

    int pad = 16;
    g.drawString(score, pad, 36);

    // center time
    int w = (int) this.getWidth();
    int tw = g.getFontMetrics().stringWidth(time);
    g.drawString(time, (w - tw) / 2, 36);

    // right health
    int hw = g.getFontMetrics().stringWidth(hp);
    g.drawString(hp, w - hw - pad, 36);
  }

  private static String formatSeconds(long secs) {
    if (secs < 0) secs = 0;
    long m = secs / 60;
    long s = secs % 60;
    return String.format("%d:%02d", m, s);
  }

  /**
   * Small interface to the model layer so the HUD doesn't depend on concrete classes.
   * Implement this in your existing Game / InGameScreen code.
   */
  public interface GameModelFacade {
    int getScore();
    long getRemainingSeconds();
    int getHealth();
  }
}

