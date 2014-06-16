package be.ikkeflikkeri.game;

import be.ikkeflikkeri.engine.components.lighting.SpotLightComponent;
import be.ikkeflikkeri.engine.components.movement.FreeLookComponent;
import be.ikkeflikkeri.engine.components.rendering.CameraComponent;
import be.ikkeflikkeri.engine.core.Game;
import be.ikkeflikkeri.engine.math.Vector3f;
import be.ikkeflikkeri.engine.objects.GameObject;
import be.ikkeflikkeri.engine.rendering.Attenuation;
import be.ikkeflikkeri.engine.rendering.RenderingEngine;
import be.ikkeflikkeri.game.components.GameManagerComponent;
import be.ikkeflikkeri.game.components.LevelGeneratorComponent;
import be.ikkeflikkeri.game.components.PlayerComponent;

public class DarknessGame extends Game
{
	@Override
	public void initializeRenderingEngine(RenderingEngine renderingEngine)
	{
		renderingEngine.setAmbientLight(new Vector3f(0, 0, 0));
	}
	
	@Override
	public void initialize()
	{
		GameObject gameManager = new GameObject("GameManager");
		gameManager.addComponent(new GameManagerComponent("level1.png"));
		
		GameObject levelGenerator = new GameObject("LevelGenerator");
		levelGenerator.addComponent(new LevelGeneratorComponent());
		
		GameObject player = new GameObject("Player");
		player.addComponent(new CameraComponent(75));
		player.addComponent(new FreeLookComponent(0.5f));
		player.addComponent(new PlayerComponent(5));
		player.addComponent(new SpotLightComponent(new Vector3f(1, 1, 1), 1, new Attenuation(0, 0, 0.2f), 0.75f));
		player.getTransform().moveTo(29, 1, 54);
		player.getTransform().rotate(Vector3f.UP, 180);
		
		addObject(player);
		addObject(gameManager);
		addObject(levelGenerator);
	}
}
