package com.gdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class ResourceTextures {
	
	private static String textureDir = "textures/resources/";
	
	public static Texture TEXTURE_ENERGY;
	public static Texture TEXTURE_METAL;
	public static Texture TEXTURE_CURRENCY;
	public static Texture TEXTURE_FOOD;
	
	public static void init() {
		TEXTURE_ENERGY = new Texture(Gdx.files.internal(textureDir + "energy.png"));
		TEXTURE_METAL = new Texture(Gdx.files.internal(textureDir + "metal.png"));
		TEXTURE_CURRENCY = new Texture(Gdx.files.internal(textureDir + "currency.png"));
		TEXTURE_FOOD = new Texture(Gdx.files.internal(textureDir + "food.png"));
	}
	
	public static void dispose() {
		TEXTURE_ENERGY.dispose();
		TEXTURE_METAL.dispose();
		TEXTURE_CURRENCY.dispose();
		TEXTURE_FOOD.dispose();
	}
	
}
