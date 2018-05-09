package de.magiczerda.lwjgl_game_test.shaders;

import org.joml.Matrix4f;

import de.magiczerda.lwjgl_game_test.entities.Camera;
import de.magiczerda.lwjgl_game_test.entities.Light;
import de.magiczerda.lwjgl_game_test.utils.Maths;

public class StaticShader extends ShaderProgram {
	
	private static final String VERTEX_FILE = "src/main/java/de/magiczerda/lwjgl_game_test/shaders/vertexShader.glsl";
	private static final String FRAGMENT_FILE = "src/main/java/de/magiczerda/lwjgl_game_test/shaders/fragmentShader.glsl";
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPosition;
	private int location_lightColor;
	private int location_shineDamper;
	private int location_reflectivity;
	
	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");	//pass attribute list number 0 to the vertex shader
		super.bindAttribute(1, "textureCoords"); //pass attribute list number 1 to the vertex shader
		super.bindAttribute(2, "normal");	//pass attribute list number 2 to the vertex shader
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_lightPosition = super.getUniformLocation("lightPosition");
		location_lightColor = super.getUniformLocation("lightColor");
		location_shineDamper = super.getUniformLocation("shineDamper");
		location_reflectivity = super.getUniformLocation("reflectivity");
	}
	
	/**
	 * Loads up the light variables to the shaders
	 * 
	 * @param light
	 */
	
	public void loadLightVariables(Light light) {
		super.loadVector(location_lightPosition, light.getPosition());
		super.loadVector(location_lightColor, light.getColor());
	}
	
	/**
	 * This method loads up the specular lighting variables to the shader
	 * 
	 * @param damper- determines how close the camera needs to be to the reflected light beam to see it
	 * @param reflectivity- determines how much light gets reflected
	 */
	
	public void loadShineVariables(float damper, float reflectivity) {
		super.loadFloat(location_reflectivity, reflectivity);
		super.loadFloat(location_shineDamper, damper);
	}
	
	public void loadTransformationMatrix(Matrix4f transformationMatrix) {
		super.loadMatrix(location_transformationMatrix, transformationMatrix);
	}
	
	public void loadProjectionMatrix(Matrix4f projectionMatrix) {
		super.loadMatrix(location_projectionMatrix, projectionMatrix);
	}
	
	public void loadViewMatrix(Camera camera) {
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}

}
