package com.gdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gdx.game.MainGameScreen;
import com.gdx.game.world.Ship;

public class ShipUI extends Stage{
	
	private int screenWidth, screenHeight;
	private MainGameScreen game;
	private Ship ship;
	private float boxWidth = 250;
	private float boxHeight = 50;
	private float spacing = 10;

	
	public ShipUI(Ship s, MainGameScreen game) {
		super(new ScreenViewport());
		this.ship = s;
		this.game = game;
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		setupScreen();
	}
	
	private void setupScreen() {
		Table table = new Table();
		table.setFillParent(true);
		//table.setDebug(true);
		this.addActor(table);
		//Todo make global ui skin so it is not loaded from file every time UI is called
		Skin skin = new Skin(Gdx.files.internal("skins/neon/neon-ui.json"));
		
		Label nameLabel = new Label("Ship name: " + ship.getName(), skin);
		Label empireLabel = new Label("Owned by Empire: " + ship.getOwner().getName(), skin);
		table.add(nameLabel).top().left().expandX().pad(0, spacing, 0, 0);
		table.row();
		table.add(empireLabel).top().left().expand().pad(0, spacing, 0, 0);
		table.pack();
	}
	
	public void hide() {
		ship.setSelected(false);
	}
	
	@Override
	public void draw() {
		this.getBatch().begin();
		drawBackgroundUI(this.getBatch());
		this.getBatch().end();
		
		super.draw();
	}
	
	private void drawBackgroundUI(Batch batch) {
		batch.draw(UITextures.EMPTY_BOX, 0, screenHeight - boxHeight, boxWidth, boxHeight);
	}

}
