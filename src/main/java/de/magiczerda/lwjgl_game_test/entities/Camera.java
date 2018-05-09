package de.magiczerda.lwjgl_game_test.entities;

import org.joml.Vector2f;
import org.joml.Vector3f;

import de.magiczerda.lwjgl_game_test.options.Options;

public class Camera {
	
	private Vector3f position = new Vector3f(0, 0, 0);	//initial camera position
	private float pitch;
	private float yaw;
	private float roll;
	
	private boolean moveForward = false;
	private boolean moveRight = false;
	private boolean moveLeft = false;
	private boolean moveBackward = false;
	private boolean ascent = false;
	private boolean decent = false;
	
	public void move() {
		Vector3f dPosition = new Vector3f();
		
		if(pitch >= 360 || pitch <= -360) this.pitch = 0;
		if(yaw >= 360 || yaw <= -360) this.yaw = 0;
		if(pitch >= 110) this.pitch = 110;
		if(pitch <= -100) this.pitch = -100;
		
		
		/*
		 * I'd like to thank my maths teacher Mrs. Eckstein for helping me out with this :)
		 */
		
		float frustum_length = Options.NEAR_PLANE + Options.FAR_PLANE;
		
		if(moveForward) {
			dPosition.z += (float) (Math.sin(Math.toRadians(yaw - 90)) * frustum_length) * Options.MOVE_SPEED;
			dPosition.x += (float) (Math.cos(Math.toRadians(yaw - 90)) * frustum_length) * Options.MOVE_SPEED;
		}
		if(moveBackward) {
			dPosition.z += -(float) (Math.sin(Math.toRadians(yaw - 90)) * frustum_length) * Options.MOVE_SPEED;
			dPosition.x += -(float) (Math.cos(Math.toRadians(yaw - 90)) * frustum_length) * Options.MOVE_SPEED;
		}
		if(moveRight) {
			dPosition.z += (float) (Math.sin(Math.toRadians(yaw)) * frustum_length) * Options.MOVE_SPEED;
			dPosition.x += (float) (Math.cos(Math.toRadians(yaw)) * frustum_length) * Options.MOVE_SPEED;
		}
		if(moveLeft) {
			dPosition.z += -(float) (Math.sin(Math.toRadians(yaw)) * frustum_length) * Options.MOVE_SPEED;
			dPosition.x += -(float) (Math.cos(Math.toRadians(yaw)) * frustum_length) * Options.MOVE_SPEED;
		}
		
		//-----------------------------------------------------------------------------------
		
		if(ascent) dPosition.y = Options.MOVE_SPEED * 1000;
		if(decent) dPosition.y = -Options.MOVE_SPEED * 1000;
		
		this.position.add(dPosition);
	}

	public void setMoveForward(boolean moveForward) {
		this.moveForward = moveForward;
	}public void setMoveRight(boolean moveRight) {
		this.moveRight = moveRight;
	}public void setMoveLeft(boolean moveLeft) {
		this.moveLeft = moveLeft;
	}public void setMoveBackward(boolean moveBackward) {
		this.moveBackward = moveBackward;
	}public void setAscent(boolean ascent) {
		this.ascent = ascent;
	}public void setDecent(boolean decent) {
		this.decent = decent;
	}
	
	/**
	 * Changes the pitch and the yaw of the camera
	 * pitchYaw[0] is yaw and [1] is pitch
	 * 	
	 * @param pitchYaw
	 */
	
	public void changeCameraView(Vector2f pitchYaw) {
		this.yaw += pitchYaw.x;
		this.pitch += pitchYaw.y;
	}
	
	public void addToYaw(float d) { this.yaw += d; }
	public void addToPitch(float d) { this.pitch += d; }
	public void addToRoll(float d) { this.roll += d; }

	public Vector3f getPosition() {
		return position;
	}public void setPosition(Vector3f position) {
		this.position = position;
	}public float getPitch() {
		return pitch;
	}public void setPitch(float pitch) {
		this.pitch = pitch;
	}public float getYaw() {
		return yaw;
	}public void setYaw(float yaw) {
		this.yaw = yaw;
	}public float getRoll() {
		return roll;
	}public void setRoll(float roll) {
		this.roll = roll;
	}
}
