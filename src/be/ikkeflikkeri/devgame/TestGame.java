package be.ikkeflikkeri.devgame;

import be.ikkeflikkeri.engine.components.lighting.PointLightComponent;
import be.ikkeflikkeri.engine.components.lighting.SpotLightComponent;
import be.ikkeflikkeri.engine.components.movement.ConstantRotationComponent;
import be.ikkeflikkeri.engine.components.movement.LookAtObjectComponent;
import be.ikkeflikkeri.engine.components.rendering.WireFrameModeComponent;
import be.ikkeflikkeri.engine.core.Game;
import be.ikkeflikkeri.engine.core.Input;
import be.ikkeflikkeri.engine.math.Vector3f;
import be.ikkeflikkeri.engine.objects.GameObject;
import be.ikkeflikkeri.engine.objects.rendering.CameraObject;
import be.ikkeflikkeri.engine.objects.rendering.MeshObject;
import be.ikkeflikkeri.engine.objects.shapes.PlaneObject;
import be.ikkeflikkeri.engine.rendering.Attenuation;
import be.ikkeflikkeri.engine.rendering.Material;
import be.ikkeflikkeri.engine.rendering.Mesh;

public class TestGame extends Game
{
	@Override
	public void initialize()
	{		
		Mesh ballMesh = new Mesh("ball.obj");
		Mesh monkeyMesh = new Mesh("monkey.obj");
		
		Material planeMaterial = new Material("bricks.jpg", 0, 8);
		Material ballMaterial = new Material("ball.png", 1, 8);
		Material monkeyMaterial = new Material("bricks.jpg", 1, 32);
		
		GameObject camera = new CameraObject(75, true);
		GameObject planeObject = new PlaneObject(planeMaterial);
		GameObject spotLightObject1 = new GameObject();
		GameObject spotLightObject2 = new GameObject();
		GameObject pointLightObject = new GameObject();
		GameObject ballObject = new MeshObject(ballMesh, ballMaterial);
		GameObject monkeyObject = new MeshObject(monkeyMesh, monkeyMaterial);
		
		planeObject.getTransform().moveTo(0, -1, 0);
		planeObject.getTransform().getScale().set(10, 1, 10);

		spotLightObject1.addComponent(new SpotLightComponent(new Vector3f(1, 0, 0), 1f, new Attenuation(0, 0, 0.05f), 0f)).addComponent(new ConstantRotationComponent(360, Vector3f.UP));
		spotLightObject1.getTransform().rotate(Vector3f.UP, 90);
		spotLightObject1.getTransform().moveTo(0, 1, 0);
		
		spotLightObject2.addComponent(new SpotLightComponent(new Vector3f(0, 0, 1), 1f, new Attenuation(0, 0, 0.05f), 0f)).addComponent(new ConstantRotationComponent(360, Vector3f.UP));
		spotLightObject2.getTransform().rotate(Vector3f.UP, -90);
		spotLightObject2.getTransform().moveTo(0, 1, 0);
		
		pointLightObject.addComponent(new PointLightComponent(new Vector3f(0, 1, 0), 0.8f, new Attenuation(0, 0, 0.5f)));
		pointLightObject.getTransform().moveTo(-5, 1, 0);
				
		ballObject.addComponent(new ConstantRotationComponent(180, Vector3f.UP));
		ballObject.getTransform().moveTo(0, 1.75f, 0);
		ballObject.getTransform().getScale().set(0.0125f, 0.0125f, 0.0125f);
		
		monkeyObject.addComponent(new LookAtObjectComponent(5, camera));
		monkeyObject.getTransform().moveTo(5, 0, 0);
		monkeyObject.addChild(ballObject);
		
		camera.addComponent(new WireFrameModeComponent(Input.KEY_F1));
		camera.getTransform().rotate(Vector3f.UP, 90);
		
		addObject(camera);
		addObject(planeObject);
		addObject(pointLightObject);
		addObject(spotLightObject1);
		addObject(spotLightObject2);
		addObject(monkeyObject);
	}
}
