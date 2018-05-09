package de.magiczerda.lwjgl_game_test.renderEngine;

import java.awt.image.BufferedImage;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import de.magiczerda.lwjgl_game_test.models.RawModel;
import de.magiczerda.lwjgl_game_test.textures.TextureLoader;

/**
 * Loads 3D models into memory by storing positional data about the model in a VAO
 * 
 * @author MagicZerda
 *
 */

public class Loader {
	
	
	/**
	 * Takes in vertex positions of a model
	 * and returns information about the VAO as a RawModel
	 * 
	 * 
	 * @param positions
	 * @return
	 */
	
	private List<Integer> vaos = new ArrayList<Integer>();	//delete all the VAOs when the game closes
	private List<Integer> vbos = new ArrayList<Integer>();	//delete all the VBOs when the game closes
	private List<Integer> textures = new ArrayList<Integer>(); 	//delete all the textures when the game closes
	
	public RawModel loadToVAO(float[] positions, float[] textureCoords, float[] normals, int[] indices) {
		int vaoID = createVAO();	//creates a new empty VAO and stores its ID in vaoID, see createVAO
		bindIndicesBuffer(indices);
		storeDataInAttributeList(0, 3, positions);		//stores the vertex positions in the 0th attribute list of the VAO
		storeDataInAttributeList(1, 2, textureCoords);	//stores the texture coords in the 1st attribute list of the VAO
		storeDataInAttributeList(2, 3, normals);		//stores the normal vectors in the 3rd attribute list of the VAO
		unbindVAO();
		return new RawModel(vaoID, indices.length);
	}
	
	public int loadTexture(String filename) {
		BufferedImage image = TextureLoader.loadImage("res/" + filename + ".png");
		int textureID = TextureLoader.loadTexture(image);
		
		textures.add(textureID);
		return textureID;
	}
	
	/**
	 * Deletes all of the VAOs and VBOs when the game closes
	 */
	public void cleanUp() {
		for(int vao : vaos) GL30.glDeleteVertexArrays(vao);	//deletes all of the vaos
		for(int vbo : vbos) GL15.glDeleteBuffers(vbo);		//deletes all of the vbos
		for(int texture : textures) GL11.glDeleteTextures(texture);	//deletes all of the textures
	}
	
	/** 
	 * Creates an empty VAO and returns its ID
	 * 
	 * @return
	 */
	private int createVAO() {
		int vaoID = GL30.glGenVertexArrays();	//creates an empty VAO and return its ID
		vaos.add(vaoID);
		GL30.glBindVertexArray(vaoID);			//activates the VAO
		return vaoID;
	}
	
	/**
	 * Stores data in an attribute list of the VAO
	 * 
	 * @param attributeNumber
	 * @param data
	 */
	
	private void storeDataInAttributeList(int attributeNumber, int coordinateSize, float[] data) {
		//We have to store the data as a VBO
		int vboID = GL15.glGenBuffers();	//creates the VBO
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);	//activates/ binds the VBO
		FloatBuffer buffer = storeDataInFloatBuffer(data);	//buffer is the data in the form of a FloatBuffer
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);	//store the data into the VBO (GL_STATIC_DRAW means we're not going to edit the data once it's written into the VBO)
		
		/**
		 * The following line puts the VBO into an attribute list of the VAO
		 * 
		 * attributeNumber is the ID of the attribute list in which we want to store the data
		 * coordinateSize indicates how many values are stored per vertex (3 for vertex positions, 2 for texture coordinates and so on)
		 * the type of data is float
		 * our data isn't normalized
		 * the distance between the data is 0
		 * the offset is 0 (start at the beginning of the data)
		 */
		GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);	//unbind the current VBO
	}
	
	/**
	 * unbinds the currently bound VAO
	 */
	
	private void unbindVAO() {
		GL30.glBindVertexArray(0);	//unbinds the currently bound VAO
	}
	
	/**
	 * Loads an index buffer and binds it to the VAO
	 * 
	 * @param indices
	 */
	private void bindIndicesBuffer(int[] indices) {
		int vboID = GL15.glGenBuffers();	//create a VBO
		vbos.add(vboID);	//add the VBO to the list
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);	//bind the VBO as an element array buffer
		IntBuffer buffer = storeDataInIntBuffer(indices);	//buffer is indices as an IntBuffer
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);	//store the data into the VBO (GL_STATIC_DRAW means we're not going to edit the data once it's written into the VBO)
	}
	
	/**
	 * Data must be stored into the VBO (element array buffer) as an int buffer, 
	 * so we need to create a method which will convert an int[]
	 * into an IntBuffer
	 */
	
	private IntBuffer storeDataInIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);	//create the FloatBuffer
		buffer.put(data);
		buffer.flip();	//flip from writing mode to reading mode
		return buffer;
	}
	
	/**
	 * Data must be stored into the VBO as a float buffer, 
	 * so we need to create a method which will convert a float[]
	 * into a FloatBuffer 
	 */
	
	private FloatBuffer storeDataInFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);	//create the FloatBuffer
		buffer.put(data);
		buffer.flip();	//flip from writing mode to reading mode
		return buffer;
	}

}
