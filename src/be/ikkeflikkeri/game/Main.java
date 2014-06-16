package be.ikkeflikkeri.game;

import be.ikkeflikkeri.engine.core.CoreEngine;

public class Main
{    
	public static void main(String[] args)
	{
		CoreEngine engine = new CoreEngine(800, 600, 0, new DarknessGame());
		engine.createWindow("The Darkness Game - By ikkeflikkeri");
		engine.start();
	}
}
