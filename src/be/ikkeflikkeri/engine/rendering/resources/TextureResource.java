package be.ikkeflikkeri.engine.rendering.resources;

import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;

public class TextureResource extends Resource
{
	private int id;

	public TextureResource()
	{
		this.id = glGenTextures();
	}

	@Override
	protected void finalize()
	{
		glDeleteBuffers(id);
	}

	public int getId()
	{
		return id;
	}
}
