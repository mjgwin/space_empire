package com.gdx.game.ui;

import java.util.ArrayList;

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
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gdx.game.MainGameScreen;
import com.gdx.game.world.Empire;
import com.gdx.game.world.Planet;
import com.gdx.game.world.ResourceManager;
import com.gdx.game.world.SurfaceBuilding;
import com.gdx.game.world.SurfaceBuilding.BuildingType;
import com.gdx.game.world.SurfaceCell;
import com.gdx.game.world.SurfaceCell.CellType;
import com.gdx.game.world.SurfaceTextures;

public class ManagePlanetUI extends Stage {

	private Planet planet;
	private Empire owner;
	private ResourceManager resourceManager;
	private PlanetUI planetUI;
	private int screenWidth, screenHeight;
	private MainGameScreen game;
	private ShapeRenderer shapeRender;
	private BitmapFont font;

	private SurfaceCell selected;

	private int border = 200;
	private int edgeSpacing = 30;
	
	private Label selectedLabel;
	private Label selectedBuildingLabel;
	//Update when new cell clicked
	private ScrollPane buildingPane;
	
	private TextButton housing, alloyMine, farm, turbine, currencyMine, stoneExtractor;
	
	private Skin skin;
	
	
	public ManagePlanetUI(Planet p, MainGameScreen game, PlanetUI planetUI) { 
		super(new ScreenViewport());
		shapeRender = new ShapeRenderer();
		font = new BitmapFont();
		this.game = game;
		this.planetUI = planetUI;
		planet = p;
		owner = planet.getOwner();
		if(!owner.isPlaceHolder()) {
			resourceManager = owner.getResourceManager();
		}
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
		Table buildingOptions = new Table();
		table.setFillParent(true);
		// table.setDebug(true);

		skin = new Skin(Gdx.files.internal("skins/neon/neon-ui.json"));

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
		
		selectedLabel = new Label("Selected Cell: none", skin);
		selectedBuildingLabel = new Label("Building On Cell: none", skin);
		selectedLabel.setBounds(screenWidth - border * 3, screenHeight - 300, 200, 100);
		selectedBuildingLabel.setBounds(screenWidth - border * 3, screenHeight - 325, 200, 100);
		TextButton build = new TextButton("Build Structure", skin);
		build.setBounds(screenWidth - border * 3, screenHeight - 350, 150, 50);
		
		housing = new TextButton("Housing", skin);
		alloyMine = new TextButton("AlloyMine", skin);
		currencyMine = new TextButton("CurrencyMine", skin);
		farm = new TextButton("Farm", skin);
		turbine = new TextButton("Turbine", skin);
		stoneExtractor = new TextButton("StoneExtractor", skin);
		
		housing.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(selected.getSurfaceBuilding() == null) {
					selected.setSurfaceBuilding(new SurfaceBuilding(BuildingType.HOUSING));
				}
			}
		});
		
		alloyMine.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(selected.getSurfaceBuilding() == null) {
					selected.setSurfaceBuilding(new SurfaceBuilding(BuildingType.ALLOYMINE));
				}
			}
		});
		
		currencyMine.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(selected.getSurfaceBuilding() == null) {
					selected.setSurfaceBuilding(new SurfaceBuilding(BuildingType.CURRENCYMINE));
				}
			}
		});
		
		farm.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(selected.getSurfaceBuilding() == null) {
					selected.setSurfaceBuilding(new SurfaceBuilding(BuildingType.FARM));
				}
			}
		});
		
		turbine.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(selected.getSurfaceBuilding() == null) {
					selected.setSurfaceBuilding(new SurfaceBuilding(BuildingType.TURBINE));
				}
			}
		});
		
		stoneExtractor.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(selected.getSurfaceBuilding() == null) {
					selected.setSurfaceBuilding(new SurfaceBuilding(BuildingType.STONEEXTRACTOR));
				}
			}
		});
		
		build.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(selected == null) return;
				if(buildingPane.isVisible()) {
					buildingPane.setVisible(false);
					return;
				}
				Table buildingList = new Table();
				if(selected.getCurrType() == CellType.GRASS) {
					buildingList.add(housing);
					buildingList.row();
				}
				if(selected.getCurrType() == CellType.METAL) {
					buildingList.add(alloyMine);
					buildingList.row();
				}
				if(selected.getCurrType() == CellType.FOOD) {
					buildingList.add(farm);
					buildingList.row();
				}
				if(selected.getCurrType() == CellType.WATER) {
					buildingList.add(turbine);
					buildingList.row();
				}
				if(selected.getCurrType() == CellType.CURRENCY) {
					buildingList.add(currencyMine);
					buildingList.row();
				}
				if(selected.getCurrType() == CellType.ROCK) {
					buildingList.add(stoneExtractor);
					buildingList.row();
				}
				buildingPane = new ScrollPane(buildingList, skin);
				buildingPane.setPosition(screenWidth - border * 2f, screenHeight - 450);
				buildingPane.setSize(150, 150);
				buildingPane.setVisible(false);
				buildingPane.setFadeScrollBars(false);
				addActor(buildingPane);
				buildingPane.setVisible(true);
			}
		});
		
		//buildingOptions.add(new TextButton("Housing", skin));
		//buildingOptions.row();
		//buildingOptions.add(new TextButton("Mine", skin));
		//buildingOptions.row();
		//buildingOptions.add(new TextButton("Harvester", skin));
		//buildingOptions.row();
		//buildingOptions.add(new TextButton("Turbine", skin));
		
		buildingPane = new ScrollPane(buildingOptions, skin);
		//buildingPane.setPosition(screenWidth - border * 2f, screenHeight - 450);
		//buildingPane.setSize(150, 150);
		buildingPane.setVisible(false);
		//buildingPane.setFadeScrollBars(false);
		
		this.addActor(buildingPane);
		
		this.addActor(selectedLabel);
		this.addActor(selectedBuildingLabel);
		this.addActor(build);
		
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

		drawSelectedOutline();
		//drawSurfaceDebug();
	}

	private void drawBackgroundUI(Batch batch) {
		batch.draw(UITextures.EMPTY_BOX, border, border / 3, screenWidth - border * 2, screenHeight - border);
	}

	private void drawCellInformation(Batch batch) {
		if (selected != null) {
			String selectDraw = "Selected Cell: " + selected.getCurrType().name();
			String buildingDraw = "Building On Cell: ";
			if(selected.getSurfaceBuilding() == null) {
				buildingDraw += "NONE";
			}else {
				buildingDraw += selected.getSurfaceBuilding().getBuildingType().name();
			}
			
			selectedLabel.setText(selectDraw);
			selectedBuildingLabel.setText(buildingDraw);
		}
	}
	
	private void drawSelectedOutline() {
		if(selected != null) {
			
			int startX = border + edgeSpacing * 2;
			int startY = (border / 2) + edgeSpacing;
			int offsetX = selected.getX() * SurfaceCell.DEFAULT_CELL_SIZE;
			int offsetY = selected.getY() * SurfaceCell.DEFAULT_CELL_SIZE;
			
			shapeRender.begin(ShapeType.Line);
			shapeRender.setColor(Color.RED);
			shapeRender.rect(startX + offsetX, startY + offsetY, SurfaceCell.DEFAULT_CELL_SIZE,
					SurfaceCell.DEFAULT_CELL_SIZE);
			shapeRender.end();
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
		int factoryBuffer = SurfaceCell.DEFAULT_CELL_SIZE / 4;
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
				
				if(surface[i][j].getSurfaceBuilding() != null) {
					batch.draw(SurfaceTextures.TEXTURE_FACTORY, startX + offsetX + factoryBuffer, 
							startY + offsetY + factoryBuffer,
							SurfaceCell.DEFAULT_CELL_SIZE / 2, SurfaceCell.DEFAULT_CELL_SIZE / 2);
				}
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
					if(buildingPane.isVisible()) {
						buildingPane.setVisible(false);
					}
					return true;
				}

			}
		}
		return true;
	}

}
