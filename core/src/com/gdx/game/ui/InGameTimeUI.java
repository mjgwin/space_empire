package com.gdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;
import com.gdx.game.MainGameScreen;

public class InGameTimeUI extends Stage{
	
	private MainGameScreen game;
	private int screenWidth, screenHeight;
	private float timeBoxWidth = 200;
	private float timeBoxHeight = 30;
	private float spacing = 10;
	private int days, months, years;
	private BitmapFont font;
	private long startTime, currTime;
	
	public InGameTimeUI(MainGameScreen game) {
		this.game = game;
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		days = 0;
		months = 0;
		years = 0;
		font = new BitmapFont();
		startTime = System.currentTimeMillis();
	}
	
	@Override
	public void draw() {
		this.getBatch().begin();
		drawBackgroundUI(this.getBatch());
		drawTime(this.getBatch());
		this.getBatch().end();
	}
	
	@Override
	public void act() {
		//super.act();
		updateTime();
	}
	
	private void drawBackgroundUI(Batch batch) {
		batch.draw(UITextures.STATUS_BAR, screenWidth - timeBoxWidth, screenHeight - timeBoxHeight, timeBoxWidth, timeBoxHeight);
	}
	
	private void drawTime(Batch batch) {
		StringBuilder sb = new StringBuilder();
		sb.append("Year: ").append(years).append("  ")
		.append("Month: ").append(months).append("  ")
		.append("Day: ").append(days);
		font.setColor(Color.FIREBRICK);
		float drawX = screenWidth - timeBoxWidth + spacing;
		float drawY = screenHeight - (timeBoxHeight / 2) + (spacing / 2);
		font.draw(batch, sb.toString(), drawX, drawY);
	}
	
	private void updateTime() {
		currTime = System.currentTimeMillis();
		long elapsedTime = currTime - startTime;
		
		if(elapsedTime > 500) {
			days++;
			startTime = System.currentTimeMillis();
		}
		
		if(days == 31) {
			months++;
			days = 0;
		}
		
		if(months == 13) {
			years++;
			months = 0;
		}
	}
	
	public int getMonths() {
		return months;
	}
	
	
	
}
