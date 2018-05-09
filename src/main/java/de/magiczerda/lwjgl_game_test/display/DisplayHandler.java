package de.magiczerda.lwjgl_game_test.display;

import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import de.magiczerda.lwjgl_game_test.controls.Input;
import de.magiczerda.lwjgl_game_test.entities.Camera;
import de.magiczerda.lwjgl_game_test.options.Options;

public class DisplayHandler {
	
	private Camera camera;
	
	public DisplayHandler(Camera camera) {
		this.camera = camera;
	}
	
	//window ID
	public long window = -1l;
	
	public void init(int width, int height, String title) {
		//Error callback will be printed in System.err
		GLFWErrorCallback.createPrint(System.err).set();
		
		//init GLFW
		if(!GLFW.glfwInit())
			throw new IllegalStateException("Could not initialize GLFW!");
		
		//GLFW configuration
		GLFW.glfwDefaultWindowHints();	//for clarity (this is already the default)
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);	//the window will stay invisible
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);	//the window shouldn't be rezizable
		
		//window creation
		window = GLFW.glfwCreateWindow(width, height, title.subSequence(0, title.length()), 0l, 0l);
		if(window == -1l)
			throw new RuntimeException("Failed to create the GLFW window!");
		
		Input input = new Input(camera);
		
		GLFW.glfwSetCursorPos(window, Options.DISPLAY_SIZE.x / 2, Options.DISPLAY_SIZE.y / 2);	//place the cursor in the center of the window
		GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
		
		//setup key callback- this will involke the controlls
		GLFW.glfwSetKeyCallback(window, new GLFWKeyCallbackI() {
			public void invoke(long window, int key, int scancode, int action, int mods) {
				input.keyInput(window, key, action);	//process all inputs in the Input.keyInput method
			}
		});
		
		//get the thread stack and push a new frame
		try(MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1);	//int*
			IntBuffer pHeight = stack.mallocInt(1);	//int*
			
			//get the window size passed to glfwCreateWindow
			GLFW.glfwGetWindowSize(window, pWidth, pHeight);
			
			//get the resolution of the primary monitor
			GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
			
			//center the window
			GLFW.glfwSetWindowPos(window, (vidMode.width() - pWidth.get(0)) / 2, (vidMode.height() - pHeight.get(0)) / 2);
		}	//the stack frame is popped automatically
		
		//make OpenGL context current
		GLFW.glfwMakeContextCurrent(window);
		
		//enable v-sync
		if(Options.VSYNC)
			GLFW.glfwSwapInterval(1);
		
		//make the window visible
		GLFW.glfwShowWindow(window);
		
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();
	}
	
	public void loop() {
		GLFW.glfwSwapBuffers(window);	//swap the color buffers
			
		//poll for window events (e.g. the key callback)
		GLFW.glfwPollEvents();
	}
}
