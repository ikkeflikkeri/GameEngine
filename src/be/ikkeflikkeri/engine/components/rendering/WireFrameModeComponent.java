package be.ikkeflikkeri.engine.components.rendering;

import be.ikkeflikkeri.engine.components.GameComponent;
import be.ikkeflikkeri.engine.core.CoreEngine;
import be.ikkeflikkeri.engine.core.Input;
import be.ikkeflikkeri.engine.rendering.RenderingEngine;

public class WireFrameModeComponent extends GameComponent
{
	private int key;
	private boolean enabled;
	private RenderingEngine renderingEngine;
	
	public WireFrameModeComponent(int key)
	{
		this.key = key;
		this.enabled = false;
	}
	
	@Override
	public void input(float delta)
	{
		if(Input.isKeyPressed(key))
		{
			enabled = !enabled;
			renderingEngine.setWireFrameMode(enabled);
		}
	}
	
	@Override
	public void addToEngine(CoreEngine core)
	{
		this.renderingEngine = core.getRenderingEngine();
	}
}
