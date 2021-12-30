package com.gdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
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
import com.gdx.game.world.Empire;
import com.gdx.game.world.Planet;

public class PlanetUI extends Stage {

	private Planet planet;
	private int screenWidth, screenHeight;
	private MainGameScreen game;
	private ManagePlanetUI managePlanetUI;
	private boolean managePlanetSelected;
	private float boxWidth = 250;
	private float boxHeight = 150;
	private float spacing = 5;

	public PlanetUI(Planet p, MainGameScreen game) {
		super(new ScreenViewport());
		managePlanetUI = new ManagePlanetUI(p, game, this);
		this.game = game;
		planet = p;
		managePlanetSelected = false;
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		setupScreen();
		Gdx.input.setInputProcessor(this);
	}

	private void setupScreen() {
		Table table = new Table();
		table.setFillParent(true);
		// table.setDebug(true);
		this.addActor(table);

		Skin skin = new Skin(Gdx.files.internal("skins/neon/neon-ui.json"));

		Label nameLabel = new Label("Planet name: " + planet.getName(), skin);
		String owner = "";
		if (planet.getOwner().isPlaceHolder()) {
			owner = "NO_OWNER_FOUND";
		}
			
		else {
			owner = planet.getOwner().getName();
		}
		Label ownerLabel = new Label("Owned by: " + owner, skin);
		TextButton manageOption = new TextButton("Manage Planet", skin);
		TextButton exit = new TextButton("Exit", skin);
		table.add(nameLabel).top().left().expandX().pad(spacing, spacing, 0, 0);
		table.row();
		table.add(ownerLabel).top().left().expandX().pad(spacing, spacing, 0, 0);
		table.row();
		table.add(manageOption).top().left().expandX();
		table.row();
		table.add(exit).top().left().expand();
		table.pack();

		exit.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.input.setInputProcessor(game);
				planet.setSelected(false);
			}
		});

		manageOption.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(planet.getOwner().isPlaceHolder()) {
					return;
				}
				
				managePlanetSelected = !managePlanetSelected;
				if (managePlanetSelected) {
					Gdx.input.setInputProcessor(managePlanetUI);
				}
			}
		});
	}

	public void hide() {
		Gdx.input.setInputProcessor(game);
		planet.setSelected(false);
	}

	@Override
	public void act() {
		super.act();
		if (managePlanetSelected) {
			managePlanetUI.act();
		}
	}

	@Override
	public void draw() {
		this.getBatch().begin();
		drawBackgroundUI(this.getBatch());
		this.getBatch().end();

		super.draw();
		if (managePlanetSelected) {
			managePlanetUI.draw();
		}

	}

	private void drawBackgroundUI(Batch batch) {
		batch.draw(UITextures.EMPTY_BOX, 0, screenHeight - boxHeight, boxWidth, boxHeight);
	}

	public void setManagePlanetSelected(boolean selected) {
		this.managePlanetSelected = selected;
	}
}
