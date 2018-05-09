package de.magiczerda.lwjgl_game_test.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;

import de.magiczerda.lwjgl_game_test.models.RawModel;
import de.magiczerda.lwjgl_game_test.renderEngine.Loader;

public class OBJParser {
	
	public static RawModel loadOBJModel(String fileName, Loader loader) {
		FileReader fr = null;
		try {
			fr = new FileReader(new File("res/" + fileName + ".obj"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		BufferedReader br = new BufferedReader(fr);
		String line;
		List<Vector3f> vertices = new ArrayList<Vector3f>();
		List<Vector2f> textureCoords = new ArrayList<Vector2f>();
		List<Vector3f> normals = new ArrayList<Vector3f>();
		List<Integer> indices = new ArrayList<Integer>();
		float[] verticesArray = null;
		float[] normalsArray = null;
		float[] textureArray = null;
		int[] indicesArray = null;
		
		try {
			
			while (true) {
				line = br.readLine();
				String[] currentLine = line.split(" ");
				if (line.startsWith("v ")) { // vertex position
					Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]),
							Float.parseFloat(currentLine[3])); // currentLine[0] would be "v "
					vertices.add(vertex);
				} else if (line.startsWith("vt ")) { // texture coorinate
					Vector2f textureCoord = new Vector2f(Float.parseFloat(currentLine[1]),
							Float.parseFloat(currentLine[2])); // currentLine[0] would be "vt "
					textureCoords.add(textureCoord);
				} else if (line.startsWith("vn ")) { // normal vector
					Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]),
							Float.parseFloat(currentLine[3])); // currentLine[0] would be "vn "
					normals.add(normal);
				} else if (line.startsWith("f ")) { // arrangement
					textureArray = new float[vertices.size() * 2]; // if we go on to "f ", we are in another section of
																	// the OBJ- File
					normalsArray = new float[vertices.size() * 3];
					break;
				}
			}
			
			while(line != null) {
				if(!line.startsWith("f ")) {
					line = br.readLine();
					continue;
				}
				
				String[] currentLine = line.split(" ");	//break the line up into three parts, one for each vertex
				String[] vertex1 = currentLine[1].split("/");
				String[] vertex2 = currentLine[2].split("/");
				String[] vertex3 = currentLine[3].split("/");
				
				processVertex(vertex1, indices, textureCoords, normals, textureArray, normalsArray);
				processVertex(vertex2, indices, textureCoords, normals, textureArray, normalsArray);
				processVertex(vertex3, indices, textureCoords, normals, textureArray, normalsArray);
				line = br.readLine();
			}
			
			br.close();
			
		} catch(Exception e) {
			e.printStackTrace();
			System.err.println("Invalid OBJ file format");
		}
		
		verticesArray = new float[vertices.size() * 3];	//we are using floats instead of vectors now, so we have to multipy the size by three
		indicesArray = new int[indices.size()];
		
		int vertexPointer = 0;
		for(Vector3f vertex : vertices) {
			verticesArray[vertexPointer ++] = vertex.x;
			verticesArray[vertexPointer ++] = vertex.y;
			verticesArray[vertexPointer ++] = vertex.z;
		}
		
		for(int i = 0; i < indices.size(); i++) {
			indicesArray[i] = indices.get(i);
		}
		
		return loader.loadToVAO(verticesArray, textureArray, normalsArray, indicesArray);
	}
	
	private static void processVertex(String[] vertexData, List<Integer> indices, List<Vector2f> textureCoords, List<Vector3f> normalVectors, float[] textureCoordsArray, float[] normalsArray) {
		int currentVertexPointer = Integer.parseInt(vertexData[0]) -1;	//OBJ Files start at 1
		indices.add(currentVertexPointer);
		Vector2f currentTextureCoordinate = textureCoords.get(Integer.parseInt(vertexData[1]) -1);
		textureCoordsArray[currentVertexPointer * 2] = currentTextureCoordinate.x;
		textureCoordsArray[currentVertexPointer * 2 +1] = 1 - currentTextureCoordinate.y;	//OpenGL starts at the top left of the texture, OBJ Files at the bottom left
		Vector3f currentNormalVector = normalVectors.get(Integer.parseInt(vertexData[2]) -1);
		normalsArray[currentVertexPointer * 3] = currentNormalVector.x;
		normalsArray[currentVertexPointer * 3 + 1] = currentNormalVector.y;
		normalsArray[currentVertexPointer * 3 + 2] = currentNormalVector.z;
	}
	
}
