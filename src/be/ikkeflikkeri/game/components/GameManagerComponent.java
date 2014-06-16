package be.ikkeflikkeri.game.components;

import be.ikkeflikkeri.engine.components.GameComponent;
import be.ikkeflikkeri.engine.core.Input;
import be.ikkeflikkeri.engine.rendering.loading.texture.Image;

public class GameManagerComponent extends GameComponent
{
	Image level;
	LevelGeneratorComponent levelGenerator;
	
	public GameManagerComponent(String filename)
	{
		this.level = new Image(filename, false);
	}
	
	@Override
	public void input(float delta)
	{
		if(Input.isKeyPressed(Input.KEY_F1))
		{
			level = new Image("level1.png", false);
			levelGenerator.generateMesh(level);
		}
		
		if(Input.isKeyPressed(Input.KEY_F2))
		{
			level = new Image("level2.png", false);
			levelGenerator.generateMesh(level);
		}
	}
	
	@Override
	public void update(float delta)
	{
		if(levelGenerator == null)
		{
			levelGenerator = (LevelGeneratorComponent)getParent().findGameObjectByName("LevelGenerator")[0].getComponents(LevelGeneratorComponent.class);
			levelGenerator.generateMesh(level);
		}
	}
	
	public void setLevel(String filename)
	{
		level = new Image(filename, false);
	}
	
	public Image getLevel()
	{
		return level;
	}
}
