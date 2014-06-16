package be.ikkeflikkeri.game.components;

import java.util.ArrayList;
import java.util.Arrays;

import be.ikkeflikkeri.engine.components.GameComponent;
import be.ikkeflikkeri.engine.components.rendering.MeshRendererComponent;
import be.ikkeflikkeri.engine.core.Utilities;
import be.ikkeflikkeri.engine.math.Vector2f;
import be.ikkeflikkeri.engine.math.Vector3f;
import be.ikkeflikkeri.engine.rendering.Material;
import be.ikkeflikkeri.engine.rendering.Mesh;
import be.ikkeflikkeri.engine.rendering.Vertex;
import be.ikkeflikkeri.engine.rendering.loading.texture.Image;

public class LevelGeneratorComponent extends GameComponent
{
	private ArrayList<Vertex> vertices;
	private ArrayList<Integer> indices;
	
	public LevelGeneratorComponent()
	{
		vertices = new ArrayList<Vertex>();
		indices = new ArrayList<Integer>();
	}

	@Override
	public void update(float delta)
	{
	}
	
	public void generateMesh(Image level)
	{
		vertices.clear();
		indices.clear();
		
		if(getParent().getComponents(MeshRendererComponent.class) != null)
			getParent().getComponents(MeshRendererComponent.class).destroy();
		
		for(int i = 0; i < level.getWidth(); i++)
		{
			for(int j = 0; j < level.getHeight(); j++)
			{
				if((level.getPixel(i, j) & 0xFFFFFF) == 0)
					continue;
				
				addFloor(i, j);
				addRoof(i, j);
				
				if((level.getPixel(i, j - 1) & 0xFFFFFF) == 0 && j > 0)
					addBackWall(i, j);
				if((level.getPixel(i, j + 1) & 0xFFFFFF) == 0 && j < level.getHeight())
					addFrontWall(i, j);
				if((level.getPixel(i - 1, j) & 0xFFFFFF) == 0 && i > 0)
					addLeftWall(i, j);
				if((level.getPixel(i + 1, j) & 0xFFFFFF) == 0 && i < level.getWidth())
					addRightWall(i, j);
			}
		}
		
		getParent().addComponent(new MeshRendererComponent(new Mesh(vertices.toArray(new Vertex[0]), Utilities.toIntArray(indices.toArray(new Integer[0])), true), new Material("bricks.jpg", 0, 8)));
	}
	private void addFloor(int i, int j)
	{
		Vertex[] floorVertices =
		{
			new Vertex(new Vector3f(1 + i * 2, 0, 1 + j * 2), new Vector2f(0, 0)),
			new Vertex(new Vector3f(-1 + i * 2, 0, 1 + j * 2), new Vector2f(0, 1)),
			new Vertex(new Vector3f(1 + i * 2, 0, -1 + j * 2), new Vector2f(1, 0)),
			new Vertex(new Vector3f(-1 + i * 2, 0, -1 + j * 2), new Vector2f(1, 1))
		};
		
		Integer[] floorIndices = 
		{
			vertices.size() + 1, vertices.size() + 2, vertices.size() + 3,
			vertices.size() + 2, vertices.size() + 1, vertices.size() + 0
		};
		
		vertices.addAll(Arrays.asList(floorVertices));
		indices.addAll(Arrays.asList(floorIndices));
	}
	private void addRoof(int i, int j)
	{
		Vertex[] roofVertices =
		{
			new Vertex(new Vector3f(1 + i * 2, 2, 1 + j * 2), new Vector2f(0, 0)),
			new Vertex(new Vector3f(-1 + i * 2, 2, 1 + j * 2), new Vector2f(0, 1)),
			new Vertex(new Vector3f(1 + i * 2, 2, -1 + j * 2), new Vector2f(1, 0)),
			new Vertex(new Vector3f(-1 + i * 2, 2, -1 + j * 2), new Vector2f(1, 1))
		};
		
		Integer[] roofIndices = 
		{
			vertices.size() + 3, vertices.size() + 2, vertices.size() + 1,
			vertices.size() + 0, vertices.size() + 1, vertices.size() + 2
		};
		
		vertices.addAll(Arrays.asList(roofVertices));
		indices.addAll(Arrays.asList(roofIndices));
	}
	private void addBackWall(int i, int j)
	{
		Vertex[] wallVertices =
		{
			new Vertex(new Vector3f(-1 + i * 2, 2, -1 + j * 2), new Vector2f(0, 0)),
			new Vertex(new Vector3f(1 + i * 2, 2, -1 + j * 2), new Vector2f(0, 1)),
			new Vertex(new Vector3f(-1 + i * 2, 0, -1 + j * 2), new Vector2f(1, 0)),
			new Vertex(new Vector3f(1 + i * 2, 0, -1 + j * 2), new Vector2f(1, 1))
		};
		
		Integer[] wallIndices = 
		{
			vertices.size() + 1, vertices.size() + 2, vertices.size() + 3,
			vertices.size() + 2, vertices.size() + 1, vertices.size() + 0
		};
		
		vertices.addAll(Arrays.asList(wallVertices));
		indices.addAll(Arrays.asList(wallIndices));
	}
	private void addFrontWall(int i, int j)
	{
		Vertex[] wallVertices =
		{
			new Vertex(new Vector3f(-1 + i * 2, 2, 1 + j * 2), new Vector2f(0, 0)),
			new Vertex(new Vector3f(1 + i * 2, 2, 1 + j * 2), new Vector2f(0, 1)),
			new Vertex(new Vector3f(-1 + i * 2, 0, 1 + j * 2), new Vector2f(1, 0)),
			new Vertex(new Vector3f(1 + i * 2, 0, 1 + j * 2), new Vector2f(1, 1))
		};
			
		Integer[] wallIndices = 
		{
			vertices.size() + 3, vertices.size() + 2, vertices.size() + 1,
			vertices.size() + 0, vertices.size() + 1, vertices.size() + 2
		};
			
		vertices.addAll(Arrays.asList(wallVertices));
		indices.addAll(Arrays.asList(wallIndices));
	}
	private void addLeftWall(int i, int j)
	{
		Vertex[] wallVertices =
		{
			new Vertex(new Vector3f(-1 + i * 2, 2, -1 + j * 2), new Vector2f(0, 0)),
			new Vertex(new Vector3f(-1 + i * 2, 2, 1 + j * 2), new Vector2f(0, 1)),
			new Vertex(new Vector3f(-1 + i * 2, 0, -1 + j * 2), new Vector2f(1, 0)),
			new Vertex(new Vector3f(-1 + i * 2, 0, 1 + j * 2), new Vector2f(1, 1))
		};
			
		Integer[] wallIndices = 
		{
			vertices.size() + 3, vertices.size() + 2, vertices.size() + 1,
			vertices.size() + 0, vertices.size() + 1, vertices.size() + 2
		};
			
		vertices.addAll(Arrays.asList(wallVertices));
		indices.addAll(Arrays.asList(wallIndices));
	}
	private void addRightWall(int i, int j)
	{
		Vertex[] wallVertices =
		{
			new Vertex(new Vector3f(1 + i * 2, 2, -1 + j * 2), new Vector2f(0, 0)),
			new Vertex(new Vector3f(1 + i * 2, 2, 1 + j * 2), new Vector2f(0, 1)),
			new Vertex(new Vector3f(1 + i * 2, 0, -1 + j * 2), new Vector2f(1, 0)),
			new Vertex(new Vector3f(1 + i * 2, 0, 1 + j * 2), new Vector2f(1, 1))
		};
			
		Integer[] wallIndices = 
		{
			vertices.size() + 1, vertices.size() + 2, vertices.size() + 3,
			vertices.size() + 2, vertices.size() + 1, vertices.size() + 0
		};
			
		vertices.addAll(Arrays.asList(wallVertices));
		indices.addAll(Arrays.asList(wallIndices));
	}
}
