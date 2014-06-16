package be.ikkeflikkeri.engine.core;

import be.ikkeflikkeri.engine.math.Matrix4f;
import be.ikkeflikkeri.engine.math.Quaternion;
import be.ikkeflikkeri.engine.math.Vector3f;

public class Transform
{
	private Transform parent;
	private Matrix4f parentMatrix;
	
	private Vector3f position;
	private Quaternion rotation;
	private Vector3f scale;
	
	private Vector3f oldPosition;
	private Quaternion oldRotation;
	private Vector3f oldScale;

	public Transform()
	{
		position = new Vector3f(0, 0, 0);
		rotation = new Quaternion(0, 0, 0, 1);
		scale = new Vector3f(1, 1, 1);
		parentMatrix = new Matrix4f().initializeIdentity();
	}
	
	public void update()
	{
		if(oldPosition != null)
		{
			oldPosition.set(position);
			oldRotation.set(rotation);
			oldScale.set(scale);
		}
		else
		{
			oldPosition = new Vector3f(0,0,0).set(position).add(1.0f);
			oldRotation = new Quaternion(0,0,0,0).set(rotation).mul(0.5f);
			oldScale = new Vector3f(0,0,0).set(scale).add(1.0f);
		}
	}
	
	public void lookAt(Vector3f point, Vector3f up)
	{
		rotation = getLookAtRotation(point, up);
	}

	public Quaternion getLookAtRotation(Vector3f point, Vector3f up)
	{
		return new Quaternion(new Matrix4f().initializeRotation(point.sub(getTransformedPosition()).rotate(parent.getTransformedRotation().conjugate()).normalized(), up));
	}
	
	public void rotate(Vector3f axis, float angle)
	{
		rotation = new Quaternion(axis, angle).mul(rotation).normalized();
	}
	
	public void move(float x, float y, float z)
	{
		position.addLocal(x, y, z);
	}
	
	public void moveTo(float x, float y, float z)
	{
		position.set(x, y, z);
	}
	
	public boolean hasChanged()
	{	
		if(parent != null && parent.hasChanged())
			return true;
		
		if(!position.equals(oldPosition))
			return true;
		
		if(!rotation.equals(oldRotation))
			return true;
		
		if(!scale.equals(oldScale))
			return true;
		
		return false;
	}

	public Matrix4f getTransformation()
	{
		Matrix4f translationMatrix = new Matrix4f().initializeTranslation(position.getX(), position.getY(), position.getZ());
		Matrix4f rotationMatrix = rotation.toRotationMatrix();
		Matrix4f scaleMatrix = new Matrix4f().initializeScale(scale.getX(), scale.getY(), scale.getZ());

		return getParentTransformation().mul(translationMatrix.mul(rotationMatrix.mul(scaleMatrix)));
	}
	
	private Matrix4f getParentTransformation()
	{
		if(parent != null && parent.hasChanged())
			parentMatrix = parent.getTransformation();
		
		return parentMatrix;
	}
	
	public Quaternion getTransformedRotation()
	{
		Quaternion parentRotation = new Quaternion(0, 0, 0, 1);
		
		if(parent != null)
			parentRotation = parent.getTransformedRotation();
		
		return parentRotation.mul(rotation);
	}
	
	public Vector3f getTransformedPosition()
	{
		return getParentTransformation().transform(position);
	}
	
	public Vector3f getPosition()
	{
		return position;
	}

	public void setPosition(Vector3f translation)
	{
		this.position = translation;
	}
	
	public Quaternion getRotation()
	{
		return rotation;
	}

	public void setRotation(Quaternion rotation)
	{
		this.rotation = rotation;
	}

	public Vector3f getScale()
	{
		return scale;
	}

	public void setScale(Vector3f scale)
	{
		this.scale = scale;
	}
	
	public void setParent(Transform parent)
	{
		this.parent = parent;
	}
}