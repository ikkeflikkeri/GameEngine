package be.ikkeflikkeri.engine.components.movement;

import be.ikkeflikkeri.engine.components.GameComponent;
import be.ikkeflikkeri.engine.math.Vector3f;

public class ConstantRotationComponent extends GameComponent
{
	private float speed;
	private Vector3f axis;
	
	public ConstantRotationComponent(float speed, Vector3f axis)
	{
		this.speed = speed;
		this.axis = axis;
	}
	
	@Override
	public void update(float delta)
	{
		getTransform().rotate(axis, speed * delta);
	}
}
