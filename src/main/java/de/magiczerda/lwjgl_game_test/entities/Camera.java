package de.magiczerda.lwjgl_game_test.entities;

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
		
		if(moveForward) dPosition.z = -Options.MOVE_SPEED;
		if(moveBackward) dPosition.z = Options.MOVE_SPEED;
		if(moveRight) dPosition.x = Options.MOVE_SPEED;
		if(moveLeft) dPosition.x = -Options.MOVE_SPEED;
		if(ascent) dPosition.y = Options.MOVE_SPEED;
		if(decent) dPosition.y = -Options.MOVE_SPEED;
		
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
