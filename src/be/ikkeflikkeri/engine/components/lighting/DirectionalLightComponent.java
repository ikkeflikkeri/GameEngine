package be.ikkeflikkeri.engine.components.lighting;

import be.ikkeflikkeri.engine.math.Vector3f;
import be.ikkeflikkeri.engine.rendering.Shader;

public class DirectionalLightComponent extends BaseLightComponent
{
	public DirectionalLightComponent(Vector3f color, float intensity)
	{
		super(color, intensity);
		
		setShader(new Shader("forward-directional"));
	}

	public Vector3f getDirection()
	{
		return getTransform().getTransformedRotation().getForward();
	}
}
