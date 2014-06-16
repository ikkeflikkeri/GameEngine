package be.ikkeflikkeri.engine.rendering.resources;

import java.util.HashMap;
import java.util.Map;

import be.ikkeflikkeri.engine.math.Vector3f;

public abstract class MappedValues
{
	private Map<String, Vector3f> vectorMap;
	private Map<String, Float> floatMap;
	
	public MappedValues()
	{
		vectorMap = new HashMap<String, Vector3f>();
		floatMap = new HashMap<String, Float>();
	}
	
	public void addVector(String name, Vector3f vector)
	{
		vectorMap.put(name, vector);
	}
	
	public Vector3f getVector(String name)
	{
		if(vectorMap.containsKey(name))
			return vectorMap.get(name);
		return new Vector3f(0, 0, 0);
	}
	
	public void addFloat(String name, float value)
	{
		floatMap.put(name, value);
	}
	
	public float getFloat(String name)
	{
		if(floatMap.containsKey(name))
			return floatMap.get(name);
		return 0;
	}
}
