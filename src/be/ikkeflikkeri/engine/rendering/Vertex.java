package be.ikkeflikkeri.engine.rendering;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import be.ikkeflikkeri.engine.math.Vector2f;
import be.ikkeflikkeri.engine.math.Vector3f;

public class Vertex
{
	public static final int SIZE = 8;

	private Vector3f position;
	private Vector3f normal;
	private Vector2f texCoord;
	
	public Vertex(Vector3f position)
	{
		this(position, new Vector2f(0, 0));
	}

	public Vertex(Vector3f position, Vector2f texCoord)
	{
		this(position, texCoord, new Vector3f(0, 0, 0));
	}
	
	public Vertex(Vector3f position, Vector2f texCoord, Vector3f normal)
	{
		this.position = position;
		this.texCoord = texCoord;
		this.normal = normal;
	}

	public Vector3f getPosition()
	{
		return position;
	}

	public void setPosition(Vector3f position)
	{
		this.position = position;
	}
	
	public Vector2f getTexCoord()
	{
		return texCoord;
	}

	public void setTexCoord(Vector2f texCoord)
	{
		this.texCoord = texCoord;
	}

	public Vector3f getNormal()
	{
		return normal;
	}

	public void setNormal(Vector3f normal)
	{
		this.normal = normal;
	}
	
	public static FloatBuffer toFlippedFloatBuffer(Vertex[] vertices)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length * Vertex.SIZE);

		for(int i = 0; i < vertices.length; i++)
		{
			buffer.put(vertices[i].getPosition().getX());
			buffer.put(vertices[i].getPosition().getY());
			buffer.put(vertices[i].getPosition().getZ());
			buffer.put(vertices[i].getTexCoord().getX());
			buffer.put(vertices[i].getTexCoord().getY());
			buffer.put(vertices[i].getNormal().getX());
			buffer.put(vertices[i].getNormal().getY());
			buffer.put(vertices[i].getNormal().getZ());
		}

		buffer.flip();

		return buffer;
	}
}