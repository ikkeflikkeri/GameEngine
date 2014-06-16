package be.ikkeflikkeri.engine.components.movement;

import be.ikkeflikkeri.engine.components.GameComponent;
import be.ikkeflikkeri.engine.core.Input;
import be.ikkeflikkeri.engine.math.Vector3f;

public class FreeMoveComponent extends GameComponent
{
	private float speed;
	private int forwardKey;
	private int backwardKey;
	private int leftKey;
	private int rightKey;
	private int upKey;
	private int downKey;
	
	public FreeMoveComponent(float speed)
	{
		this(speed, Input.KEY_Z, Input.KEY_S, Input.KEY_Q, Input.KEY_D, Input.KEY_SPACE, Input.KEY_LSHIFT);
	}
	public FreeMoveComponent(float speed, int forwardKey, int backwardKey, int leftKey, int rightKey, int upKey, int downKey)
	{
		this.speed = speed;
		this.forwardKey = forwardKey;
		this.backwardKey = backwardKey;
		this.leftKey = leftKey;
		this.rightKey = rightKey;
		this.upKey = upKey;
		this.downKey = downKey;
	}

	@Override
	public void input(float delta)
	{
		float movAmt = (float)(speed * delta);

		if(Input.isKeyDown(forwardKey))
			move(getTransform().getRotation().getForward(), movAmt);
		if(Input.isKeyDown(backwardKey))
			move(getTransform().getRotation().getForward(), -movAmt);
		if(Input.isKeyDown(leftKey))
			move(getTransform().getRotation().getLeft(), movAmt);
		if(Input.isKeyDown(rightKey))
			move(getTransform().getRotation().getRight(), movAmt);
		if(Input.isKeyDown(upKey))
			move(Vector3f.UP, movAmt);
		if(Input.isKeyDown(downKey))
			move(Vector3f.DOWN, movAmt);
	}

	public void move(Vector3f dir, float amt)
	{
		getTransform().getPosition().addLocal(dir.mul(amt));
	}
}
