package de.magiczerda.lwjgl_game_test.options;

import org.joml.Vector2i;

public class Options {
	
	public static final float FOV = 70;
	public static final float NEAR_PLANE = 0.1f;
	public static final float FAR_PLANE = 1000;
	
	public static boolean VSYNC = true;
	public static Vector2i DISPLAY_SIZE = new Vector2i(1080, 720);
	public static String DISPLAY_TITLE = "LWJGL";
	
	public static final float MOVE_SPEED = 0.0005f;
	public static final float MOUSE_SENSITIVITY = 0.01f;
}
