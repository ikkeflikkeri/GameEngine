package be.ikkeflikkeri.engine.components.lighting;

import be.ikkeflikkeri.engine.math.Vector3f;
import be.ikkeflikkeri.engine.rendering.Attenuation;
import be.ikkeflikkeri.engine.rendering.Shader;

public class PointLightComponent extends BaseLightComponent
{
	private static final int COLOR_DEPTH = 256;

	private Attenuation attenuation;
	private float range;
	
	public PointLightComponent(Vector3f color, float intensity, Attenuation attenuation)
	{
		super(color, intensity);
		this.attenuation = attenuation;
		
		float a = attenuation.getExponent();
		float b = attenuation.getLinear();
		float c = attenuation.getConstant() - COLOR_DEPTH * getIntensity() * getColor().max();
		
		this.range = (float)((-b + Math.sqrt(b * b - 4 * a * c))/(2 * a));
		
		setShader(new Shader("forward-point"));
	}

	public Attenuation getAttenuation()
	{
		return attenuation;
	}

	public float getRange()
	{
		return range;
	}

	public void setRange(float range)
	{
		this.range = range;
	}
}
