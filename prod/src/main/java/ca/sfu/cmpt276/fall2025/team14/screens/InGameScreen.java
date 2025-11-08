package ca.sfu.cmpt276.fall2025.team14.screens;

import de.gurkenlabs.litiengine.gui.screens.GameScreen;

public class InGameScreen extends GameScreen {
    public static final String NAME = "INGAME-SCREEN";

    private GameHUD hud;

    public InGameScreen() {
        super(NAME);
    }

    @Override
    protected void initializeComponents() {
    super.initializeComponents();

    // Create and add HUD
    hud = new GameHUD();
    this.getComponents().add(hud);
    }
}
