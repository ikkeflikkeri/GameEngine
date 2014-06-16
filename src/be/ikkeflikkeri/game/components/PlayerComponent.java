package be.ikkeflikkeri.game.components;

import be.ikkeflikkeri.engine.components.GameComponent;
import be.ikkeflikkeri.engine.core.Input;
import be.ikkeflikkeri.engine.math.Vector3f;
import be.ikkeflikkeri.engine.objects.GameObject;
import be.ikkeflikkeri.engine.rendering.loading.texture.Image;

public class PlayerComponent extends GameComponent
{
	private Image level;
	private float speed;
	private int forwardKey;
	private int backwardKey;
	private int leftKey;
	private int rightKey;
	
	public PlayerComponent(float speed)
	{
		this(speed, Input.KEY_Z, Input.KEY_S, Input.KEY_Q, Input.KEY_D);
	}
	public PlayerComponent(float speed, int forwardKey, int backwardKey, int leftKey, int rightKey)
	{
		this.speed = speed;
		this.forwardKey = forwardKey;
		this.backwardKey = backwardKey;
		this.leftKey = leftKey;
		this.rightKey = rightKey;
	}

	boolean lockLeft = false;
	boolean lockRight = false;
	boolean lockForward = false;
	boolean lockBackward = false;

	
	@Override
	public void input(float delta)
	{
		if(level == null)
			return;
		
		lockLeft = false;
		lockRight = false;
		lockForward = false;
		lockBackward = false;
		
		int x = (int)Math.ceil((getTransform().getPosition().getX() - 1) / 2);
		int y = (int)Math.ceil((getTransform().getPosition().getZ() - 1) / 2);
		
		if((level.getPixel(x - 1, y) & 0xFFFFFF) == 0)
			lockLeft = true;
		
		if((level.getPixel(x + 1, y) & 0xFFFFFF) == 0)
			lockRight = true;
		
		if((level.getPixel(x, y + 1) & 0xFFFFFF) == 0)
			lockForward = true;
		
		if((level.getPixel(x, y - 1) & 0xFFFFFF) == 0)
			lockBackward = true;
		
		float movAmt = (float)(speed * delta);

		if(Input.isKeyDown(forwardKey))
			move(getTransform().getRotation().getForward(), movAmt);
		if(Input.isKeyDown(backwardKey))
			move(getTransform().getRotation().getForward(), -movAmt);
		if(Input.isKeyDown(leftKey))
			move(getTransform().getRotation().getLeft(), movAmt);
		if(Input.isKeyDown(rightKey))
			move(getTransform().getRotation().getRight(), movAmt);
		
		getTransform().getPosition().setY(1);

		if((level.getPixel(x, y) & 0xFFFFFF) == 0xFF0000)
		{
			LevelGeneratorComponent l = null;
			GameObject g = getParent().findGameObjectByName("LevelGenerator")[0];
			l = (LevelGeneratorComponent)g.getComponents(LevelGeneratorComponent.class);
			
			System.out.println(l);
		}
	}

	public void move(Vector3f dir, float amt)
	{
		getTransform().getPosition().addLocal(dir.mul(amt));
		
		if(lockLeft)
		{
			float xLeft = (float)Math.ceil((getTransform().getPosition().getX() - 1) / 2) * 2 - 0.9f;
			if(getTransform().getPosition().getX() < xLeft)
				getTransform().getPosition().setX(xLeft);
		}
		
		if(lockRight)
		{
			float xRight = (float)Math.ceil((getTransform().getPosition().getX() - 1) / 2) * 2 + 0.9f;
			if(getTransform().getPosition().getX() > xRight)
				getTransform().getPosition().setX(xRight);
		}
		
		if(lockForward)
		{
			float zForward = (float)Math.ceil((getTransform().getPosition().getZ() - 1) / 2) * 2 + 0.9f;
			if(getTransform().getPosition().getZ() > zForward)
				getTransform().getPosition().setZ(zForward);
		}
		
		if(lockBackward)
		{
			float zBackward = (float)Math.ceil((getTransform().getPosition().getZ() - 1) / 2) * 2 - 0.9f;
			if(getTransform().getPosition().getZ() < zBackward)
				getTransform().getPosition().setZ(zBackward);
		}
	}
	
	public void setLevel(Image level)
	{
		this.level = level;
	}
}
