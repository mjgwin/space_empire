package com.gdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class UITextures {
	
	private static String resourcePath = "textures/ui/";
	
	public static Texture STATUS_BAR;
	public static Texture EMPTY_BOX;
	public static Texture LOADING_BAR_OUTLINE;
	public static Texture LOADING_BAR;
	
	public static void init() {
		STATUS_BAR = new Texture(Gdx.files.internal(resourcePath + "status_bar.jpg"));
		EMPTY_BOX = new Texture(Gdx.files.internal(resourcePath + "empty_box.jpg"));
		LOADING_BAR_OUTLINE = new Texture(Gdx.files.internal(resourcePath + "loading_bar_outline.jpg"));
		LOADING_BAR = new Texture(Gdx.files.internal(resourcePath + "loading_bar.jpg"));
	}
	
	public static void dispose() {
		STATUS_BAR.dispose();
		EMPTY_BOX.dispose();
		LOADING_BAR_OUTLINE.dispose();
		LOADING_BAR.dispose();
	}
}
