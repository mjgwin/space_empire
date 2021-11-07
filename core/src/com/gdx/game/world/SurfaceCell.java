package com.gdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class SurfaceCell {
	
	public enum CellType{
		GRASS,
		WATER,
		ROCK,
		METAL,
		CURRENCY,
		FOOD
	};
	
	public static final int DEFAULT_CELL_SIZE = 20;
	
	public static int numTypes = 6;
	
	private CellType currType;
	private int x, y, size;
	
	public SurfaceCell(int x, int y, int size, CellType currType) {
		this.x = x;
		this.y = y;
		this.size = size;
		this.currType = currType;
	}

	public CellType getCurrType() {
		return currType;
	}

	public void setCurrType(CellType currType) {
		this.currType = currType;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public static CellType getTypeFromInt(int num) {
		CellType toReturn = null;
		
		switch(num) {
		case 0:
			toReturn =  CellType.GRASS;
			break;
		case 1:
			toReturn = CellType.WATER;
			break;
		case 2:
			toReturn = CellType.ROCK;
			break;
		case 3:
			toReturn = CellType.METAL;
			break;
		case 4:
			toReturn = CellType.CURRENCY;
			break;
		case 5:
			toReturn = CellType.FOOD;
			break;
		}
		
		return toReturn;
	}
	
	@Override
	public String toString() {
		return "SurfaceCell {type = " + this.currType.name() + ", x = " + this.x +
				", y = " + this.y + ", size = " + this.size + "}";
	}
	
	
	
	

}
