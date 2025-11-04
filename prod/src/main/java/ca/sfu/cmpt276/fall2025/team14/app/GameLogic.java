package ca.sfu.cmpt276.fall2025.team14.app;

import ca.sfu.cmpt276.fall2025.team14.model.Alien;
import ca.sfu.cmpt276.fall2025.team14.model.Player;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.IEntity;
import de.gurkenlabs.litiengine.entities.Spawnpoint;
import de.gurkenlabs.litiengine.graphics.Camera;
import de.gurkenlabs.litiengine.graphics.LocationLockCamera;

import java.util.Collection;

public final class GameLogic {

    private GameLogic() {
    }

    public static void init() {
        // we'll use a camera in our game that is locked to the location of the player
        Camera camera = new LocationLockCamera(Player.instance());
        camera.setClampToMap(true);
        camera.setZoom(3, 0);
        Game.world().setCamera(camera);

        // add default game logic for when a level was loaded
        Game.world().onLoaded(e -> {
            // spawn the player instance on the spawn point with the name "enter"
            Spawnpoint enter = e.getSpawnpoint("player-spawn");
            if (enter != null) {
                enter.spawn(Player.instance());
            }

            Spawnpoint enemySpawn = e.getSpawnpoint("enemy-spawn");
            Collection<IEntity> enemies = Game.world().environment().getEntities();

            for (IEntity entity : enemies) {
                if (entity instanceof Alien) {
                    enemySpawn.spawn((Alien) entity);
                }
            }
        });
    }
}
