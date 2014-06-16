package be.ikkeflikkeri.engine.rendering;

import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import be.ikkeflikkeri.engine.components.lighting.BaseLightComponent;
import be.ikkeflikkeri.engine.components.lighting.DirectionalLightComponent;
import be.ikkeflikkeri.engine.components.lighting.PointLightComponent;
import be.ikkeflikkeri.engine.components.lighting.SpotLightComponent;
import be.ikkeflikkeri.engine.core.Transform;
import be.ikkeflikkeri.engine.math.Matrix4f;
import be.ikkeflikkeri.engine.math.Vector3f;
import be.ikkeflikkeri.engine.rendering.resources.ShaderResource;

public class Shader
{
	private static Map<String, ShaderResource> loadedShaders = new HashMap<String, ShaderResource>();
	
	private ShaderResource resource;
	private String filename;
	
	public Shader(String filename)
	{
		this.filename = filename;
		
		if(loadedShaders.containsKey(filename))
		{
			resource = loadedShaders.get(filename);
			resource.addReference();
		}
		else
		{
			resource = new ShaderResource();
			loadedShaders.put(filename, resource);
			
			String vertexShaderText = loadShader(filename + ".vs");
			String fragmentShaderText = loadShader(filename + ".fs");
			
			addVertexShader(vertexShaderText);
			addFragmentShader(fragmentShaderText);
					
			compileShader();
			
			addAllUniforms(vertexShaderText);
			addAllUniforms(fragmentShaderText);
		}
	}
	
	@Override
	protected void finalize()
	{
		if(resource.removeReference() && !filename.isEmpty())
			loadedShaders.remove(filename);
	}

	public void updateUniforms(Transform transform, Material material, RenderingEngine renderingEngine)
	{
		Matrix4f worldMatrix = transform.getTransformation();
		Matrix4f modelViewProjectionMatrix = renderingEngine.getMainCamera().getViewProjection().mul(worldMatrix);
		
		for (int i = 0; i < resource.getUniformNames().size(); i++)
		{
			String uniformName = resource.getUniformNames().get(i);
			String uniformType = resource.getUniformTypes().get(i);
			
			if(uniformType.equals("sampler2D"))
			{
				int samplerSlot = renderingEngine.getSamplerSlot(uniformName);
				material.getTexture(uniformName).bind(samplerSlot);
				setUniformi(uniformName, samplerSlot);
			}
			else if(uniformName.startsWith("T_"))
			{
				if(uniformName.equals("T_modelViewProjection"))
					setUniform(uniformName, modelViewProjectionMatrix);
				else if(uniformName.equals("T_model"))
					setUniform(uniformName, worldMatrix);
				else
					throw new IllegalArgumentException(uniformName + " is not a valid component of Transform!");
			}
			else if(uniformName.startsWith("R_"))
			{
				String name = uniformName.substring(2);
				if(uniformType.equals("vec3"))
					setUniform(uniformName, renderingEngine.getVector(name));
				else if(uniformType.equals("float"))
					setUniformf(uniformName, renderingEngine.getFloat(name));
				else if(uniformType.equals("DirectionalLight"))
					setUniform(uniformName, (DirectionalLightComponent)renderingEngine.getActiveLight());
				else if(uniformType.equals("PointLight"))
					setUniform(uniformName, (PointLightComponent)renderingEngine.getActiveLight());
				else if(uniformType.equals("SpotLight"))
					setUniform(uniformName, (SpotLightComponent)renderingEngine.getActiveLight());
				else
					renderingEngine.updateUniformStruct(transform, material, this, uniformName, uniformType);
			}
			else if(uniformName.startsWith("M_"))
			{
				if(uniformType.equals("vec3"))
					setUniform(uniformName, material.getVector(uniformName.substring(2)));
				else if(uniformType.equals("float"))
					setUniformf(uniformName, material.getFloat(uniformName.substring(2)));
				else
					throw new IllegalArgumentException(uniformName + " is not a supported type in Material!");
			}
			else if(uniformName.startsWith("C_"))
			{
				if(uniformName.equals("C_cameraPosition"))
					setUniform(uniformName, renderingEngine.getMainCamera().getTransform().getTransformedPosition());
				else
					throw new IllegalArgumentException(uniformName + " is not a valid component of Camera!");
			}
			else
				throw new IllegalArgumentException(uniformName + " is not a valid uniform!");
		}
	}
	
	private class GLSLStructMember
	{
		public String name;
		public String type;
	}
	
