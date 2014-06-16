package be.ikkeflikkeri.engine.objects;

import java.util.ArrayList;
import java.util.Iterator;

import be.ikkeflikkeri.engine.components.GameComponent;
import be.ikkeflikkeri.engine.core.CoreEngine;
import be.ikkeflikkeri.engine.core.Transform;
import be.ikkeflikkeri.engine.rendering.RenderingEngine;
import be.ikkeflikkeri.engine.rendering.Shader;

public class GameObject
{
	private CoreEngine coreEngine;
	private GameObject parent;
	private ArrayList<GameObject> children;
	private ArrayList<GameComponent> components;
	private Transform transform;
	private String name;
	private boolean removed;
	
	public GameObject()
	{
		this("");
	}
	
	public GameObject(String name)
	{
		this.children = new ArrayList<GameObject>();
		this.components = new ArrayList<GameComponent>();
		this.transform = new Transform();
		this.removed = false;
		this.coreEngine = null;
		this.name = name;
	}
	
	public GameObject addChild(GameObject child)
	{
		child.getTransform().setParent(transform);
		child.setParent(this);
		child.setCoreEngine(coreEngine);
		children.add(child);
		
		return this;
	}

	public GameObject addComponent(GameComponent component)
	{
		component.setParent(this);
		components.add(component);
		
		return this;
	}
	
	public void destroy()
	{
		removed = true;
		
		for(GameComponent component : components)
			component.destroy();
		for(GameObject child : children)
			child.destroy();
	}
	
	public void inputAll(float delta)
	{
		input(delta);
		
		for(GameObject child : children)
			child.inputAll(delta);
	}
	
	public void updateAll(float delta)
	{
		update(delta);
		
		Iterator<GameObject> childIterator = children.iterator();
		while (childIterator.hasNext())
		{
		    GameObject child = childIterator.next();
		    
		    child.updateAll(delta);
		    if(child.isRemoved())
		    	childIterator.remove();
		}
	}
	
	public void renderAll(Shader shader, RenderingEngine renderingEngine)
	{
		render(shader, renderingEngine);
		
		for(GameObject child : children)
			child.renderAll(shader, renderingEngine);
	}
	
	public void input(float delta)
	{
		transform.update();
		
		for(GameComponent component : components)
			component.input(delta);
	}
	
	public void update(float delta)
	{
		Iterator<GameComponent> componentIterator = components.iterator();
		while (componentIterator.hasNext())
		{
			GameComponent component = componentIterator.next();

			component.update(delta);
		    if(component.isRemoved())
		    {
		    	component = null;
		    	componentIterator.remove();
		    }
		}
	}
	
	public void render(Shader shader, RenderingEngine renderingEngine)
	{
		for(GameComponent component : components)
			component.render(shader, renderingEngine);
	}
	
	public ArrayList<GameObject> getAllAttached()
	{
		ArrayList<GameObject> result = new ArrayList<GameObject>();
		
		for(GameObject child : children)
		{
			result.addAll(child.getAllAttached());
		}
		
		result.add(this);
		return result;
	}
	
	public Transform getTransform()
	{
		return transform;
	}

	public GameObject getParent()
	{
		return parent;
	}

	public void setParent(GameObject parent)
	{
		this.parent = parent;
	}
	
	public void setCoreEngine(CoreEngine coreEngine)
	{
		if(this.coreEngine != coreEngine)
		{
			this.coreEngine = coreEngine;

			for(GameComponent component : components)
				component.addToEngine(coreEngine);
			
			for(GameObject child : children)
				child.setCoreEngine(coreEngine);
		}
	}
	
	public GameObject[] findGameObjectByName(String name)
	{
		if(parent != null)
			return parent.findGameObjectByName(name);
		else
		{
			ArrayList<GameObject> objects = getAllAttached();
			ArrayList<GameObject> result = new ArrayList<GameObject>();
			
			for(GameObject object : objects)
			{
				if(object.getName().equals(name))
					result.add(object);
			}
			
			return result.toArray(new GameObject[0]);
		}
	}
	
	public GameComponent[] getComponents()
	{
		return components.toArray(new GameComponent[0]);
	}
	
	public GameComponent getComponents(Class<?> c)
	{
		for(GameComponent component : components)
		{
			if(component.getClass().equals(c))
			{
				return component;
			}
		}
		return null;
	}
	
	public boolean isRemoved()
	{
		return removed;
	}

	public String getName()
	{
		return name;
	}
}
