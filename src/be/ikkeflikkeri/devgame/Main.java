package be.ikkeflikkeri.devgame;

import be.ikkeflikkeri.engine.core.CoreEngine;

public class Main
{
	public static void main(String[] args)
	{
		CoreEngine engine = new CoreEngine(800, 600, 0, new TestGame());
		engine.createWindow("3D Game Engine");
		engine.start();
	}
}
