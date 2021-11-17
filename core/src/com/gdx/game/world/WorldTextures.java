package com.gdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class WorldTextures {
	
	public static Texture STAR_TEX;
	public static Texture DULL_PLANET_TEX;
	public static Texture BLUE_PLANET_TEX;
	public static Texture PURPLE_PLANET_TEX;
	public static Texture SUN_TEX;
	public static Texture BLUE_SUN_TEX;
	public static Texture SHIP_TEX;
	public static Texture STARTING_SYMBOL_TEX;
	
	private static String textureDir = "textures/space/";
	
	public static void init() {
		STAR_TEX = new Texture(Gdx.files.internal(textureDir + "star.png"));
		DULL_PLANET_TEX = new Texture(Gdx.files.internal(textureDir + "dull_planet.png"));
		BLUE_PLANET_TEX = new Texture(Gdx.files.internal(textureDir + "blue_planet.png"));
		PURPLE_PLANET_TEX = new Texture(Gdx.files.internal(textureDir + "purple_planet.png"));
		SUN_TEX = new Texture(Gdx.files.internal(textureDir + "sun.png"));
		BLUE_SUN_TEX = new Texture(Gdx.files.internal(textureDir + "blue_sun.png"));
		SHIP_TEX = new Texture(Gdx.files.internal(textureDir + "ship.png"));
		STARTING_SYMBOL_TEX = new Texture(Gdx.files.internal(textureDir + "gold_star.png"));
	}
	
	public static void dispose() {
		STAR_TEX.dispose();
		DULL_PLANET_TEX.dispose();
		BLUE_PLANET_TEX.dispose();
		PURPLE_PLANET_TEX.dispose();
		SUN_TEX.dispose();
		SHIP_TEX.dispose();
		STARTING_SYMBOL_TEX.dispose();
	}
	
	
}