	private Map<String, ArrayList<GLSLStructMember>> findUniformStructs(String shaderText)
	{
		Map<String, ArrayList<GLSLStructMember>> result = new HashMap<String, ArrayList<GLSLStructMember>>();
		
		final String STRUCT_COMMAND = "struct";
		
		int structStartLocation = shaderText.indexOf(STRUCT_COMMAND);
		while(structStartLocation != -1)
		{
			if(!(structStartLocation != 0 && (Character.isWhitespace(shaderText.charAt(structStartLocation - 1)) || shaderText.charAt(structStartLocation - 1) == ';') && Character.isWhitespace(shaderText.charAt(structStartLocation + STRUCT_COMMAND.length()))))
			{
				structStartLocation = shaderText.indexOf(STRUCT_COMMAND, structStartLocation + STRUCT_COMMAND.length());
				continue;
			}
			
			int nameBegin = structStartLocation + STRUCT_COMMAND.length() + 1;
			int braceBegin = shaderText.indexOf('{', nameBegin);
			int braceEnd = shaderText.indexOf('}', braceBegin);
			
			String structName = shaderText.substring(nameBegin, braceBegin - 1).trim();
			
			ArrayList<GLSLStructMember> structMemebers = new ArrayList<GLSLStructMember>();
			
			int structEnd = shaderText.indexOf(';', braceBegin);
			
			while(structEnd != -1 && structEnd < braceEnd)
			{
				int memberNameEnd = structEnd + 1;

				while(Character.isWhitespace(shaderText.charAt(memberNameEnd - 1)) || shaderText.charAt(memberNameEnd - 1) == ';')
					memberNameEnd--;

				int memberNameBegin = structEnd;

				while(!Character.isWhitespace(shaderText.charAt(memberNameBegin - 1)))
					memberNameBegin--;

				int memberTypeEnd = memberNameBegin;

				while(Character.isWhitespace(shaderText.charAt(memberTypeEnd - 1)))
					memberTypeEnd--;

				int memberTypeBegin = memberTypeEnd;

				while(!Character.isWhitespace(shaderText.charAt(memberTypeBegin - 1)))
					memberTypeBegin--;

				String memberName = shaderText.substring(memberNameBegin, memberNameEnd);
				String memberType = shaderText.substring(memberTypeBegin, memberTypeEnd);
				
				GLSLStructMember member = new GLSLStructMember();
				member.type = memberType;
				member.name = memberName;
				
				structMemebers.add(member);
								
				structEnd = shaderText.indexOf(';', structEnd + 1);
			}
			
			result.put(structName, structMemebers);
			
			structStartLocation = shaderText.indexOf(STRUCT_COMMAND, structStartLocation + STRUCT_COMMAND.length());
		}
		
		return result;
	}
	
	private void addAllUniforms(String shaderText)
	{
		Map<String, ArrayList<GLSLStructMember>> structs = findUniformStructs(shaderText);
		
		final String UNIFORM_COMMAND = "uniform";
		
		int uniformStartLocation = shaderText.indexOf(UNIFORM_COMMAND);
		
		while(uniformStartLocation != -1)
		{
			if(!(uniformStartLocation != 0 && (Character.isWhitespace(shaderText.charAt(uniformStartLocation - 1)) || shaderText.charAt(uniformStartLocation - 1) == ';') && Character.isWhitespace(shaderText.charAt(uniformStartLocation + UNIFORM_COMMAND.length()))))
			{
				uniformStartLocation = shaderText.indexOf(UNIFORM_COMMAND, uniformStartLocation + UNIFORM_COMMAND.length());
				continue;
			}
			
			int begin = uniformStartLocation + UNIFORM_COMMAND.length() + 1;
			int end = shaderText.indexOf(';', begin);
			
			String unifromLine = shaderText.substring(begin, end);
			
			int spacePos = unifromLine.indexOf(' ');
			
			String uniformName = unifromLine.substring(spacePos + 1, unifromLine.length());
			String uniformType = unifromLine.substring(0, spacePos);
			
			resource.getUniformNames().add(uniformName);
			resource.getUniformTypes().add(uniformType);
			addUniform(uniformName, uniformType, structs);
			
			uniformStartLocation = shaderText.indexOf(UNIFORM_COMMAND, uniformStartLocation + UNIFORM_COMMAND.length());
		}
	}
	
	private void addUniform(String uniformName, String uniformType, Map<String, ArrayList<GLSLStructMember>> structs)
	{
		if(structs.containsKey(uniformType))
		{
			ArrayList<GLSLStructMember> struct = structs.get(uniformType);
			
			for(GLSLStructMember member : struct)
			{
				addUniform(uniformName + "." + member.name, member.type, structs);
			}
		}
		else
		{
			int uniformLocation = glGetUniformLocation(resource.getProgram(), uniformName);

			if(uniformLocation == 0xFFFFFFFF)
			{
				System.err.println("Error: Could not find uniform: " + uniformName);
				new Exception().printStackTrace();
				System.exit(1);
			}

			resource.getUniforms().put(uniformName, uniformLocation);
		}
		
	}
	
