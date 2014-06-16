package be.ikkeflikkeri.engine.objects.shapes;

import be.ikkeflikkeri.engine.components.rendering.MeshRendererComponent;
import be.ikkeflikkeri.engine.objects.GameObject;
import be.ikkeflikkeri.engine.rendering.Material;
import be.ikkeflikkeri.engine.rendering.Mesh;

public class PlaneObject extends GameObject
{
	public PlaneObject()
	{
		this(new Material());
	}
	public PlaneObject(Material material)
	{
		addComponent(new MeshRendererComponent(new Mesh("plane.obj"), material));
	}
}
