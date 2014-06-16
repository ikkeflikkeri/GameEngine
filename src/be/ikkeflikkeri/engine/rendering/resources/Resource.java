package be.ikkeflikkeri.engine.rendering.resources;

public abstract class Resource
{
	private int references = 1;
	
	public void addReference()
	{
		references++;
	}
	
	public boolean removeReference()
	{
		references--;
		return references == 0;
	}
}
