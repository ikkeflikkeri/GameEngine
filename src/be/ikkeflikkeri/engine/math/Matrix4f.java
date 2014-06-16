package be.ikkeflikkeri.engine.math;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Matrix4f
{
	private float[][] m;
	
	public Matrix4f()
	{
		m = new float[4][4];
	}
	
	public Matrix4f initializeIdentity()
	{
		m[0][0] = 1;
		m[0][1] = 0;
		m[0][2] = 0;
		m[0][3] = 0;
		
		m[1][0] = 0;
		m[1][1] = 1;
		m[1][2] = 0;
		m[1][3] = 0;
		
		m[2][0] = 0;
		m[2][1] = 0;
		m[2][2] = 1;
		m[2][3] = 0;
	
		m[3][0] = 0;
		m[3][1] = 0;
		m[3][2] = 0;
		m[3][3] = 1;
		
		return this;
	}
	
	public Matrix4f initializeTranslation(float x, float y, float z)
	{
		initializeIdentity();
		
		m[0][3] = x;
		m[1][3] = y;
		m[2][3] = z;
		
		return this;
	}

	/*public Matrix4f initializeRotation(float x, float y, float z)
	{
		Matrix4f rx = new Matrix4f().initializeIdentity();
		Matrix4f ry = new Matrix4f().initializeIdentity();
		Matrix4f rz = new Matrix4f().initializeIdentity();

		x = Mathf.toRadians(x);
		y = Mathf.toRadians(y);
		z = Mathf.toRadians(z);

		rz.m[0][0] = Mathf.cos(z);
		rz.m[0][1] = -Mathf.sin(z);
		rz.m[1][0] = Mathf.sin(z);
		rz.m[1][1] = Mathf.cos(z);

		rx.m[1][1] = Mathf.cos(x);
		rx.m[1][2] = -Mathf.sin(x);
		rx.m[2][1] = Mathf.sin(x);
		rx.m[2][2] = Mathf.cos(x);

		ry.m[0][0] = Mathf.cos(y);
		ry.m[0][2] = -Mathf.sin(y);
		ry.m[2][0] = Mathf.sin(y);
		ry.m[2][2] = Mathf.cos(y);

		m = rz.mul(ry.mul(rx)).getM();

		return this;
	}*/

	public Matrix4f initializeScale(float x, float y, float z)
	{
		initializeIdentity();
		
		m[0][0] = x;
		m[1][1] = y;
		m[2][2] = z;

		return this;
	}
	
	public Matrix4f initializePerspective(float fov, float aspectRatio, float zNear, float zFar)
	{
		float tanHalfFOV = Mathf.tan(Mathf.toRadians(fov) / 2);
		float zRange = zNear - zFar;

		m[0][0] = 1.0f / (tanHalfFOV * aspectRatio);
		m[1][1] = 1.0f / tanHalfFOV;
		m[2][2] = (-zNear -zFar)/zRange;
		m[2][3] = 2 * zFar * zNear / zRange;
		m[3][2] = 1;

		return this;
	}
	
	public Matrix4f initializeOrthographic(float left, float right, float bottom, float top, float near, float far)
	{
		float width = right - left;
		float height = top - bottom;
		float depth = far - near;
		
		m[0][0] = 2/width;
		m[0][3] = -(right + left)/width;
		m[1][1] = 2/height;
		m[1][3] = -(top + bottom)/height;
		m[2][2] = -2/depth;
		m[2][3] = -(far + near)/depth;
		m[3][3] = 1;
		
		return this;
	}

	public Matrix4f initializeRotation(Vector3f forward, Vector3f up)
	{
		Vector3f f = forward;
		f.normalizedLocal();

		Vector3f r = up;
		r.normalizedLocal();
		r = r.cross(f);

		Vector3f u = f.cross(r);

		return initializeRotation(f, u, r);
	}
	
	public Matrix4f initializeRotation(Vector3f forward, Vector3f up, Vector3f right)
	{
		Vector3f f = forward.normalized();
		Vector3f r = right.normalized();
		Vector3f u = up.normalized();

		m[0][0] = r.getX();
		m[0][1] = r.getY();
		m[0][2] = r.getZ();	
		m[1][0] = u.getX();
		m[1][1] = u.getY();
		m[1][2] = u.getZ();
		m[2][0] = f.getX();
		m[2][1] = f.getY();
		m[2][2] = f.getZ();
		m[3][3] = 1;

		return this;
	}
	
	public Vector3f transform(Vector3f r)
	{
		return new Vector3f(m[0][0] * r.getX() + m[0][1] * r.getY() + m[0][2] * r.getZ() + m[0][3], m[1][0] * r.getX() + m[1][1] * r.getY() + m[1][2] * r.getZ() + m[1][3], m[2][0] * r.getX() + m[2][1] * r.getY() + m[2][2] * r.getZ() + m[2][3]);
	}

	public Matrix4f mul(Matrix4f r)
	{
		Matrix4f res = new Matrix4f();

		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				res.set(i, j, m[i][0] * r.get(0, j) + m[i][1] * r.get(1, j) + m[i][2] * r.get(2, j) + m[i][3] * r.get(3, j));
			}
		}

		return res;
	}
	
	public FloatBuffer toFlippedFloatBuffer()
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);

		for(int i = 0; i < 4; i++)
			for(int j = 0; j < 4; j++)
				buffer.put(get(i, j));

		buffer.flip();

		return buffer;
	}
	
	public float get(int x, int y)
	{
		return m[x][y];
	}
	
	public void set(int x, int y, float value)
	{
		m[x][y] = value;
	}
	
	public float[][] getM()
	{
		return m;
	}
	
	public void setM(float[][] m)
	{
		this.m = m;
	}
}
