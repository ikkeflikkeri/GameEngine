package be.ikkeflikkeri.engine.rendering;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import be.ikkeflikkeri.engine.math.Vector2f;

public class Window
{
	public static void create(int width, int height, String title)
	{
		try
		{
			Display.setTitle(title);
			Display.setDisplayMode(new DisplayMode(width, height));
			//Display.setDisplayModeAndFullscreen(Display.getDesktopDisplayMode());
			Display.create();
			Keyboard.create();
			Mouse.create();
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void render()
	{
		Display.update();
	}
	
	public static void destroy()
	{
		Display.destroy();
		Keyboard.destroy();
		Mouse.destroy();
	}
	
	public static boolean isCloseRequested()
	{
		return Display.isCloseRequested();
	}
	
	public static int getWidth()
	{
		return Display.getDisplayMode().getWidth();
	}
	
	public static int getHeight()
	{
		return Display.getDisplayMode().getHeight();
	}

	public static String getTitle()
	{
		return Display.getTitle();
	}
	
	public Vector2f getCenterPosition()
	{
		return new Vector2f(getWidth() / 2, getHeight() / 2);
	}
}
