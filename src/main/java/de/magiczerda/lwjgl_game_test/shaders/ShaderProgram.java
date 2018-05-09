package de.magiczerda.lwjgl_game_test.shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public abstract class ShaderProgram {
	
	private int programID;
	private int vertexShaderID;
	private int fragmentShaderID;
	
	private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);	//16 floats because we use this to pass 4*4 matrices to the shader
	
	public ShaderProgram(String vertexFile, String fragmentFile) {
		vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
		fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
		programID = GL20.glCreateProgram();	//ties vertex shader and fragment shader together
		GL20.glAttachShader(programID, vertexShaderID);	//attach the vertex shader to the shader program
		GL20.glAttachShader(programID, fragmentShaderID);	//attach the fragment shader to the shader program
		bindAttributes();
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID);
		getAllUniformLocations();
	}
	
	/**
	 * Starts the shader program
	 */
	public void start() {
		GL20.glUseProgram(programID);
	}
	
	/**
	 * Stops the shader program
	 */
	public void stop() {
		GL20.glUseProgram(0);
	}
	
	/**
	 * Stops and deletes the shaders
	 */
	public void cleanUp() {
		stop();	//stop the shaders
		GL20.glDetachShader(programID, vertexShaderID);	//detach the vertex shader from the shader program
		GL20.glDetachShader(programID, fragmentShaderID);	//detach the fragment shader from the shader program
		GL20.glDeleteProgram(vertexShaderID);	//delete the vertex shader
		GL20.glDeleteShader(fragmentShaderID);	//delete the fragment shader
		GL20.glDeleteProgram(programID);		//delete the shader program
	}
	
	protected void bindAttribute(int attribute, String variableName) {
		GL20.glBindAttribLocation(programID, attribute, variableName);
	}
	
	protected abstract void bindAttributes();
	protected abstract void getAllUniformLocations();
	
	
	//------------load values to the shader----------------
	protected void loadFloat(int location, float value) {
		GL20.glUniform1f(location, value);
	}
	
	protected void loadVector(int location, Vector3f vector) {
		GL20.glUniform3f(location, vector.x, vector.y, vector.z);
	}
	
	protected void loadBoolean(int location, boolean value) {
		GL20.glUniform1f(location, (value ? 1 : 0));
	}
	
	protected void loadMatrix(int location, Matrix4f matrix) {
		matrix.get(matrixBuffer);
		GL20.glUniformMatrix4fv(location, false, matrixBuffer);
	}
	
	//--------------------------------------------------
	
	protected int getUniformLocation(String uniformName) {
		return GL20.glGetUniformLocation(programID, uniformName);
	}
	
	private static int loadShader(String file, int type) {
		StringBuilder shaderSource = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				shaderSource.append(line).append("//\n");
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		int shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shaderSource);
		GL20.glCompileShader(shaderID);
		if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
			System.err.println("Could not compile shader!");
			System.exit(-1);
		}
		return shaderID;
	}

}
