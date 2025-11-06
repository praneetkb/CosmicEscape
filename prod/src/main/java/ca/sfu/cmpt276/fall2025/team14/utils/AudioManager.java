package ca.sfu.cmpt276.fall2025.team14.utils;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.resources.Resources;
import de.gurkenlabs.litiengine.sound.Sound;

/**
 * Controls background music and sound effects for Cosmic Escape.
 * Compatible with LITIENGINE 0.8.0.
 */
public final class AudioManager {
  private static final AudioManager INSTANCE = new AudioManager();
  private float musicVolume = 0.6f;
  private float sfxVolume = 0.8f;
  private Sound currentMusic;

  private AudioManager() {}

  public static AudioManager get() { return INSTANCE; }

  public void playMusic(String path) {
    stopMusic();
    try {
      currentMusic = Resources.sounds().get(path);
      if (currentMusic != null) {
        // Game.audio().getMusicPlayer().setVolume(musicVolume);
        Game.audio().playMusic(currentMusic);
      }
    } catch (Exception ignored) {}
  }

  public void stopMusic() {
    try { Game.audio().stopMusic(); } catch (Exception ignored) {}
    currentMusic = null;
  }

  public void playSfx(String path) {
    try {
      Sound sfx = Resources.sounds().get(path);
      if (sfx != null) {
       // Game.audio().getSoundPlayer().setVolume(sfxVolume);
        Game.audio().playSound(sfx);
      }
    } catch (Exception ignored) {}
  }

  public void setMusicVolume(float v) {
    musicVolume = clamp(v);
    // Game.audio().getMusicPlayer().setVolume(musicVolume);
  }

  public void setSfxVolume(float v) {
    sfxVolume = clamp(v);
    // Game.audio().getSoundPlayer().setVolume(sfxVolume);
  }

  private static float clamp(float v) { return Math.max(0, Math.min(1, v)); }
}

