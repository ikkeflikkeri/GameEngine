package be.ikkeflikkeri.engine.rendering.loading.mesh.OBJ;

public class OBJIndex
{
	public int vertexIndex;
	public int texCoordIndex;
	public int normalIndex;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + normalIndex;
		result = prime * result + texCoordIndex;
		result = prime * result + vertexIndex;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		OBJIndex other = (OBJIndex) obj;
		
		if (normalIndex != other.normalIndex)
			return false;
		if (texCoordIndex != other.texCoordIndex)
			return false;
		if (vertexIndex != other.vertexIndex)
			return false;
		
		return true;
	}
}
