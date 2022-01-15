package com.gdx.game.world;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Ship {
	
	public enum ShipType {
		FIGHTER,
		COLONY,
		CONSTRUCTION
	}
	
	public static final float DEFAULT_SIZE = 50;
	
	private Sprite sprite;
	private String name;
	private Empire owner;
	private ShipType type;
	private boolean selected;
	private float speed = 2f;
	private int targetMovementBuffer = 10;
	private boolean moving;
	private Vector2 currMove;
	private Vector2 spawnPos;
	
	public Ship(Vector2 spawnPos, ShipType type, Empire owner, String name) {
		this.type = type;
		this.owner = owner;
		this.spawnPos = spawnPos;
		this.name = name;
		init();
		currMove = null;
		selected = false;
		moving = false;
	}
	
	private void init() {
		switch(type) {
		case FIGHTER:
			this.sprite = new Sprite(WorldTextures.SHIP_TEX_FIGHTER);
			this.sprite.setBounds(spawnPos.x, spawnPos.y, DEFAULT_SIZE, DEFAULT_SIZE);
			break;
		case COLONY:
			break;
		case CONSTRUCTION:
			break;
		}
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
		
		float currX = sprite.getX() + sprite.getWidth() / 2f;
		float currY = sprite.getY() + sprite.getHeight() / 2f;
		float targetX = vec.x;
		float targetY = vec.y;
		
		float radAngle = (float)Math.atan2(targetY - currY, targetX - currX);
		
		float newX = (float) Math.cos(radAngle) * speed;
		float newY = (float) Math.sin(radAngle) * speed;
		
		sprite.translateX(newX);
		sprite.translateY(newY);
		
		sprite.setOrigin(sprite.getWidth() / 2f, sprite.getHeight() / 2f);
		
		float angle = new Vector2(targetY - currY, currX - targetX).angleDeg();
		
		sprite.setRotation(angle);
		
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

	public ShipType getType() {
		return type;
	}

	public void setType(ShipType type) {
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
