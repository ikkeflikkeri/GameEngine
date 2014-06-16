package be.ikkeflikkeri.engine.math;

// ADD DOC lerp, equals

/**
 * File: Vector3f.java
 * 
 * Description:
 * Vector3f represents a three dimensional value, such as a position, normal, etc.
 * 
 * @author Niels Wouters
 * @version 1.02 05/06/2014
 */

public class Vector3f
{
	/**
	 * Initiates a new Vector3f with values of the global up direction (0, 1, 0).
	 */
	public static final Vector3f UP = new Vector3f(0, 1, 0);
	
	/**
	 * Initiates a new Vector3f with values of the global down direction (0, -1, 0).
	 */
	public static final Vector3f DOWN = new Vector3f(0, -1, 0);
	
	/**
	 * Initiates a new Vector3f with values of the global right direction (1, 0, 0).
	 */
	public static final Vector3f RIGHT = new Vector3f(1, 0, 0);
	
	/**
	 * Initiates a new Vector3f with values of the global left direction (-1, 0, 0).
	 */
	public static final Vector3f LEFT = new Vector3f(-1, 0, 0);
	
	/**
	 * Initiates a new Vector3f with values of the global forward direction (0, 0, 1).
	 */
	public static final Vector3f FORWARD = new Vector3f(0, 0, 1);
	
	/**
	 * Initiates a new Vector3f with values of the global backward direction (0, 0, -1).
	 */
	public static final Vector3f BACKWARD = new Vector3f(0, 0, -1);
	
	/**
	 * The x value of the vector.
	 */
	private float x;
	
	/**
	 * The y value of the vector.
	 */
	private float y;
	
	/**
	 * The z value of the vector.
	 */
	private float z;
	
	/**
	 * Constructor initiates a new Vector3f with default values of (0, 0, 0).
	 */
	public Vector3f()
	{
		this(0, 0, 0);
	}
	
