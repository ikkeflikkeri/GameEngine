package be.ikkeflikkeri.engine.rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import be.ikkeflikkeri.engine.components.lighting.BaseLightComponent;
import be.ikkeflikkeri.engine.components.rendering.CameraComponent;
import be.ikkeflikkeri.engine.core.Transform;
import be.ikkeflikkeri.engine.math.Vector3f;
import be.ikkeflikkeri.engine.objects.GameObject;
import be.ikkeflikkeri.engine.rendering.resources.MappedValues;

public class RenderingEngine extends MappedValues
{
	private Map<String, Integer> samplerMap;
	private ArrayList<BaseLightComponent> lights;
	
	private CameraComponent mainCamera;
	private BaseLightComponent activeLight;
	private Shader forwardAmbient;
	
	public RenderingEngine()
	{
		super();
		
		lights = new ArrayList<BaseLightComponent>();
		samplerMap = new HashMap<String, Integer>();
		samplerMap.put("diffuse", 0);
		forwardAmbient = new Shader("forward-ambient");
		
		addVector("ambient", new Vector3f(0.1f, 0.1f, 0.1f));
		
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		glFrontFace(GL_CW);
		glCullFace(GL_BACK);
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_DEPTH_CLAMP);
		glEnable(GL_TEXTURE_2D);
	}
	
	public void updateUniformStruct(Transform transform, Material material, Shader shader, String uniformName, String uniformType)
	{
		throw new IllegalArgumentException(uniformName + " is not a supported type in RenderingEngine!");
	}
	
	public void render(GameObject object)
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		object.renderAll(forwardAmbient, this);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_ONE, GL_ONE);
		glDepthMask(false);
		glDepthFunc(GL_EQUAL);
		
		for(BaseLightComponent light : lights)
		{
			activeLight = light;
			object.renderAll(light.getShader(), this);
		}

		glDepthFunc(GL_LESS);
		glDepthMask(true);
		glDisable(GL_BLEND);
	}

	public static String getOpenGLVersion()
	{
		return glGetString(GL_VERSION);
	}

	public CameraComponent getMainCamera()
	{
		return mainCamera;
	}

	public void setMainCamera(CameraComponent mainCamera)
	{
		this.mainCamera = mainCamera;
	}
	
	public BaseLightComponent getActiveLight()
	{
		return activeLight;
	}
	
	public void addLight(BaseLightComponent light)
	{
		lights.add(light);
	}
	
	public void removeLight(BaseLightComponent baseLight)
	{
		lights.remove(baseLight);
	}
	
	public void addCamera(CameraComponent camera)
	{
		mainCamera = camera;
	}

	public int getSamplerSlot(String name)
	{
		return samplerMap.get(name);
	}

	public void setWireFrameMode(boolean enabled)
	{
		if(enabled)
			glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		else
			glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
	}

	public void setAmbientLight(Vector3f ambientLight)
	{
		addVector("ambient", ambientLight);
	}
}
