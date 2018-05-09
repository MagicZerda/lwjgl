package de.magiczerda.lwjgl_game_test.main;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import de.magiczerda.lwjgl_game_test.controls.Input;
import de.magiczerda.lwjgl_game_test.display.DisplayHandler;
import de.magiczerda.lwjgl_game_test.entities.Camera;
import de.magiczerda.lwjgl_game_test.entities.Entity;
import de.magiczerda.lwjgl_game_test.entities.Light;
import de.magiczerda.lwjgl_game_test.models.RawModel;
import de.magiczerda.lwjgl_game_test.models.TexturedModel;
import de.magiczerda.lwjgl_game_test.options.Options;
import de.magiczerda.lwjgl_game_test.parser.OBJParser;
import de.magiczerda.lwjgl_game_test.renderEngine.Loader;
import de.magiczerda.lwjgl_game_test.renderEngine.MasterRenderer;
import de.magiczerda.lwjgl_game_test.textures.ModelTexture;

public class Game {
	
	//------------objects--------------
	
	DisplayHandler display;
	Loader loader;
	ModelTexture texture;
	Camera camera;
	Input input;
	//---------------------------------
	
	//test data
	
	RawModel model;
	
	TexturedModel texturedModel;
	MasterRenderer renderer;
	Entity entity;
	Light light;
	
	//---------------------------------
	
	public Game() {
		//-------OpenGL initialization---------
		camera = new Camera();
		display = new DisplayHandler(camera);
		display.init(Options.DISPLAY_SIZE.x, Options.DISPLAY_SIZE.y, Options.DISPLAY_TITLE);
		//-------------------------------------
		
		//--------object initialisation---------
		loader = new Loader();
		texture = new ModelTexture(loader.loadTexture("test/grey"));
		input = new Input(camera);
		light = new Light(new Vector3f(-1, 4, -1), new Vector3f(0.25f, 0.75f, 1));
		//--------------------------------------
		
		model = OBJParser.loadOBJModel("test/dragon", loader);
		texturedModel = new TexturedModel(model, texture);
		entity = new Entity(texturedModel, new Vector3f(0, 0, -5f), 0, 0, 0, 1f);
		renderer = new MasterRenderer();
		
		gameLoop();
		closeGame();
	}
	
	//----------------game loop-------------------
	
	int fps = 0;
	private void gameLoop() {
		long timer = System.currentTimeMillis();
		while(!GLFW.glfwWindowShouldClose(display.window)) {
			
			update();
			fps ++;
			
			if((System.currentTimeMillis() - timer) >= 1000) {
				timer = System.currentTimeMillis();
				System.out.println("FPS: " + fps);
				fps = 0;
			}
		}
	}
	
	//-------------------------------------------
	
	private void update() {
		camera.move();
		
		renderer.processEntity(entity);
		
		renderer.render(light, camera);
		display.loop();
	}
	
	//------------shut down------------------
	private void closeGame() {
		renderer.cleanUp();
		loader.cleanUp();
	}
}
