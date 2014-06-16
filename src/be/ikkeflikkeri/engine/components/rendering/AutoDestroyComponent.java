package be.ikkeflikkeri.engine.components.rendering;

import be.ikkeflikkeri.engine.components.GameComponent;

public class AutoDestroyComponent extends GameComponent
{
	float time;
	float currentTime;
	
	public AutoDestroyComponent(float time)
	{
		this.time = time;
	}
	
	@Override
	public void update(float delta)
	{
		currentTime += delta;
		
		if(currentTime >= time)
			getParent().destroy();
	}
}
