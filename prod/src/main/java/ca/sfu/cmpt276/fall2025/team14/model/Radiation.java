package ca.sfu.cmpt276.fall2025.team14.model;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.AnimationInfo;

/**
 * Represents a radiation-based punishment that uses a shared static countdown
 * timer across all radiation tiles. The countdown ticks globally, ensuring that
 * walking across multiple radiation tiles does not reset the timer.
 */
@AnimationInfo(spritePrefix = "prop-radiation")
public class Radiation extends Punishment{

    private static final int COUNTDOWN_MAX = 800;
    private static int countdownTimer = COUNTDOWN_MAX;
    private static boolean countdownStarted = false;
    private static boolean tickerAttached = false;

    /**
     * Static ticker shared across all instances of Radiation so countdown
     * is not reset when moving from one Radiation tile to another Radiation tile.
     * This ticker is attached to the game loop once and decrements the timer
     * when the countdown has started.
     */
    private static final IUpdateable TICKER = () -> {
        if (countdownStarted) {
            if (countdownTimer > 0) {
                countdownTimer--;
            }
        }
        else { countdownTimer = COUNTDOWN_MAX; }
    };

    /**
     * Constructs a new Radiation punishment instance using the "radiation" sprite.
     */
    public Radiation() {
        super("radiation");
    }

    /**
     * Returns the punishment type associated with radiation.
     *
     * @return {@link PunishmentType#RADIATION}
     */
    @Override
    public PunishmentType getPunishmentType() {
        return PunishmentType.RADIATION;
    }

    /**
     * Updates the radiation state each tick.  
     * Ensures the static ticker is attached to the game loop exactly once and
     * manages the shared countdown behavior.
     */
    @Override
    public void update() {
        // Attach ticker to loop
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

    /**
     * Returns the maximum countdown duration.
     *
     * @return the maximum countdown value
     */
    public static int getCountdownMax() { return COUNTDOWN_MAX; }

    /**
     * Starts the global radiation countdown.
     */
    public static void startCountdown() { countdownStarted = true; }

    /**
     * Stops the global radiation countdown and resets behavior.
     */
    public static void stopCountdown() { countdownStarted = false; }

    /**
     * Indicates whether the countdown is currently active.
     *
     * @return true if the countdown is running, false otherwise
     */
    public static boolean isCountdownStarted() { return countdownStarted; }

    /**
     * Returns the current value of the shared countdown timer.
     *
     * @return the remaining countdown time
     */
    public static int getCountdownTimer() { return countdownTimer; }
}

