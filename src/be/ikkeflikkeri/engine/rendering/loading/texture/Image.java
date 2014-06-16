package be.ikkeflikkeri.engine.rendering.loading.texture;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameterf;
import static org.lwjgl.opengl.GL11.glTexParameteri;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import be.ikkeflikkeri.engine.core.Utilities;
import be.ikkeflikkeri.engine.rendering.resources.TextureResource;

public class Image
{
	private boolean hasAlpha;
	private TextureResource resource;
	private int[] pixels;
	private int width;
	private int height;
	
	public Image(String filename, boolean generateResource)
	{
		try
		{
			BufferedImage image = ImageIO.read(new File("./res/textures/" + filename));
			ByteBuffer buffer = Utilities.createByteBuffer(image.getHeight() * image.getWidth() * 4);
			pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
			width = image.getWidth();
			height = image.getHeight();
			hasAlpha = image.getColorModel().hasAlpha();
			
			int[] temp = new int[pixels.length];

			for(int i = 0; i < width; i++)
				for(int j = 0; j < height; j++)
					temp[i + j * width] = pixels[i + (height - j - 1)  * width];

			pixels = temp;
			
			if(generateResource)
			{
				for(int y = 0; y < image.getHeight(); y++)
				{
					for(int x = 0; x < image.getWidth(); x++)
					{
						int pixel = pixels[y * image.getWidth() + x];
						
						buffer.put((byte)((pixel >> 16) & 0xFF));
						buffer.put((byte)((pixel >> 8) & 0xFF));
						buffer.put((byte)(pixel & 0xFF));
						
						if(hasAlpha)
							buffer.put((byte)((pixel >> 24) & 0xFF));
						else
							buffer.put((byte)0xFF);
					}
				}
				
				buffer.flip();
				
				resource = new TextureResource();
				
				glBindTexture(GL_TEXTURE_2D, resource.getId());
	
				glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
				glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
	
				glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
				glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
	
				glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public TextureResource getResource()
	{
		return resource;
	}
	
	public boolean hasAlpa()
	{
		return hasAlpha;
	}
	
	public int[] getPixels()
	{
		return pixels;
	}
	
	public int getPixel(int x, int y)
	{
		return pixels[x + y * width];
	}
	
	public void setPixel(int x, int y, int value)
	{
		pixels[x + y * width] = value;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}
}
