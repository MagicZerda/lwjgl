package de.magiczerda.lwjgl_game_test.controls;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

import de.magiczerda.lwjgl_game_test.entities.Camera;

public class Input {
	
	private Camera camera;
	
	public Input(Camera camera) {
		this.camera = camera;
	}
	
	public void keyInput(long window, int key, int action) {
		if(key == GLFW_KEY_TAB) glfwSetWindowShouldClose(window, true);
		
		if(action == GLFW_PRESS) {
			if(key == GLFW_KEY_W) camera.setMoveForward(true);
			if(key == GLFW_KEY_A) camera.setMoveLeft(true);
			if(key == GLFW_KEY_S) camera.setMoveBackward(true);
			if(key == GLFW_KEY_D) camera.setMoveRight(true);
			if(key == GLFW_KEY_SPACE) camera.setAscent(true);
			if(key == GLFW_KEY_LEFT_SHIFT) camera.setDecent(true);
		} if(action == GLFW_RELEASE) {
			if(key == GLFW_KEY_W) camera.setMoveForward(false);
			if(key == GLFW_KEY_A) camera.setMoveLeft(false);
			if(key == GLFW_KEY_S) camera.setMoveBackward(false);
			if(key == GLFW_KEY_D) camera.setMoveRight(false);
			if(key == GLFW_KEY_SPACE) camera.setAscent(false);
			if(key == GLFW_KEY_LEFT_SHIFT) camera.setDecent(false);
		}
	}
	
	public void mousePos(double xPos, double yPos) {
		
	}
	
}
