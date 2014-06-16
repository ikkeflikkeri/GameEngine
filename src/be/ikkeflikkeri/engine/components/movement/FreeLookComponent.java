package be.ikkeflikkeri.engine.components.movement;

import be.ikkeflikkeri.engine.components.GameComponent;
import be.ikkeflikkeri.engine.core.Input;
import be.ikkeflikkeri.engine.math.Vector2f;
import be.ikkeflikkeri.engine.math.Vector3f;
import be.ikkeflikkeri.engine.rendering.Window;

public class FreeLookComponent extends GameComponent
{
	private boolean mouseLocked;
	private float sensitivity;
	private int lockKey;
	
	public FreeLookComponent(float sensitivity)
	{
		this(sensitivity, Input.KEY_ESCAPE);
	}
	public FreeLookComponent(float sensitivity, int lockKey)
	{
		this.sensitivity = sensitivity;
		this.lockKey = lockKey;
		this.mouseLocked = false;
	}

	@Override
	public void input(float delta)
	{
		Vector2f centerPosition = new Vector2f(Window.getWidth()/2, Window.getHeight()/2);
		
		if(Input.isKeyPressed(lockKey))
		{
			Input.setCursor(true);
			mouseLocked = false;
		}
		if(Input.isMousePressed(0))
		{
			Input.setMousePosition(centerPosition);
			Input.setCursor(false);
			mouseLocked = true;
		}
		
		if(mouseLocked)
		{
			Vector2f deltaPos = Input.getMousePosition().sub(centerPosition);
			
			boolean rotY = deltaPos.getX() != 0;
			boolean rotX = deltaPos.getY() != 0;
			
			if(rotY)
				getTransform().rotate(Vector3f.UP, deltaPos.getX() * sensitivity);
			if(rotX)
				getTransform().rotate(getTransform().getRotation().getRight(), deltaPos.getY() * -sensitivity);
				
			if(rotY || rotX)
				Input.setMousePosition(new Vector2f(Window.getWidth()/2, Window.getHeight()/2));
		}		
	}
}
