package de.magiczerda.lwjgl_game_test.renderEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.magiczerda.lwjgl_game_test.entities.Camera;
import de.magiczerda.lwjgl_game_test.entities.Entity;
import de.magiczerda.lwjgl_game_test.entities.Light;
import de.magiczerda.lwjgl_game_test.models.TexturedModel;
import de.magiczerda.lwjgl_game_test.shaders.StaticShader;

public class MasterRenderer {
	
	private StaticShader shader = new StaticShader();
	private Renderer renderer = new Renderer(shader);
	
	private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();	//we have one model and multiple instances of it
	
	public void render(Light sun, Camera camera) {
		renderer.prepare();
		shader.start();
		shader.loadLightVariables(sun);
		shader.loadViewMatrix(camera);
		renderer.render(entities);
		shader.stop();
		entities.clear();//otherwise the entities would stack
	}
	
	public void processEntity(Entity entity) {
		TexturedModel texturedModel = entity.getModel();
		List<Entity> batch = entities.get(texturedModel);
		if(batch != null) batch.add(entity);
		else {
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(texturedModel, newBatch);
		}
	}
	
	public void cleanUp() {
		shader.cleanUp();
	}
}
