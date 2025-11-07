package ca.sfu.cmpt276.fall2025.team14.model;

/**
 * Defines the different types of power-ups available in the game.
 * This enum is used to identify power-ups and apply their specific effects.
 */
public enum PowerUpType {
    /**
     * Increases the player's movement speed for a short duration.
     */
    JETPACK,

    /**
     * Makes the player invisible to enemies for a short duration.
     */
    INVISIBILITY,

    /**
     * Temporarily stops all enemies (Aliens and Turrets) from moving.
     */
    TIMESTOP,

    /**
     * A one-time use item that saves the player from being caught by an enemy.
     */
    ALIEN_CHARM
}