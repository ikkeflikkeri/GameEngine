package be.ikkeflikkeri.engine.components.movement;

import be.ikkeflikkeri.engine.components.GameComponent;
import be.ikkeflikkeri.engine.math.Quaternion;
import be.ikkeflikkeri.engine.math.Vector3f;
import be.ikkeflikkeri.engine.objects.GameObject;

public class LookAtObjectComponent extends GameComponent
{
	private GameObject object;
	private float speed;
	
	public LookAtObjectComponent(float speed, GameObject object)
	{
		this.speed = speed;
		this.object = object;
	}
	
	@Override
	public void update(float delta)
	{
		Vector3f point = new Vector3f();
		
		if(object != null)
			point = object.getTransform().getTransformedPosition();
		
		Quaternion newRotation = getTransform().getLookAtRotation(point, Vector3f.UP);
		getTransform().setRotation(getTransform().getRotation().nlerp(newRotation, delta * speed, true));
	}
}
