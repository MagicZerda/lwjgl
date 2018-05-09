package de.magiczerda.lwjgl_game_test.utils;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import de.magiczerda.lwjgl_game_test.entities.Camera;

public class Maths {
	
	public static Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry, float rz, float scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.identity();
		matrix.translate(translation);
		matrix.rotate((float)Math.toRadians(rx), 1,0,0);
		matrix.rotate((float)Math.toRadians(ry), 0,1,0);
		matrix.rotate((float)Math.toRadians(rz), 0,0,1);
		matrix.scale(new Vector3f(scale, scale, scale));
		
		return matrix;
	}
	
	public static Matrix4f createViewMatrix(Camera camera) {
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.identity();
		viewMatrix.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1,0,0), viewMatrix);
		viewMatrix.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0,1,0), viewMatrix);
		viewMatrix.rotate((float) Math.toRadians(camera.getRoll()), new Vector3f(0,0,1), viewMatrix);
		Vector3f cameraPos = camera.getPosition();
		Vector3f negCamPos = new Vector3f(-cameraPos.x, -cameraPos.y, -cameraPos.z);
		viewMatrix.translate(negCamPos, viewMatrix);
		return viewMatrix;
	}
	
}
