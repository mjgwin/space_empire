package com.gdx.game.world;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Planet {
	
	public static int DEFAULT_SIZE = 100;
	public static int DEFAULT_SUN_SIZE = 200;
	public static int TYPE_PLANET = 0;
	public static int TYPE_SUN = 1;
	public static int TYPE_MOON = 2;
	
	private final int MIN_PLANET_SIZE = 70;
	private final int MAX_PLANET_SIZE = 150;
	private final int NUM_TEXTURES = 3;
	
	private Sprite sprite;
	private String name;
	private int type;
	private boolean selected;
	private Empire owner;
	
	private final int MIN_SURFACE_SIZE = 5;
	private final int MAX_SURFACE_SIZE = 10;
	private Random rand;
	
	private int surfaceWidth, surfaceHeight;
	private SurfaceCell[][] surfaceCells;
	
	public Planet(Sprite sprite, String name, int type) {
		this.sprite = sprite;
		this.name = name;
		this.type = type;
		rand = new Random();
		generateSurface();
		selected = false;
	}
	
	public Planet(Vector2 spawnPos, String name, int type) {
		this.name = name;
		this.type = type;
		rand = new Random();
		int size = DEFAULT_SIZE;
		Texture tex = WorldTextures.DULL_PLANET_TEX;
		int pickTexture = rand.nextInt(NUM_TEXTURES);
		
		if(type == TYPE_PLANET) {
			size = rand.nextInt(MAX_PLANET_SIZE - MIN_PLANET_SIZE) + MIN_PLANET_SIZE;
			tex = findPlanetTexture(pickTexture);
		}else if(type == TYPE_SUN) {
			size = DEFAULT_SUN_SIZE;
			int pickSun = rand.nextInt(2);
			tex = findSunTexture(pickSun);
		}else if(type == TYPE_MOON) {
			size = MIN_PLANET_SIZE;
			tex = findPlanetTexture(pickTexture);
		}
		
		this.sprite = new Sprite(tex);
		this.sprite.setBounds(spawnPos.x, spawnPos.y, size, size);
		selected = false;
		generateSurface();
		
	}
	
	private Texture findSunTexture(int texID) {
		Texture tex = WorldTextures.SUN_TEX;
		
		switch(texID) {
		case 0:
			break;
		case 1:
			tex = WorldTextures.BLUE_SUN_TEX;
			break;
		}
		
		return tex;
	}
	
	private Texture findPlanetTexture(int texID) {
		Texture tex = WorldTextures.DULL_PLANET_TEX;
		
		switch(texID) {
		case 0:
			break;
		case 1:
			tex = WorldTextures.BLUE_PLANET_TEX;
			break;
		case 2:
			tex = WorldTextures.PURPLE_PLANET_TEX;
			break;
		}
		
		return tex;
	}
	
	private void generateSurface() {
		surfaceWidth = rand.nextInt(MAX_SURFACE_SIZE - MIN_SURFACE_SIZE) + MIN_SURFACE_SIZE;
		surfaceHeight = rand.nextInt(MAX_SURFACE_SIZE - MIN_SURFACE_SIZE) + MIN_SURFACE_SIZE;
		surfaceCells = new SurfaceCell[surfaceWidth][surfaceHeight];
		fillCells();
	}
	
	private void fillCells() {
		for(int i = 0; i < surfaceWidth; i++) {
			for(int j = 0; j < surfaceHeight; j++) {
				int type = rand.nextInt(SurfaceCell.numTypes);
				surfaceCells[i][j] = new SurfaceCell(i, j, SurfaceCell.DEFAULT_CELL_SIZE, SurfaceCell.getTypeFromInt(type));
			}
		}
		
	}
	
	public void printSurface() {
		for(int i = 0; i < surfaceWidth; i++) {
			for(int j = 0; j < surfaceHeight; j++) {
				System.out.print(surfaceCells[i][j].toString() + " : ");
			}
			System.out.println();
		}
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	

	public Empire getOwner() {
		return owner;
	}

	public void setOwner(Empire owner) {
		this.owner = owner;
	}

	public int getSurfaceWidth() {
		return surfaceWidth;
	}

	public void setSurfaceWidth(int surfaceWidth) {
		this.surfaceWidth = surfaceWidth;
	}

	public int getSurfaceHeight() {
		return surfaceHeight;
	}

	public void setSurfaceHeight(int surfaceHeight) {
		this.surfaceHeight = surfaceHeight;
	}

	public SurfaceCell[][] getSurfaceCells() {
		return surfaceCells;
	}

	public void setSurfaceCells(SurfaceCell[][] surfaceCells) {
		this.surfaceCells = surfaceCells;
	}
	
	public Rectangle getBounds() {
		return this.getSprite().getBoundingRectangle();
	}


	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		Planet p = (Planet)obj;
		return this.name == p.name && this.getSprite().getX() == p.getSprite().getX() 
				&& this.getSprite().getY() == p.getSprite().getY();
	}
	
	
	

}
