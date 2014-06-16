package be.ikkeflikkeri.engine.rendering;

import java.util.HashMap;
import java.util.Map;

import be.ikkeflikkeri.engine.rendering.resources.MappedValues;

public class Material extends MappedValues
{	
	private Map<String, Texture> textureMap;
	
	public Material()
	{
		this("default_diffuse.jpg", 0, 8);
	}
	public Material(String filename, float specularIntensity, float specularExponent)
	{
		super();
		textureMap = new HashMap<String, Texture>();

		addTexture("diffuse", new Texture(filename));
		addFloat("specularIntensity", specularIntensity);
		addFloat("specularExponent", specularExponent);
	}
	
	public void addTexture(String name, Texture texture)
	{
		textureMap.put(name, texture);
	}
	
	public Texture getTexture(String name)
	{
		if(textureMap.containsKey(name))
			return textureMap.get(name);
		return new Texture("default_diffuse.jpg");
	}
}
