package com.gdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gdx.game.MainGameScreen;
import com.gdx.game.world.Planet;
import com.gdx.game.world.SurfaceCell;
import com.gdx.game.world.SurfaceCell.CellType;
import com.gdx.game.world.SurfaceTextures;

public class ManagePlanetUI extends Stage{

	private Planet planet;
	private int screenWidth, screenHeight;
	private MainGameScreen game;
	private ShapeRenderer shapeRender;
	
	private int border = 300;
	
	public ManagePlanetUI(Planet p, MainGameScreen game) {
		super(new ScreenViewport());
		shapeRender = new ShapeRenderer();
		this.game = game;
		planet = p;
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		setupScreen();
		Gdx.input.setInputProcessor(this);
	}
	
	private void setupScreen() {
		Group group = new Group();
		Table table = new Table();
		table.setFillParent(true);
		//table.setDebug(true);

		Skin skin = new Skin(Gdx.files.internal("skins/neon/neon-ui.json"));
		
		Label screenTitle = new Label("Manage Planet", skin);

		table.add(screenTitle).top();
		
		group.addActor(table);
		group.setScale(2f);
		group.setPosition(screenWidth / 2, screenHeight - 200);
		
		this.addActor(group);
		
	}
	
	@Override
	public void draw() {
		drawBackground();
		super.draw();
		this.getBatch().begin();
		drawSurface(this.getBatch());
		this.getBatch().end();
	}
	
	private void drawBackground() {
		shapeRender.begin(ShapeType.Filled);
		shapeRender.setColor(Color.DARK_GRAY);
		shapeRender.rect(border, border / 2, screenWidth - border * 2, screenHeight - border);
		shapeRender.end();
	}
	
	private void drawSurface(Batch batch) {
		int startX = border;
		int startY = (border / 2);
		
		for(int i = 0; i < planet.getSurfaceWidth(); i++) {
			for(int j = 0; j < planet.getSurfaceHeight(); j++) {
				int offsetX = i * SurfaceCell.DEFAULT_CELL_SIZE;
				int offsetY = j * SurfaceCell.DEFAULT_CELL_SIZE;
				
				Texture toDraw = SurfaceTextures.TEXTURE_GRASS;
				CellType type = planet.getSurfaceCells()[i][j].getCurrType();
				switch(type) {
				case GRASS:
					toDraw = SurfaceTextures.TEXTURE_GRASS;
					break;
				case WATER:
					toDraw = SurfaceTextures.TEXTURE_WATER;
					break;
				case ROCK:
					toDraw = SurfaceTextures.TEXTURE_ROCK;
					break;
				case METAL:
					toDraw = SurfaceTextures.TEXTURE_METAL;
					break;
				case CURRENCY:
					toDraw = SurfaceTextures.TEXTURE_CURRENCY;
					break;
				case FOOD:
					toDraw = SurfaceTextures.TEXTURE_FOOD;
					break;
				}
				
				batch.draw(toDraw, startX + offsetX, startY + offsetY, SurfaceCell.DEFAULT_CELL_SIZE, SurfaceCell.DEFAULT_CELL_SIZE);
			}
		}
	}
	
}
