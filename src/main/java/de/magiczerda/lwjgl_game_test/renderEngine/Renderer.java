package de.magiczerda.lwjgl_game_test.renderEngine;

import java.util.List;
import java.util.Map;

import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import de.magiczerda.lwjgl_game_test.entities.Entity;
import de.magiczerda.lwjgl_game_test.models.RawModel;
import de.magiczerda.lwjgl_game_test.models.TexturedModel;
import de.magiczerda.lwjgl_game_test.options.Options;
import de.magiczerda.lwjgl_game_test.shaders.StaticShader;
import de.magiczerda.lwjgl_game_test.textures.ModelTexture;
import de.magiczerda.lwjgl_game_test.utils.Maths;

/**
 * This class renders a model from the VAO
 * 
 * @author MagicZerda
 *
 */

public class Renderer {
	
	private Matrix4f projectionMatrix;
	private StaticShader shader;
	
	public Vector4f clearColor = new Vector4f(0.5f, 0.7f, 1.0f, 0.0f);
	
	public Renderer(StaticShader shader) {
		this.shader = shader;
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);	//enable back culling
		createProjectionMatrix();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
	
	/**
	 * Prepares OpenGL to render the object
	 */
	public void prepare() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);	//test if there are triangles on top of each other to not end up with a total mess
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);	//clear both the color and the depth buffer
		GL11.glClearColor(clearColor.x, clearColor.y, clearColor.z, clearColor.w);
	}
	
	public void render(Map<TexturedModel, List<Entity>> entities) {
		for(TexturedModel model : entities.keySet()) {
			prepareTexturedModel(model);
			List<Entity> batch = entities.get(model);
			for(Entity entity : batch) {
				prepareInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);	//use GL_LINES for wireframe
			}
			unbindTexturedModel();
		}
	}
	
	private void prepareTexturedModel(TexturedModel texturedModel) {
		RawModel model = texturedModel.getRawModel();
		GL30.glBindVertexArray(model.getVaoID());	//bind the VAO
		GL20.glEnableVertexAttribArray(0);			//enable array 0 (vertex positions)
		GL20.glEnableVertexAttribArray(1);			//enable array 1 (texture coords)
		GL20.glEnableVertexAttribArray(2);			//enable array 2 (normal vectors)
		
		ModelTexture texture = texturedModel.getModelTexture();
		shader.loadShineVariables(texture.getShineDamper(),texture.getReflectivity());	//loads up the specular lighting variables to the shaderr
		GL13.glActiveTexture(GL13.GL_TEXTURE0);	//Sampler2D (fragment shader) uses this by default
		GL11.glBindTexture(GL11.GL_PROXY_TEXTURE_2D, texturedModel.getModelTexture().getID());
	}
	
	private void unbindTexturedModel() {
		GL20.glDisableVertexAttribArray(0);			//disable the positions attribute list
		GL20.glDisableVertexAttribArray(1);			//disable the texture coords attribute list
		GL20.glDisableVertexAttribArray(2);			//disable the normal vector attribute list
		GL30.glBindVertexArray(0);					//unbind the VAO
	}
	
	private void prepareInstance(Entity entity) {
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());	//get the transformation matrix for the entity
		shader.loadTransformationMatrix(transformationMatrix);
	}
	
	/**
	 * Creates the projectionMatrix
	 * 
	 * see http://www.songho.ca/opengl/gl_projectionmatrix.html
	 * 
	 */
	
	private void createProjectionMatrix(){
        float aspectRatio = (float)(Options.DISPLAY_SIZE.x / Options.DISPLAY_SIZE.y);
		//float aspectRatio = 1080 / 720;
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(Options.FOV/2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = Options.FAR_PLANE - Options.NEAR_PLANE;
        
        projectionMatrix = new Matrix4f();
        projectionMatrix.m00(x_scale);// = x_scale;
        projectionMatrix.m11(y_scale);// = y_scale;
        projectionMatrix.m22(-((Options.FAR_PLANE + Options.NEAR_PLANE) / frustum_length));
        projectionMatrix.m23(-1);// = -1;
        projectionMatrix.m32(-((2 * Options.NEAR_PLANE * Options.FAR_PLANE) / frustum_length));
        projectionMatrix.m33(0);// = 0;
	}
}

