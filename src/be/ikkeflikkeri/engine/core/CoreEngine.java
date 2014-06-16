package be.ikkeflikkeri.engine.core;

import be.ikkeflikkeri.engine.rendering.RenderingEngine;
import be.ikkeflikkeri.engine.rendering.Window;

public class CoreEngine
{
	private boolean isRunning;
	private Game game;
	private int width;
	private int height;
	private double frametime;
	private RenderingEngine renderingEngine;
	
	public CoreEngine(int width, int height, int framerate, Game game)
	{
		this.isRunning = false;
		this.game = game;
		this.width = width;
		this.height = height;
		
		framerate = (framerate > 1000 || framerate < 1 ? 1000 : framerate);
		
		this.frametime = 1.0 / (double)framerate;
		
		game.setCoreEngine(this);
	}
	
	public void createWindow(String title)
	{
		Window.create(width, height, title);
		renderingEngine = new RenderingEngine();
	}
	
	public void start()
	{
		if(isRunning)
			return;
		
		isRunning = true;
		run();
	}
	
	public void stop()
	{
		if(!isRunning)
			return;
		
		isRunning = false;
	}
	
	private void run()
	{		
		int frames = 0;
		double frameCounter = 0;
		
		game.initialize();
		game.initializeRenderingEngine(renderingEngine);
		
		double lastTime = Time.getTime();
		double unprocessedTime = 0;
		
		while(isRunning)
		{
			boolean render = false;
			
			double startTime = Time.getTime();
			double passedTime = startTime - lastTime;
			lastTime = startTime;
			
			frameCounter += passedTime;

			unprocessedTime += passedTime;
			
			while(unprocessedTime > frametime)
			{
				unprocessedTime -= frametime;
				
				if(Window.isCloseRequested())
					stop();
				
				game.input((float)frametime);
				Input.update();
				game.update((float)frametime);
				
				render = true;
				
				if(frameCounter >= 1)
				{
					System.out.println(frames);
					frames = 0;
					frameCounter = 0;
				}
			}
			if(render)
			{
				game.render(renderingEngine);
				Window.render();
				frames++;
			}
		}
		
		Window.destroy();
	}
	
	public RenderingEngine getRenderingEngine()
	{
		return renderingEngine;
	}
}
