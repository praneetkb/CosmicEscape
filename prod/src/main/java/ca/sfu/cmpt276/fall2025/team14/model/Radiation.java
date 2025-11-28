package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.AnimationInfo;

@AnimationInfo(spritePrefix = "prop-radiation")
public class Radiation extends Punishment{

    private static final int COUNTDOWN_MAX = 800;
    private static int countdownTimer = COUNTDOWN_MAX;
    private static boolean countdownStarted = false;
    private static boolean tickerAttached = false;

    /**
     * Static ticker shared across all instances of Radiation so countdown
     * is not reset when moving from on Radiation tile to another Radiation tile
     */
    private static final IUpdateable TICKER = () -> {
        if (countdownStarted) {
            if (countdownTimer > 0) {
                countdownTimer--;
            }
        }
        else { countdownTimer = COUNTDOWN_MAX; }
    };

    public Radiation() {
        super("radiation");
    }

    @Override
    public PunishmentType getPunishmentType() {
        return PunishmentType.RADIATION;
    }

    @Override
    public void update() {
        // Attach ticker to leeop
        if (!tickerAttached) {
            Game.loop().attach(TICKER);
            tickerAttached = true;
        }
        // Countdown timer
        if (countdownStarted) {
            countdownTimer--;
        } else {
            // Reset countdown
            countdownTimer = COUNTDOWN_MAX;
        }
    }

    public static int getCountdownMax() { return COUNTDOWN_MAX; }
    public static void startCountdown() { countdownStarted = true; }
    public static void stopCountdown() { countdownStarted = false; }
    public static boolean isCountdownStarted() { return countdownStarted; }
    public static int getCountdownTimer() { return countdownTimer; }

    // for tests
    public static void setCountdownTimer(int countdownTimer) {
        Radiation.countdownTimer = countdownTimer;
    }

    public static void decrementTimer() { countdownTimer--; }
}
