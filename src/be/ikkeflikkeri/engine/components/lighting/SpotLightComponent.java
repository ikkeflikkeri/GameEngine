package be.ikkeflikkeri.engine.components.lighting;

import be.ikkeflikkeri.engine.math.Vector3f;
import be.ikkeflikkeri.engine.rendering.Attenuation;
import be.ikkeflikkeri.engine.rendering.Shader;

public class SpotLightComponent extends PointLightComponent
{	
	private float cutoff;
	
	public SpotLightComponent(Vector3f color, float intensity, Attenuation attenuation, float cutoff)
	{
		super(color, intensity, attenuation);
		this.cutoff = cutoff;
		
		setShader(new Shader("forward-spot"));
	}
	
	public Vector3f getDirection()
	{
		return getTransform().getTransformedRotation().getForward();
	}
	
	public float getCutoff()
	{
		return cutoff;
	}

	public void setCutoff(float cutoff)
	{
		this.cutoff = cutoff;
	}
}
