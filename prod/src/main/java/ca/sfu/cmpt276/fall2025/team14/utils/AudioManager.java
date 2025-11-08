package ca.sfu.cmpt276.fall2025.team14.utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Handles all in-game audio: background music and sound effects.
 * Uses pure Java Sound API (javax.sound.sampled) so it works on any JDK.
 */
public class AudioManager {

    private Clip backgroundMusicClip;
    private Clip deathClip;
    private Clip explosionClip;
    private Clip pickupClip;
    private Clip teleportClip;

    public AudioManager() {
        try {
            backgroundMusicClip = loadClip("/music/space-theme.wav");
            deathClip = loadClipWithVolume("/sfx/death.wav", 6.0f);
            explosionClip = loadClipWithVolume("/sfx/explosion.wav", 4.0f);
            pickupClip = loadClip("/sfx/pickup.wav");
            teleportClip = loadClip("/sfx/teleport.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /** Plays background music on loop until stopped. */
    public void playBackgroundMusic() {
        if (backgroundMusicClip != null) {
            backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    /** Stops background music if it’s playing. */
    public void stopBackgroundMusic() {
        if (backgroundMusicClip != null && backgroundMusicClip.isRunning()) {
            backgroundMusicClip.stop();
        }
    }

    /** Plays the death sound. */
    public void playDeathSound() {
        playSound(deathClip);
    }

    /** Plays the explosion sound. */
    public void playExplosionSound() {
        playSound(explosionClip);
    }

    /** Plays the pickup sound (resets if already playing). */
    public void playPickupSound() {
        alwaysPlaySound(pickupClip);
    }

    /** Plays the teleport sound (resets if already playing). */
    public void playTeleportSound() {
        alwaysPlaySound(teleportClip);
    }

    // --------------------------------------------------------------------
    // Helper methods
    // --------------------------------------------------------------------

    private Clip loadClip(String resourcePath)
            throws UnsupportedAudioFileException, IOException, LineUnavailableException, URISyntaxException {

        File soundFile = new File(getClass().getResource(resourcePath).toURI());
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        return clip;
    }

    private Clip loadClipWithVolume(String resourcePath, float volumeBoost)
            throws UnsupportedAudioFileException, IOException, LineUnavailableException, URISyntaxException {

        File soundFile = new File(getClass().getResource(resourcePath).toURI());
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);

        FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gain.setValue(gain.getValue() + volumeBoost);
        return clip;
    }

    /** Plays the clip only if it’s not already running. */
    private void playSound(Clip clip) {
        if (clip != null && !clip.isRunning()) {
            clip.setFramePosition(0);
            clip.start();
        }
    }

    /** Always restarts the clip from the beginning. */
    private void alwaysPlaySound(Clip clip) {
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }
    }
}

