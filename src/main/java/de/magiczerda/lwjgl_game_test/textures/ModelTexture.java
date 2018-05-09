package de.magiczerda.lwjgl_game_test.textures;

/**
 * Represents a texture which can texture a model
 * 
 * @author MagicZerda
 *
 */

public class ModelTexture {
	
	private int textureID;
	
	private float shineDamper = 1;	//this variable indicates how close the camera needs to be to the reflected light beam to see it
	private float reflectivity = 0;	//this variable indicates how much light is reflected
	
	public ModelTexture(int id) {
		this.textureID = id;
	}
	
	public int getID() {
		return this.textureID;
	}public float getShineDamper() {
		return shineDamper;
	}public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}public float getReflectivity() {
		return reflectivity;
	}public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}
}