	/**
	 * Constructor initiates a new Vector3f with provided values.
	 * 
	 * @param x The x value of the vector.
	 * @param y The y value of the vector.
	 * @param z The z value of the vector.
	 */
	public Vector3f(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Constructor initiates a new Vector3f that is a copy of the provided vector.
	 * 
	 * @param copy The Vector3f to copy.
	 * @throws IllegalArgumentException If provided vector is null.
	 */
	public Vector3f(Vector3f copy) throws IllegalArgumentException
	{
		this.set(copy);
	}
	
	/**
	 * Calculates the length of this vector.
	 * 
	 * @return The length of this vector.
	 */
	public float length()
	{
		return Mathf.sqrt(lengthSquared());
	}
	
	/**
	 * Calculates the squared length of this vector.
	 * 
	 * @return The squared length of this vector.
	 */
	public float lengthSquared()
	{
		return x * x + y * y + z * z;
	}
	
	public float dot(Vector3f r)
	{
		return x * r.getX() + y * r.getY() + z * r.getZ();
	}
	
	/**
	 * Makes all the values positive and returns the resultant vector.
	 * 
	 * @return The resultant vector
	 */
	public Vector3f abs()
	{
		return new Vector3f(Math.abs(x), Math.abs(y), Math.abs(z));
	}
	
	/**
	 * Makes all the values in this vector positive and returns a handle to this vector.
	 * 
	 * @return This vector.
	 */
	public Vector3f absLocal()
	{
		x = Math.abs(x);
		y = Math.abs(y);
		z = Math.abs(z);
		return this;
	}
	
	/**
	 * Adds a provided vector to this vector creating a resultant vector which is returned.
	 * 
	 * @param vector The vector to add.
	 * @return The resultant vector.
	 * @throws IllegalArgumentException If provided vector is null.
	 */
	public Vector3f add(Vector3f vector) throws IllegalArgumentException
	{
		if(vector == null)
			throw new IllegalArgumentException("Provided vector is null!");
		
		return new Vector3f(x + vector.x, y + vector.y, z + vector.z);
	}
	
	public Vector3f add(float value)
	{
		return new Vector3f(x + value, y + value, z + value);
	}
	
	/**
	 * Adds a provided vector to this vector and returns a handle to this vector.
	 * 
	 * @param vector The vector to add.
	 * @return This vector.
	 * @throws IllegalArgumentException If provided vector is null.
	 */
	public Vector3f addLocal(Vector3f vector) throws IllegalArgumentException
	{
		if(vector == null)
			throw new IllegalArgumentException("Provided vector is null!");
		
		x += vector.x;
		y += vector.y;
		z += vector.z;
		
		return this;
	}
	
	/**
	 * Adds the provided values to this vector creating a resultant vector which is returned.
	 * 
	 * @param addX The x value to add.
	 * @param addY The y value to add.
	 * @param addZ The z value to add.
	 * @return The resultant vector.
	 */
	public Vector3f add(float addX, float addY, float addZ)
	{
		return new Vector3f(x + addX, y + addY, z + addZ);
	}
	
	/**
	 * Adds the provided values values to this vector and returns a handle to this vector.
	 * 
	 * @param addX The x value to add.
	 * @param addY The y value to add.
	 * @param addZ The z value to add.
	 * @return This vector.
	 */
	public Vector3f addLocal(float addX, float addY, float addZ)
	{
		x += addX;
		y += addY;
		z += addZ;
		return this;
	}
	
	/**
	 * Multiplies this vector by the provided vector and the resultant vector is returned.
	 * 
	 * @param vector The vector to multiply.
	 * @return The resultant vector.
	 * @throws IllegalArgumentException If provided vector is null.
	 */
	public Vector3f mul(Vector3f vector) throws IllegalArgumentException
	{
		if(vector == null)
			throw new IllegalArgumentException("Provided vector is null!");
		
		return new Vector3f(x * vector.x, y * vector.y, z * vector.z);
	}
	
	/**
	 * Multiplies this vector by the provided vector and returns a handle to this vector.
	 * 
	 * @param vector The vector to multiply.
	 * @return This vector.
	 * @throws IllegalArgumentException If provided vector is null.
	 */
	public Vector3f mulLocal(Vector3f vector) throws IllegalArgumentException
	{
		if(vector == null)
			throw new IllegalArgumentException("Provided vector is null!");
		
		x *= vector.x;
		y *= vector.y;
		z *= vector.z;
		return this;
	}
	
	/**
	 * Multiplies this vector by the provided value and the resultant vector is returned.
	 * 
	 * @param value The value to multiply.
	 * @return The resultant value.
	 */
	public Vector3f mul(float value)
	{
		return new Vector3f(x * value, y * value, z * value);
	}
	
	/**
	 * Multiplies this vector by the provided value and returns a handle to this vector.
	 * 
	 * @param value The value to multiply.
	 * @return This vector.
	 */
	public Vector3f mulLocal(float value)
	{
		x *= value;
		y *= value;
		z *= value;
		return this;
	}
	
	/**
	 * Divides this vector by the provided vector and the resultant vector is returned.
	 * 
	 * @param vector The vector to divide.
	 * @return The resultant vector.
	 * @throws IllegalArgumentException If the provided vector is null.
	 * @throws IllegalArgumentException If the x value of the provided vector is 0.
	 * @throws IllegalArgumentException If the y value of the provided vector is 0.
	 * @throws IllegalArgumentException If the z value of the provided vector is 0.
	 */
	public Vector3f div(Vector3f vector) throws IllegalArgumentException
	{
		if(vector == null)
			throw new IllegalArgumentException("Provided vector is null!");
		
		if(vector.x == 0)
			throw new IllegalArgumentException("The x value of the provided vector is 0!");
		if(vector.y == 0)
			throw new IllegalArgumentException("The y value of the provided vector is 0!");
		if(vector.z == 0)
			throw new IllegalArgumentException("The z value of the provided vector is 0!");
		
		return new Vector3f(x / vector.x, y / vector.y, z / vector.z);
	}
	
	/**
	 * Divides this vector by the provided vector and returns a handle to this vector.
	 * 
	 * @param vector The vector to divide.
	 * @return This vector.
	 * @throws IllegalArgumentException If the provided vector is null.
	 * @throws IllegalArgumentException If the x value of the provided vector is 0.
	 * @throws IllegalArgumentException If the y value of the provided vector is 0.
	 * @throws IllegalArgumentException If the z value of the provided vector is 0.
	 */
	public Vector3f divLocal(Vector3f vector) throws IllegalArgumentException
	{
		if(vector == null)
			throw new IllegalArgumentException("Provided vector is null!");
		
		if(vector.x == 0)
			throw new IllegalArgumentException("The x value of the provided vector is 0!");
		if(vector.y == 0)
			throw new IllegalArgumentException("The y value of the provided vector is 0!");
		if(vector.z == 0)
			throw new IllegalArgumentException("The z value of the provided vector is 0!");
		
		x /= vector.x;
		y /= vector.y;
		z /= vector.z;
		return this;
	}
	
	/**
	 * Divides this vector by the provided value and the provided vector is returned.
	 * 
	 * @param value The value to divide.
	 * @return The resultant vector.
	 * @throws IllegalArgumentException If the provided value is 0.
	 */
	public Vector3f div(float value) throws IllegalArgumentException
	{
		if(value == 0)
			throw new IllegalArgumentException("Provided value is 0!");
		
		return new Vector3f(x / value, y / value, z / value);
	}
	
	/**
	 * Divides this vector by the provided value and returns a handle to this vector.
	 * 
	 * @param value The value to divide.
	 * @return This vector.
	 * @throws IllegalArgumentException If the provided value is 0.
	 */
	public Vector3f divLocal(float value) throws IllegalArgumentException
	{
		if(value == 0)
			throw new IllegalArgumentException("Provided value is 0!");
		
		x /= value;
		y /= value;
		z /= value;
		return this;
	}
	
	/**
	 * Subtracts this vector by the provided vector and the resultant vector is returned.
	 * 
	 * @param vector The vector to subtract.
	 * @return The resultant vector.
	 * @throws IllegalArgumentException If the provided vector is null.
	 */
	public Vector3f sub(Vector3f vector) throws IllegalArgumentException
	{
		if(vector == null)
			throw new IllegalArgumentException("The provided vector is null!");
		
		return new Vector3f(x - vector.x, y - vector.y, z - vector.z);
	}
	
	/**
	 * Subtracts this vector by the provided vector and returns a handle to this vector.
	 * 
	 * @param vector The vector to subtract.
	 * @return This vector.
	 * @throws IllegalArgumentException If the provided vector is null.
	 */
	public Vector3f subLocal(Vector3f vector) throws IllegalArgumentException
	{
		if(vector == null)
			throw new IllegalArgumentException("The provided vector is null!");
		
		x -= vector.x;
		y -= vector.y;
		z -= vector.z;
		return this;
	}
	
	/**
	 * Subtracts this vector by the provided values and the resultant vector is returned.
	 * 
	 * @param subX The x value to subtract.
	 * @param subY The y value to subtract.
	 * @param subZ The z value to subtract.
	 * @return The resultant vector.
	 */
	public Vector3f sub(float subX, float subY, float subZ)
	{
		return new Vector3f(x - subX, y - subY, z - subZ);
	}
	
	/**
	 * Subtracts this vector by the provided values and returns a handle to this vector.
	 * 
	 * @param subX The x value to subtract.
	 * @param subY The y value to subtract.
	 * @param subZ The z value to subtract.
	 * @return This vector.
	 */
	public Vector3f subLocal(float subX, float subY, float subZ)
	{
		x -= subX;
		y -= subY;
		z -= subZ;
		return this;
	}
	
	/**
	 * Calculates the unit vector of this vector and the resultant vector is returned.
	 * 
	 * @return The unit vector of this vector
	 * @throws ArithmeticException If length of this vector equals 0.
	 */
	public Vector3f normalized() throws ArithmeticException
	{
		float length = length();
		Vector3f vec = new Vector3f();
		
		if(length != 0)
		{
			vec.setX(x / length);
			vec.setY(y / length);
			vec.setZ(z / length);
		}
		else
			throw new ArithmeticException("The length of the vector is 0!");
		
		return vec;
	}
	
	/**
	 * Calculates the unit vector of this vector and overrides current values, a handle to this vector is returned.
	 * 
	 * @return This vector
	 * @throws ArithmeticException If length of this vector equals 0.
	 */
	public Vector3f normalizedLocal() throws ArithmeticException
	{
		float length = length();
		
		if(length != 0)
		{
			x /= length;
			y /= length;
			z /= length;
		}
		else
			throw new ArithmeticException("The length of the vector is 0!");
		
		return this;
	}
	
	/**
	 * Calculates the cross product with the provided vector and returns the resultant vector.
	 * 
	 * @param vector The vector to cross with.
	 * @return The resultant vector.
	 */
	public Vector3f cross(Vector3f vector)
	{
		Vector3f vec = new Vector3f();

        vec.setX((y * vector.z) - (z * vector.y)); 
        vec.setY((z * vector.x) - (x * vector.z));
        vec.setZ((x * vector.y) - (y * vector.x));

        return vec;
    }
	
	/**
	 * Calculates the cross product with the provided vector and returns this vector.
	 * 
	 * @param vector The vector to cross with.
	 * @return This vector.
	 */
	public Vector3f crossLocal(Vector3f vector)
	{
        x = (y * vector.z) - (z * vector.y); 
        y = (z * vector.x) - (x * vector.z);
        z = (x * vector.y) - (y * vector.x);

        return this;
    }
	
	/**
	 * Rotates this vector around the provided axis and returns the resultant vector.
	 * 
	 * @param axis The axis to rotate around.
	 * @param angle The angle of the rotation.
	 * @return The resultant vector.
	 */
	public Vector3f rotate(Vector3f axis, float angle)
	{
		float sinAngle = Mathf.sin(Mathf.toRadians(-angle));
		float cosAngle = Mathf.cos(Mathf.toRadians(-angle));

		return this.cross(axis.mul(sinAngle)).add((this.mul(cosAngle)).add(axis.mul(this.dot(axis.mul(1 - cosAngle)))));
	}
	
	/**
	 * Rotates this vector around the provided axis and returns a handle to this vector.
	 * 
	 * @param axis The axis to rotate around.
	 * @param angle The angle of the rotation.
	 * @return This vector.
	 */
	public Vector3f rotateLocal(Vector3f axis, float angle)
	{
		Vector3f w = rotate(axis, angle);

		x = w.getX();
		y = w.getY();
		z = w.getZ();

		return this;
	}
	
	public Vector3f rotate(Quaternion rotation)
	{
		Quaternion conjugate = rotation.conjugate();
		Quaternion w = rotation.mul(this).mul(conjugate);

		return new Vector3f(w.getX(), w.getY(), w.getZ());
	}
	
	public Vector3f lerp(Vector3f destination, float factor)
	{
		return destination.sub(this).mul(factor).add(this);
	}

	public float max()
	{
		return Math.max(x, Math.max(y, z));
	}
	
	public float angle(Vector3f vector)
	{
		return Mathf.toDegrees(Mathf.acos(normalized().dot(vector.normalized())));
	}
	
	/**
	 * Formats this vector to a string representation
	 * 
	 * @return The string representation of this vector.
	 */
	public String toString()
	{
		return "(" + x + ", " + y + ", " + z + ")";
	}
	
	/**
	 * Constructor initiates a new Vector3f with provided values.
	 * 
	 * @param x The x value of the vector.
	 * @param y The y value of the vector.
	 * @param z The z value of the vector.
	 * @return This vector.
	 */
	public Vector3f set(float x, float y, float z)
	{
		this.x = x;
        this.y = y;
        this.z = z;
        return this;
	}
	
	/**
	 * Sets the x, y, z values of the vector by copying the supplied vector.
	 * 
	 * @param vector The vector to copy.
	 * @return This vector.
	 * @throws IllegalArgumentException If provided vector is null.
	 */
	public Vector3f set(Vector3f vector) throws IllegalArgumentException
	{
		if(vector == null)
			throw new IllegalArgumentException("Provided vector is null!");
		
		this.x = vector.x;
        this.y = vector.y;
        this.z = vector.z;
        return this;
	}
	
	/**
	 * Gets the value of the provided index.
	 * 
	 * @param index Which value of the vector to get.
	 * @return The value of the provided index.
	 * @throws IndexOutOfBoundsException If index is neither 0, 1 or 2.
	 */
	public float get(int index) throws IllegalArgumentException
	{
		switch(index)
		{
			case 0:
				return x;
			case 1:
				return y;
			case 2:
				return z;
			default:
				throw new IndexOutOfBoundsException("Index must be either 0, 1 or 2!");
		}
	}
	
	/**
	 * Sets the value of the provided index to the provided value.
	 * 
	 * @param index Which value in this vector to set.
	 * @param value The value to set.
	 * @return This vector.
	 * @throws IllegalArgumentException If index is neither 0, 1 or 2.
	 */
	public Vector3f set(int index, float value) throws IndexOutOfBoundsException
	{
		switch (index)
		{
			case 0:
				x = value;
				break;
			case 1:
				y = value;
				break;
			case 2:
				z = value;
				break;
			default:
				throw new IndexOutOfBoundsException("Index must be either 0, 1 or 2!");
		}
		return this;
	}

	/**
	 * Gets the x value.
	 * 
	 * @return The x value.
	 */
	public float getX()
	{
		return x;
	}

	/**
	 * Sets the x value.
	 * 
	 * @param x The x value.
	 */
	public void setX(float x)
	{
		this.x = x;
	}

	/**
	 * Gets the y value.
	 * 
	 * @return The y value.
	 */
	public float getY()
	{
		return y;
	}

	/**
	 * Sets the y value.
	 * 
	 * @param y The y value.
	 */
	public void setY(float y)
	{
		this.y = y;
	}

	/**
	 * Gets the z value.
	 * 
	 * @return The z value.
	 */
	public float getZ()
	{
		return z;
	}

	/**
	 * Sets the z value.
	 * 
	 * @param z The z value.
	 */
	public void setZ(float z)
	{
		this.z = z;
	}
	
	public boolean equals(Vector3f other)
	{
		return x == other.getX() && y == other.getY() && z == other.getZ();
	}
}