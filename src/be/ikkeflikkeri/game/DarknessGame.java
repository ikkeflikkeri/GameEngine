package be.ikkeflikkeri.game;

import be.ikkeflikkeri.engine.core.Game;
import be.ikkeflikkeri.engine.math.Vector3f;
import be.ikkeflikkeri.engine.objects.GameObject;
import be.ikkeflikkeri.engine.objects.rendering.CameraObject;
import be.ikkeflikkeri.engine.rendering.RenderingEngine;
import be.ikkeflikkeri.game.components.GameManagerComponent;
import be.ikkeflikkeri.game.components.LevelGeneratorComponent;

public class DarknessGame extends Game
{
	@Override
	public void initializeRenderingEngine(RenderingEngine renderingEngine)
	{
		renderingEngine.setAmbientLight(new Vector3f(1, 1, 1));
	}
	
	@Override
	public void initialize()
	{
		GameObject camera = new CameraObject(75, true);

		GameObject gameManager = new GameObject("GameManager");
		gameManager.addComponent(new GameManagerComponent("level1.png"));
		
		GameObject levelGenerator = new GameObject("LevelGenerator");
		levelGenerator.addComponent(new LevelGeneratorComponent());
		
		addObject(camera);
		addObject(gameManager);
		addObject(levelGenerator);
	}
}
