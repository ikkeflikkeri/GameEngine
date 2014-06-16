package be.ikkeflikkeri.engine.rendering.loading.mesh.OBJ;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import be.ikkeflikkeri.engine.core.Utilities;
import be.ikkeflikkeri.engine.math.Vector2f;
import be.ikkeflikkeri.engine.math.Vector3f;
import be.ikkeflikkeri.engine.rendering.loading.mesh.IndexedModel;

public class OBJModel
{
	private ArrayList<Vector3f> positions;
	private ArrayList<Vector2f> texCoords;
	private ArrayList<Vector3f> normals;
	private ArrayList<OBJIndex> indices;
	private boolean hasTexCoords;
	private boolean hasNormals;
	
	public OBJModel(String filename)
	{
		positions = new ArrayList<Vector3f>();
		texCoords = new ArrayList<Vector2f>();
		normals = new ArrayList<Vector3f>();
		indices = new ArrayList<OBJIndex>();
		
		hasTexCoords = false;
		hasNormals = false;
		
		BufferedReader meshReader = null;
		
		try
		{
			meshReader = new BufferedReader(new FileReader(filename));
			String line;

			while((line = meshReader.readLine()) != null)
			{
				String[] tokens = line.split(" ");
				tokens = Utilities.removeEmptyStrings(tokens);

				if(tokens.length == 0 || tokens[0].equals("#"))
					continue;
				else if(tokens[0].equals("v"))
					positions.add(new Vector3f(Float.valueOf(tokens[1]), Float.valueOf(tokens[2]), Float.valueOf(tokens[3])));
				else if(tokens[0].equals("vt"))
					texCoords.add(new Vector2f(Float.valueOf(tokens[1]), Float.valueOf(tokens[2])));
				else if(tokens[0].equals("vn"))
					normals.add(new Vector3f(Float.valueOf(tokens[1]), Float.valueOf(tokens[2]), Float.valueOf(tokens[3])));
				else if(tokens[0].equals("f"))
				{
					for(int i = 0; i < tokens.length - 3; i++)
					{
						indices.add(parseOBJIndex(tokens[1]));
						indices.add(parseOBJIndex(tokens[2 + i]));
						indices.add(parseOBJIndex(tokens[3 + i]));
					}
				}
			}

			meshReader.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public IndexedModel toIndexedModel()
	{
		IndexedModel resultModel = new IndexedModel();
		IndexedModel normalModel = new IndexedModel();
		Map<OBJIndex, Integer> resultIndexMap = new HashMap<OBJIndex, Integer>();
		Map<Integer, Integer> normalIndexMap = new HashMap<Integer, Integer>();
		Map<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
		
		for(int i = 0; i < indices.size(); i++)
		{
			OBJIndex index = indices.get(i);
			
			Vector3f currentPosition = positions.get(index.vertexIndex);
			Vector2f currentTexCoord = new Vector2f(0, 0);
			Vector3f currentNormal = new Vector3f(0, 0, 0);
			
			if(hasTexCoords)
				currentTexCoord = texCoords.get(index.texCoordIndex);
			
			if(hasNormals)
				currentNormal = normals.get(index.normalIndex);
			
			int modelVertexIndex = -1;
			
			if(resultIndexMap.containsKey(index))
				modelVertexIndex = resultIndexMap.get(index);
			
			if(modelVertexIndex == -1)
			{
				modelVertexIndex = resultModel.getPositions().size();
				resultIndexMap.put(index, modelVertexIndex);

				resultModel.getPositions().add(currentPosition);
				resultModel.getTexCoords().add(currentTexCoord);
				if(hasNormals)
					resultModel.getNormals().add(currentNormal);
			}
			
			int modelNormalIndex = -1;
			
			if(normalIndexMap.containsKey(index))
				modelNormalIndex = normalIndexMap.get(index.vertexIndex);
			
			if(modelNormalIndex == -1)
			{
				modelNormalIndex = normalModel.getPositions().size();
				normalIndexMap.put(index.vertexIndex, modelNormalIndex);
				
				normalModel.getPositions().add(currentPosition);
				normalModel.getTexCoords().add(currentTexCoord);
				normalModel.getNormals().add(currentNormal);
			}
			
			resultModel.getIndices().add(modelVertexIndex);
			normalModel.getIndices().add(modelNormalIndex);
			indexMap.put(modelVertexIndex, modelNormalIndex);
		}
		
		if(!hasNormals)
		{
			normalModel.calcNormals();
			
			for(int i = 0; i < resultModel.getPositions().size(); i++)
				resultModel.getNormals().add(normalModel.getNormals().get(indexMap.get(i)));
		}
		
		return resultModel;
	}

	private OBJIndex parseOBJIndex(String token)
	{
		String[] values = token.split("/");
		
		OBJIndex result = new OBJIndex();
		result.vertexIndex = Integer.parseInt(values[0]) - 1;
		
		if(values.length > 1)
		{
			
			if(!values[1].isEmpty())
			{
				hasTexCoords = true;
				result.texCoordIndex = Integer.parseInt(values[1]) - 1;
			}
			
			if(values.length > 2)
			{
				hasNormals = true;
				result.normalIndex = Integer.parseInt(values[2]) - 1;
			}
		}
		
		return result;
	}
}
