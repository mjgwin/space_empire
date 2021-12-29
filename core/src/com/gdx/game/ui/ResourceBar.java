package com.gdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.gdx.game.MainGameScreen;
import com.gdx.game.world.Empire;
import com.gdx.game.world.ResourceManager;
import com.gdx.game.world.ResourceTextures;

public class ResourceBar extends Stage{
	
	private int screenWidth, screenHeight;
	private MainGameScreen game;
	private Empire mainPlayer;
	private ResourceManager resourceManager;
	private float barStart = 300;
	private float spacing = 100;
	private float imageSize = 25;
	private float barHeight = 30;
	
	private BitmapFont font;
	
	public ResourceBar(MainGameScreen game, Empire mainPlayer) {
		this.game = game;
		this.mainPlayer = mainPlayer;
		resourceManager = mainPlayer.getResourceManager();
		font = new BitmapFont();
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		
	}
	
	@Override
	public void draw() {
		this.getBatch().begin();
		drawBackgroundUI(this.getBatch());
		displayResourceVisuals(this.getBatch());
		drawResourceCount(this.getBatch());
		this.getBatch().end();
	}
	
	private void displayResourceVisuals(Batch batch) {
		float startPos = barStart;
		batch.draw(ResourceTextures.TEXTURE_CURRENCY, startPos, screenHeight - barHeight, imageSize, imageSize);
		startPos += spacing;
		batch.draw(ResourceTextures.TEXTURE_ENERGY, startPos, screenHeight - barHeight, imageSize, imageSize);
		startPos += spacing;
		batch.draw(ResourceTextures.TEXTURE_METAL, startPos, screenHeight - barHeight, imageSize, imageSize);
		startPos += spacing;
		batch.draw(ResourceTextures.TEXTURE_FOOD, startPos, screenHeight - barHeight, imageSize, imageSize);
		startPos += spacing;
		batch.draw(ResourceTextures.TEXTURE_PERSON, startPos, screenHeight - barHeight, imageSize, imageSize);
	}
	
	
	
	private void drawBackgroundUI(Batch batch) {
		int numResources = resourceManager.getNumResources();
		batch.draw(UITextures.STATUS_BAR, barStart - (spacing / 2), screenHeight - barHeight, numResources * (spacing + (imageSize / 2)), barHeight);
	}
	
	private void drawResourceCount(Batch batch) {
		font.setColor(Color.FIREBRICK);
		float startX = barStart;
		
		for(String resource : resourceManager.getResourceMap().keySet()) {
			float drawX = startX + (imageSize * 1.5f);
			float drawY = screenHeight - barHeight + (imageSize / 1.5f);
			String toDraw = Integer.toString(resourceManager.getResourceValue(resource));
			font.draw(batch, toDraw, drawX, drawY);
			startX += spacing;
		}
	}
	
	
	


}
