package be.ikkeflikkeri.engine.rendering.resources;

import static org.lwjgl.opengl.GL15.*;

public class MeshResource extends Resource
{
	private int vbo;
	private int ibo;
	private int size;
	
	public MeshResource(int size)
	{
		this.ibo = glGenBuffers();
		this.vbo = glGenBuffers();
		this.size = size;
	}

	@Override
	protected void finalize()
	{
		glDeleteBuffers(vbo);
		glDeleteBuffers(ibo);
	}

	public int getVbo()
	{
		return vbo;
	}

	public int getIbo()
	{
		return ibo;
	}
	
	public int getSize()
	{
		return size;
	}
}
