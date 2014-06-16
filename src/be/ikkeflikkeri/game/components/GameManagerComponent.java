package be.ikkeflikkeri.game.components;

import be.ikkeflikkeri.engine.components.GameComponent;
import be.ikkeflikkeri.engine.core.Input;
import be.ikkeflikkeri.engine.rendering.loading.texture.Image;

public class GameManagerComponent extends GameComponent
{
	Image level;
	LevelGeneratorComponent levelGenerator;
	PlayerComponent player;
	
	public GameManagerComponent(String filename)
	{
		this.level = new Image(filename, false);
	}
	
	@Override
	public void input(float delta)
	{
		if(Input.isKeyPressed(Input.KEY_F1))
		{
			setLevel("level1.png");
		}
		
		if(Input.isKeyPressed(Input.KEY_F2))
		{
			setLevel("level2.png");
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
		
		if(player == null)
		{
			player = (PlayerComponent)getParent().findGameObjectByName("Player")[0].getComponents(PlayerComponent.class);
			player.setLevel(level);
		}
	}
	
	public void setLevel(String filename)
	{
		level = new Image(filename, false);
		
		if(player != null)
			player.setLevel(level);
		
		if(levelGenerator != null)
			levelGenerator.generateMesh(level);
	}
	
	public Image getLevel()
	{
		return level;
	}
}
