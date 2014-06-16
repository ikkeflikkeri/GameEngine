package be.ikkeflikkeri.engine.rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import be.ikkeflikkeri.engine.core.Utilities;
import be.ikkeflikkeri.engine.math.Vector3f;
import be.ikkeflikkeri.engine.rendering.loading.mesh.IndexedModel;
import be.ikkeflikkeri.engine.rendering.loading.mesh.OBJ.OBJModel;
import be.ikkeflikkeri.engine.rendering.resources.MeshResource;

public class Mesh
{
	private static Map<String, MeshResource> loadedMeshes = new HashMap<String, MeshResource>();
	private MeshResource resource;
	private String filename;
	
	public Mesh(String filename)
	{
		this.filename = filename;
		
		if(loadedMeshes.containsKey(filename))
		{
			resource = loadedMeshes.get(filename);
			resource.addReference();
		}
		else
		{
			loadMesh(filename);
			loadedMeshes.put(filename, resource);
		}
	}
	
	public Mesh(Vertex[] vertices, int[] indices)
	{
		this(vertices, indices, false);
	}
	
	public Mesh(Vertex[] vertices, int[] indices, boolean calcNormals)
	{
		filename = "";
		addVertices(vertices, indices, calcNormals);
	}
	
	@Override
	protected void finalize()
	{
		if(resource.removeReference() && !filename.isEmpty())
			loadedMeshes.remove(filename);
	}

	public void addVertices(Vertex[] vertices, int[] indices, boolean calcNormals)
	{
		if(calcNormals)
			calcNormals(vertices, indices);
		
		resource = new MeshResource(indices.length);

		glBindBuffer(GL_ARRAY_BUFFER, resource.getVbo());
		glBufferData(GL_ARRAY_BUFFER, Vertex.toFlippedFloatBuffer(vertices), GL_STATIC_DRAW);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, resource.getIbo());
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, Utilities.createFlippedBuffer(indices), GL_STATIC_DRAW);
	}

	public void draw()
	{
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);

		glBindBuffer(GL_ARRAY_BUFFER, resource.getVbo());
		glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, Vertex.SIZE * 4, 12);
		glVertexAttribPointer(2, 3, GL_FLOAT, false, Vertex.SIZE * 4, 20);

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, resource.getIbo());
		glDrawElements(GL_TRIANGLES, resource.getSize(), GL_UNSIGNED_INT, 0);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
	}
	
	private void calcNormals(Vertex[] vertices, int[] indices)
	{
		for(int i = 0; i < indices.length; i += 3)
		{
			int i0 = indices[i];
			int i1 = indices[i + 1];
			int i2 = indices[i + 2];

			Vector3f v1 = vertices[i1].getPosition().sub(vertices[i0].getPosition());
			Vector3f v2 = vertices[i2].getPosition().sub(vertices[i0].getPosition());

			Vector3f normal = v1.cross(v2).normalized();

			vertices[i0].getNormal().addLocal(normal);
			vertices[i1].getNormal().addLocal(normal);
			vertices[i2].getNormal().addLocal(normal);
		}

		for(int i = 0; i < vertices.length; i++)
			vertices[i].getNormal().normalizedLocal();
		
		for(int i = 0; i < vertices.length; i++)
		{
			Vertex v = vertices[i];
			ArrayList<Vertex> vertices2 = new ArrayList<Vertex>();
			vertices2.add(v);
			
			for(int j = 0; j < vertices.length; j++)
			{
				if(i != j)
				{
					if(vertices[j].getPosition().equals(v.getPosition()))
					{
						vertices2.add(vertices[j]);
					}
				}
			}
			
			Vector3f normal = new Vector3f();
			for(int j = 0; j < vertices2.size(); j++)
			{
				normal.addLocal(vertices2.get(j).getNormal());
			}
			normal.divLocal(vertices2.size());
			for(int j = 0; j < vertices2.size(); j++)
			{
				vertices2.get(j).setNormal(normal);
			}
		}
	}
	
	private void loadMesh(String filename)
	{
		String[] splitArray = filename.split("\\.");
		String ext = splitArray[splitArray.length - 1];
		
		if(!ext.equals("obj"))
		{
			System.err.println("Error: File format not supported for mesh data: " + ext);
			new Exception().printStackTrace();
			System.exit(1);
		}
		
		IndexedModel model = new OBJModel("./res/models/" + filename).toIndexedModel();
		model.calcNormals();
		
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		
		for(int i = 0; i < model.getPositions().size(); i++)
		{
			vertices.add(new Vertex(model.getPositions().get(i), model.getTexCoords().get(i), model.getNormals().get(i)));
		}
		
		Vertex[] vertexData = new Vertex[vertices.size()];
		vertices.toArray(vertexData);

		Integer[] indexData = new Integer[model.getIndices().size()];
		model.getIndices().toArray(indexData);
		
		addVertices(vertexData, Utilities.toIntArray(indexData), false);
	}
}