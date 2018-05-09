package de.magiczerda.lwjgl_game_test.controls;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_TAB;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

import java.nio.DoubleBuffer;

import org.joml.Vector2f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;

import de.magiczerda.lwjgl_game_test.entities.Camera;
import de.magiczerda.lwjgl_game_test.options.Options;

public class Input {
	
	private DoubleBuffer mouseX;
	private DoubleBuffer mouseY;
	
	private float currentX = 0;
	private float currentY = 0;
	
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
	
	public void updateMouse(long window) {
		camera.changeCameraView(getMouseDelta(window));
	}
	
	private Vector2f getMouseDelta(long window) {
		mouseX = BufferUtils.createDoubleBuffer(1);
		mouseY = BufferUtils.createDoubleBuffer(1);
		
		GLFW.glfwGetCursorPos(window, mouseX, mouseY);
		
		float x = (float) mouseX.duplicate().get();
		float y = (float) mouseY.duplicate().get();
		
		Vector2f mousePosition = new Vector2f(x - currentX, y - currentY);
		mousePosition.mul(Options.MOUSE_SENSITIVITY);
		
		currentX = x;
		currentY = y;
		
		return mousePosition;
	}
}
