package be.ikkeflikkeri.engine.components.rendering;

import be.ikkeflikkeri.engine.components.GameComponent;
import be.ikkeflikkeri.engine.rendering.Material;
import be.ikkeflikkeri.engine.rendering.Mesh;
import be.ikkeflikkeri.engine.rendering.RenderingEngine;
import be.ikkeflikkeri.engine.rendering.Shader;

public class MeshRendererComponent extends GameComponent
{
	private Mesh mesh;
	private Material material;
	
	public MeshRendererComponent(Mesh mesh)
	{
		this(mesh, new Material());
	}
	public MeshRendererComponent(Mesh mesh, Material material)
	{
		this.mesh = mesh;
		this.material = material;
	}
	
	@Override
	public void render(Shader shader, RenderingEngine renderingEngine)
	{
		shader.bind();
		shader.updateUniforms(getTransform(), material, renderingEngine);
		mesh.draw();
	}
}
