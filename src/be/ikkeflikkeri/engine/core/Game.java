package be.ikkeflikkeri.engine.core;

import be.ikkeflikkeri.engine.objects.GameObject;
import be.ikkeflikkeri.engine.rendering.RenderingEngine;

public abstract class Game
{
	private GameObject root;
	
	public void initialize() {}
	public void initializeRenderingEngine(RenderingEngine renderingEngine) {}
	
	public void input(float delta)
	{
		getRootObject().inputAll(delta);
	}
	
	public void update(float delta)
	{
		getRootObject().updateAll(delta);
	}
	
	public void addObject(GameObject object)
	{
		getRootObject().addChild(object);
	}
	
	public void render(RenderingEngine renderingEngine)
	{
		renderingEngine.render(getRootObject());
	}
	
	private GameObject getRootObject()
	{
		if(root == null)
			root = new GameObject("Root");
		
		return root;
	}

	public void setCoreEngine(CoreEngine coreEngine)
	{
		getRootObject().setCoreEngine(coreEngine);
	}
}
