package com.gdx.game.world;

import com.badlogic.gdx.math.Vector2;

public class Star {
	
	public enum StarType {
		WHITE_STAR
	}
	
	public static int DEFAULT_SIZE = 20;
	
	private StarType type;
	private Vector2 pos;
	
	
	public Star(Vector2 spawnPos, StarType type) {
		this.pos = spawnPos;
		this.type = type;
	}


	public float getX() {
		return pos.x;
	}


	public void setX(float x) {
		pos.x = x;
	}


	public float getY() {
		return pos.y;
	}


	public void setY(float y) {
		pos.y = y;
	}


	public StarType getType() {
		return type;
	}


	public void setType(StarType type) {
		this.type = type;
	}

	
	
	

	


}
