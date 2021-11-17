package com.gdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gdx.game.MainGameScreen;
import com.gdx.game.world.Planet;
import com.gdx.game.world.SurfaceCell;
import com.gdx.game.world.SurfaceCell.CellType;
import com.gdx.game.world.SurfaceTextures;

public class ManagePlanetUI extends Stage {

	private Planet planet;
	private PlanetUI planetUI;
	private int screenWidth, screenHeight;
	private MainGameScreen game;
	private ShapeRenderer shapeRender;
	private BitmapFont font;

	private SurfaceCell selected;

	private int border = 200;
	private int edgeSpacing = 30;
	
	private Label selectedLabel;
	//Update when new cell clicked
	
	public ManagePlanetUI(Planet p, MainGameScreen game, PlanetUI planetUI) { 
		super(new ScreenViewport());
		shapeRender = new ShapeRenderer();
		font = new BitmapFont();
		this.game = game;
		this.planetUI = planetUI;
		planet = p;
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		setupScreen();
		// Gdx.input.setInputProcessor(this);
	}

	private void setupScreen() {
		Group group = new Group();
		Group exitGroup = new Group();
		Table table = new Table();
		Table exitTable = new Table();
		table.setFillParent(true);
		// table.setDebug(true);

		Skin skin = new Skin(Gdx.files.internal("skins/neon/neon-ui.json"));

		Label screenTitle = new Label("Manage Planet", skin);
		TextButton exit = new TextButton("Exit", skin);

		exit.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.input.setInputProcessor(planetUI);
				planetUI.setManagePlanetSelected(false);
			}
		});

		table.add(screenTitle).top();

		group.addActor(table);
		group.setScale(1.5f);
		group.setPosition(screenWidth / 2, screenHeight - 175);

		exitTable.add(exit).pad(150, 0, 0, 100);
		exitGroup.setPosition(screenWidth - border, screenHeight - 500);
		exitGroup.addActor(exitTable);

		this.addActor(exitGroup);

		this.addActor(group);

	}

	@Override
	public void draw() {
		this.getBatch().begin();
		drawBackgroundUI(this.getBatch());
		drawSurface(this.getBatch());
		drawCellInformation(this.getBatch());
		this.getBatch().end();

		super.draw();

		
		//drawSurfaceDebug();
	}

	private void drawBackgroundUI(Batch batch) {
		batch.draw(UITextures.EMPTY_BOX, border, border / 3, screenWidth - border * 2, screenHeight - border);
	}

	private void drawCellInformation(Batch batch) {
		if (selected != null) {
			font.setColor(Color.FIREBRICK);
			String draw = "Selected Cell: " + selected.getCurrType().name();
			font.draw(batch, draw, screenWidth - border, screenHeight - 300);			
		}
	}

	private void drawSurfaceDebug() {
		int startX = border + edgeSpacing * 2;
		int startY = (border / 2) + edgeSpacing;

		for (int i = 0; i < planet.getSurfaceWidth(); i++) {
			for (int j = 0; j < planet.getSurfaceHeight(); j++) {
				int offsetX = i * SurfaceCell.DEFAULT_CELL_SIZE;
				int offsetY = j * SurfaceCell.DEFAULT_CELL_SIZE;
				shapeRender.begin(ShapeType.Line);
				shapeRender.setColor(Color.RED);
				shapeRender.rect(startX + offsetX, startY + offsetY, SurfaceCell.DEFAULT_CELL_SIZE,
						SurfaceCell.DEFAULT_CELL_SIZE);
				shapeRender.end();
			}
		}
	}

	private void drawSurface(Batch batch) {
		int startX = border + edgeSpacing * 2;
		int startY = (border / 2) + edgeSpacing;
		SurfaceCell[][] surface = planet.getSurfaceCells();

		for (int i = 0; i < planet.getSurfaceWidth(); i++) {
			for (int j = 0; j < planet.getSurfaceHeight(); j++) {
				int offsetX = i * SurfaceCell.DEFAULT_CELL_SIZE;
				int offsetY = j * SurfaceCell.DEFAULT_CELL_SIZE;

				Texture toDraw = SurfaceTextures.TEXTURE_GRASS;
				CellType type = surface[i][j].getCurrType();
				switch (type) {
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

				batch.draw(toDraw, startX + offsetX, startY + offsetY, SurfaceCell.DEFAULT_CELL_SIZE,
						SurfaceCell.DEFAULT_CELL_SIZE);
			}
		}
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		super.touchDown(screenX, screenY, pointer, button);
		SurfaceCell[][] surface = planet.getSurfaceCells();
		int startX = border + edgeSpacing * 2;
		int startY = (border / 2) + edgeSpacing;
		
		Vector3 unProj = new Vector3();
		this.getCamera().unproject(unProj.set(screenX, screenY, 0));
		
		for (int i = 0; i < planet.getSurfaceWidth(); i++) {
			for (int j = 0; j < planet.getSurfaceHeight(); j++) {
				SurfaceCell currCell = surface[i][j];
				// need to reset value not incrementing every cell pos
				int offsetX = i * SurfaceCell.DEFAULT_CELL_SIZE;
				int offsetY = j * SurfaceCell.DEFAULT_CELL_SIZE;
				Rectangle cellBounds = new Rectangle(startX + offsetX, startY + offsetY, SurfaceCell.DEFAULT_CELL_SIZE,
						SurfaceCell.DEFAULT_CELL_SIZE);
				if (cellBounds.contains(unProj.x, unProj.y)) {
					selected = currCell;
					return true;
				}

			}
		}
		return true;
	}

}
