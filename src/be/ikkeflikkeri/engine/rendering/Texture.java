package be.ikkeflikkeri.engine.rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import java.util.HashMap;
import java.util.Map;

import be.ikkeflikkeri.engine.rendering.loading.texture.Image;
import be.ikkeflikkeri.engine.rendering.resources.TextureResource;

public class Texture
{
	private static Map<String, TextureResource> loadedTextures = new HashMap<String, TextureResource>();
	private TextureResource resource;
	private String filename;
	
	public Texture(String filename)
	{
		this.filename = filename;

		if(loadedTextures.containsKey(filename))
		{
			resource = loadedTextures.get(filename);
			resource.addReference();
		}
		else
		{
			resource = new Image(filename, true).getResource();
			loadedTextures.put(filename, resource);
		}
	}
	
	@Override
	protected void finalize()
	{
		if(resource.removeReference() && !filename.isEmpty())
			loadedTextures.remove(filename);
	}

	public void bind()
	{
		bind(0);
	}
	
	public void bind(int samplerSlot)
	{
		assert(samplerSlot >= 0 && samplerSlot <= 31);
		
		glActiveTexture(GL_TEXTURE0 + samplerSlot);
		glBindTexture(GL_TEXTURE_2D, resource.getId());
	}

	public int getID()
	{
		return resource.getId();
	}
}