package be.ikkeflikkeri.engine.components.lighting;

import be.ikkeflikkeri.engine.components.GameComponent;
import be.ikkeflikkeri.engine.core.CoreEngine;
import be.ikkeflikkeri.engine.math.Vector3f;
import be.ikkeflikkeri.engine.rendering.RenderingEngine;
import be.ikkeflikkeri.engine.rendering.Shader;

public class BaseLightComponent extends GameComponent
{
	private Vector3f color;
	private float intensity;
	private Shader shader;
	private RenderingEngine renderingEngine;
	
	public BaseLightComponent(Vector3f color, float intensity)
	{
		this.color = color;
		this.intensity = intensity;
	}
	
	@Override
	public void addToEngine(CoreEngine core)
	{
		renderingEngine = core.getRenderingEngine();
		renderingEngine.addLight(this);
	}
	
	@Override
	public void destroy()
	{
		super.destroy();
		renderingEngine.removeLight(this);
	}
	
	public void setShader(Shader shader)
	{
		this.shader = shader;
	}
	
	public Shader getShader()
	{
		return shader;
	}

	public Vector3f getColor()
	{
		return color;
	}
	
	public void setColor(Vector3f color)
	{
		this.color = color;
	}
	
	public float getIntensity()
	{
		return intensity;
	}
	
	public void setIntensity(float intensity)
	{
		this.intensity = intensity;
	}
}
