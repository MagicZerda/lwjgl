package de.magiczerda.lwjgl_game_test.controls;

import org.lwjgl.glfw.GLFW;

import de.magiczerda.lwjgl_game_test.display.DisplayHandler;
import de.magiczerda.lwjgl_game_test.entities.Camera;

public class AngleHandler implements Runnable {
	
	DisplayHandler displayHandler;
	
	public AngleHandler(DisplayHandler displayHandler) {
		this.displayHandler = displayHandler;
	}
	
	private void handle() {
		if(Camera.pitch >= 360 || Camera.pitch <= -360) Camera.pitch = 0;
		if(Camera.yaw >= 360 || Camera.yaw <= -360) Camera.yaw = 0;
		if(Camera.pitch >= 100) Camera.pitch = 100;
		if(Camera.pitch <= -100) Camera.pitch = -100;
	}

	@Override
	public void run() {
		while(!GLFW.glfwWindowShouldClose(displayHandler.window))
			handle();
	}
	
}
