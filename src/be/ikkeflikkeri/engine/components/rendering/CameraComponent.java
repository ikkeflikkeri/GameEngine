package be.ikkeflikkeri.engine.components.rendering;

import be.ikkeflikkeri.engine.components.GameComponent;
import be.ikkeflikkeri.engine.core.CoreEngine;
import be.ikkeflikkeri.engine.math.Matrix4f;
import be.ikkeflikkeri.engine.math.Vector3f;
import be.ikkeflikkeri.engine.rendering.Window;

public class CameraComponent extends GameComponent
{
	private Matrix4f projection;

	public CameraComponent(float fov)
	{
		this(fov, 0.01f, 1000.0f);
	}
	public CameraComponent(float fov, float zNear, float zFar)
	{
		this.projection = new Matrix4f().initializePerspective(fov, (float)Window.getWidth() / (float)Window.getHeight(), zNear, zFar);
	}

	public Matrix4f getViewProjection()
	{
		Vector3f cameraPosition = getTransform().getTransformedPosition().mul(-1);
		
		Matrix4f cameraRotation = getTransform().getTransformedRotation().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = new Matrix4f().initializeTranslation(cameraPosition.getX(), cameraPosition.getY(), cameraPosition.getZ());
		
		return projection.mul(cameraRotation.mul(cameraTranslation));
	}
	
	@Override
	public void addToEngine(CoreEngine core)
	{
		core.getRenderingEngine().addCamera(this);
	}
}