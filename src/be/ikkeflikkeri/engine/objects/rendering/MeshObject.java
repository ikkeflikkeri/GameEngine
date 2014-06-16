package be.ikkeflikkeri.engine.objects.rendering;

import be.ikkeflikkeri.engine.components.rendering.MeshRendererComponent;
import be.ikkeflikkeri.engine.objects.GameObject;
import be.ikkeflikkeri.engine.rendering.Material;
import be.ikkeflikkeri.engine.rendering.Mesh;

public class MeshObject extends GameObject
{
	public MeshObject(Mesh mesh)
	{
		this(mesh, new Material());
	}
	public MeshObject(Mesh mesh, Material material)
	{
		addComponent(new MeshRendererComponent(mesh, material));
	}
}
