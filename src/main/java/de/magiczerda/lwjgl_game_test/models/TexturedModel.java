package de.magiczerda.lwjgl_game_test.models;

import de.magiczerda.lwjgl_game_test.textures.ModelTexture;

public class TexturedModel {
	
	private RawModel rawModel;
	private ModelTexture modelTexture;
	
	public TexturedModel(RawModel rawModel, ModelTexture modelTexture) {
		this.rawModel = rawModel;
		this.modelTexture = modelTexture;
	}

	public RawModel getRawModel() {
		return rawModel;
	}public ModelTexture getModelTexture() {
		return modelTexture;
	}
}
