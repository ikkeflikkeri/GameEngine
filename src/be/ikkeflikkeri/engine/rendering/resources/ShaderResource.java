package be.ikkeflikkeri.engine.rendering.resources;

import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL20.glCreateProgram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShaderResource extends Resource
{
	private int program;
	private Map<String, Integer> uniforms;
	private ArrayList<String> uniformNames;
	private ArrayList<String> uniformTypes;
	
	public ShaderResource()
	{
		this.program = glCreateProgram();
		
		if(program == 0)
		{
			System.err.println("Shader creation failed: Could not find valid memory location in constructor");
			System.exit(1);
		}
		
		this.uniforms = new HashMap<String, Integer>();
		this.uniformNames = new ArrayList<String>();
		this.uniformTypes = new ArrayList<String>();
	}

	@Override
	protected void finalize()
	{
		glDeleteBuffers(program);
	}

	public int getProgram()
	{
		return program;
	}

	public Map<String, Integer> getUniforms()
	{
		return uniforms;
	}

	public ArrayList<String> getUniformNames()
	{
		return uniformNames;
	}

	public ArrayList<String> getUniformTypes()
	{
		return uniformTypes;
	}	
}
