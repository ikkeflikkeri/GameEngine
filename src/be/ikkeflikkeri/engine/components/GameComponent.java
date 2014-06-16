package be.ikkeflikkeri.engine.components;

import be.ikkeflikkeri.engine.core.CoreEngine;
import be.ikkeflikkeri.engine.core.Transform;
import be.ikkeflikkeri.engine.objects.GameObject;
import be.ikkeflikkeri.engine.rendering.RenderingEngine;
import be.ikkeflikkeri.engine.rendering.Shader;

public abstract class GameComponent
{
	private GameObject parent;
	private boolean removed;
	
	public void input(float delta) {}
	public void update(float delta) {}
	public void render(Shader shader, RenderingEngine renderingEngine) {}
	
	public void setParent(GameObject parent)
	{
		this.parent = parent;
		this.removed = false;
	}
	
	public void destroy()
	{
		removed = true;
	}
	
	public GameObject getParent()
	{
		return parent;
	}
	
	public Transform getTransform()
	{
		return parent.getTransform();
	}
	
	public boolean isRemoved()
	{
		return removed;
	}
	
	public void addToEngine(CoreEngine core) {}
}
