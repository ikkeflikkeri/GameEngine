package be.ikkeflikkeri.failedgame;

import be.ikkeflikkeri.engine.components.lighting.SpotLightComponent;
import be.ikkeflikkeri.engine.components.movement.FreeLookComponent;
import be.ikkeflikkeri.engine.core.Game;
import be.ikkeflikkeri.engine.math.Vector3f;
import be.ikkeflikkeri.engine.objects.GameObject;
import be.ikkeflikkeri.engine.objects.rendering.CameraObject;
import be.ikkeflikkeri.engine.rendering.Attenuation;
import be.ikkeflikkeri.engine.rendering.RenderingEngine;
import be.ikkeflikkeri.engine.rendering.loading.texture.Image;
import be.ikkeflikkeri.failedgame.components.LevelGeneratorComponent;
import be.ikkeflikkeri.failedgame.components.PlayerComponent;

public class DarknessGame extends Game
{
	Image level;
	
	@Override
	public void initializeRenderingEngine(RenderingEngine renderingEngine)
	{
		//renderingEngine.setAmbientLight(new Vector3f(0.1f, 0.1f, 0.1f));
		renderingEngine.setAmbientLight(new Vector3f(0, 0, 0));
	}
	
	@Override
	public void initialize()
	{
		GameObject camera = new CameraObject(75, false);
		camera.addComponent(new SpotLightComponent(new Vector3f(1, 1, 1), 1, new Attenuation(0, 0, 0.15f), 0.8f));
		camera.addComponent(new FreeLookComponent(0.5f));
		camera.addComponent(new PlayerComponent(3));
		camera.getTransform().moveTo(29, 1, 54);
		camera.getTransform().rotate(Vector3f.UP, 180);
		
		GameObject level = new GameObject("LevelGenerator");
		level.addComponent(new LevelGeneratorComponent("level1.png"));
		
		addObject(camera);
		addObject(level);
		
		
	}
}
