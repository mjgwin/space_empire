package com.gdx.game.world;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Ship {
	
	private Sprite sprite;
	private String name;
	private Empire owner;
	private int type;
	private boolean selected;
	private float speed = 0.5f;
	private int targetMovementBuffer = 10;
	private boolean moving;
	private Vector2 currMove;
	
	public Ship(Sprite sprite, String name, int type, Empire owner) {
		this.sprite = sprite;
		this.name = name;
		this.type = type;
		this.owner = owner;
		currMove = null;
		selected = false;
		moving = false;
	}
	
	public void move(int x, int y) {
		currMove = new Vector2(x,y);
		moving = true;
	}
	
	public void updateMovement() {
		if (currMove == null) return;
		if(moving) {
			moveToVector(currMove);
			if(atTarget(currMove, targetMovementBuffer)) {
				moving = false;
			}
		}
		
		
	}
	
	private void moveToVector(Vector2 vec) {
		int currX = (int) (sprite.getX() + sprite.getWidth() / 2);
		int currY = (int) (sprite.getY() + sprite.getHeight() / 2);
		int targetX = (int)vec.x;
		int targetY = (int)vec.y;
		if(currX < targetX) sprite.translateX(speed);
		else sprite.translateX(-speed);
		
		if(currY < targetY) sprite.translateY(speed);
		else sprite.translateY(-speed);
		
	}
	
	private boolean atTarget(Vector2 target, int buffer) {
		Rectangle targetRect = new Rectangle(target.x, target.y, buffer, buffer);
		return sprite.getBoundingRectangle().overlaps(targetRect);
	}
	
	
	

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public Empire getOwner() {
		return owner;
	}

	public void setOwner(Empire owner) {
		this.owner = owner;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		Ship s = (Ship)obj;
		return this.name == s.name && this.type == s.type && this.getSprite().getX() == s.getSprite().getX() &&
				this.sprite.getY() == s.getSprite().getY();
	}
	
	

}