	public void bind()
	{
		glUseProgram(resource.getProgram());
	}
	
	private void addVertexShader(String text)
	{
		addProgram(text, GL_VERTEX_SHADER);
	}

	/*private void addGeometryShader(String text)
	{
		addProgram(text, GL_GEOMETRY_SHADER);
	}*/

	private void addFragmentShader(String text)
	{
		addProgram(text, GL_FRAGMENT_SHADER);
	}

	private void compileShader()
	{
		glLinkProgram(resource.getProgram());

		if(glGetProgrami(resource.getProgram(), GL_LINK_STATUS) == 0)
		{
			System.err.println(glGetProgramInfoLog(resource.getProgram(), 1024));
			System.exit(1);
		}

		glValidateProgram(resource.getProgram());

		if(glGetProgrami(resource.getProgram(), GL_VALIDATE_STATUS) == 0)
		{
			System.err.println(glGetProgramInfoLog(resource.getProgram(), 1024));
			System.exit(1);
		}
	}

	private void addProgram(String text, int type)
	{
		int shader = glCreateShader(type);

		if(shader == 0)
		{
			System.err.println("Shader creation failed: Could not find valid memory location when adding shader");
			System.exit(1);
		}

		glShaderSource(shader, text);
		glCompileShader(shader);

		if(glGetShaderi(shader, GL_COMPILE_STATUS) == 0)
		{
			System.err.println(glGetShaderInfoLog(shader, 1024));
			System.exit(1);
		}

		glAttachShader(resource.getProgram(), shader);
	}
	
	private static String loadShader(String filename)
	{
		StringBuilder shaderSource = new StringBuilder();
		BufferedReader shaderReader = null;

		final String INCLUDE_COMMAND = "#include";
		
		try
		{
			shaderReader = new BufferedReader(new FileReader("./res/shaders/" + filename));
			String line;

			while((line = shaderReader.readLine()) != null)
			{
				if(line.startsWith(INCLUDE_COMMAND))
				{
					shaderSource.append(loadShader(line.substring(INCLUDE_COMMAND.length() + 2, line.length() - 1)));
				}
				else
					shaderSource.append(line).append("\n");
			}

			shaderReader.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}

		return shaderSource.toString();
	}
	
	public void setUniformi(String uniformName, int value)
	{
		glUniform1i(resource.getUniforms().get(uniformName), value);
	}
	public void setUniformf(String uniformName, float value)
	{
		glUniform1f(resource.getUniforms().get(uniformName), value);
	}
	public void setUniform(String uniformName, Vector3f value)
	{
		glUniform3f(resource.getUniforms().get(uniformName), value.getX(), value.getY(), value.getZ());
	}
	public void setUniform(String uniformName, Matrix4f value)
	{
		glUniformMatrix4(resource.getUniforms().get(uniformName), true, value.toFlippedFloatBuffer());
	}
	public void setUniform(String uniformName, BaseLightComponent baseLight)
	{
		setUniform(uniformName + ".color", baseLight.getColor());
		setUniformf(uniformName + ".intensity", baseLight.getIntensity());
	}
	public void setUniform(String uniformName, DirectionalLightComponent directionalLight)
	{
		setUniform(uniformName + ".baseLight", (BaseLightComponent)directionalLight);
		setUniform(uniformName + ".direction", directionalLight.getDirection());
	}
	public void setUniform(String uniformName, PointLightComponent pointLight)
	{
		setUniform(uniformName + ".baseLight", (BaseLightComponent)pointLight);
		setUniformf(uniformName + ".attenuation.constant", pointLight.getAttenuation().getConstant());
		setUniformf(uniformName + ".attenuation.linear", pointLight.getAttenuation().getLinear());
		setUniformf(uniformName + ".attenuation.exponent", pointLight.getAttenuation().getExponent());
		setUniform(uniformName + ".position", pointLight.getTransform().getTransformedPosition());
		setUniformf(uniformName + ".range", pointLight.getRange());
	}
	public void setUniform(String uniformName, SpotLightComponent spotLight)
	{
		setUniform(uniformName + ".pointLight", (PointLightComponent)spotLight);
		setUniform(uniformName + ".direction", spotLight.getDirection());
		setUniformf(uniformName + ".cutoff", spotLight. getCutoff());
	}
}