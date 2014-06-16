package be.ikkeflikkeri.engine.objects.rendering;

import be.ikkeflikkeri.engine.components.movement.FreeLookComponent;
import be.ikkeflikkeri.engine.components.movement.FreeMoveComponent;
import be.ikkeflikkeri.engine.components.rendering.CameraComponent;
import be.ikkeflikkeri.engine.objects.GameObject;

public class CameraObject extends GameObject
{
	public CameraObject(float fov, boolean controls)
	{
		this(fov, 0.01f, 1000.0f, controls);
	}
	public CameraObject(float fov, float zNear, float zFar, boolean controls)
	{
		addComponent(new CameraComponent(fov, zNear, zFar));
		
		if(controls)
		{
			addComponent(new FreeLookComponent(0.5f));
			addComponent(new FreeMoveComponent(15));
		}
	}
}
