package com.gdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class SurfaceTextures {
	
	private static String textureDir = "textures/surface/";
	
	public static Texture TEXTURE_GRASS;
	public static Texture TEXTURE_WATER;
	public static Texture TEXTURE_ROCK;
	public static Texture TEXTURE_METAL;
	public static Texture TEXTURE_CURRENCY;
	public static Texture TEXTURE_FOOD;
	
	public static void init() {
		TEXTURE_GRASS = new Texture(Gdx.files.internal(textureDir + "surface_grass.png"));
		TEXTURE_WATER = new Texture(Gdx.files.internal(textureDir + "surface_water.png"));
		TEXTURE_ROCK = new Texture(Gdx.files.internal(textureDir + "surface_rock.png"));
		TEXTURE_METAL = new Texture(Gdx.files.internal(textureDir + "surface_metal.png"));
		TEXTURE_CURRENCY = new Texture(Gdx.files.internal(textureDir + "surface_currency.png"));
		TEXTURE_FOOD = new Texture(Gdx.files.internal(textureDir + "surface_food.png"));
	}
	
	public static void dispose() {
		TEXTURE_GRASS.dispose();
		TEXTURE_WATER.dispose();
		TEXTURE_ROCK.dispose();
		TEXTURE_METAL.dispose();
		TEXTURE_CURRENCY.dispose();
		TEXTURE_FOOD.dispose();
	}
}
