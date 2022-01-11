package com.gdx.game.ui;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gdx.game.MainGameScreen;
import com.gdx.game.world.BuildingCost;
import com.gdx.game.world.ResourceManager;
import com.gdx.game.world.SurfaceBuilding;

public class PurchaseBuildingUI extends Stage{
	
	private MainGameScreen game;
	private ManagePlanetUI managePlanetUI;
	
	private SurfaceBuilding surfaceBuilding;
	
	private int screenWidth;
	private int screenHeight;
	
	private int boxWidth = 300;
	private int boxHeight = 200;
	
	private int border = 50;
	
	private Skin skin;
	
	private BuildingCost buildingCost;
	
	private BitmapFont font;
	
	private ResourceManager resourceManager;

	public PurchaseBuildingUI(MainGameScreen game, ManagePlanetUI managePlanetUI, SurfaceBuilding surfaceBuilding) {
		super(new ScreenViewport());
		this.game = game;
		this.managePlanetUI = managePlanetUI;
		this.surfaceBuilding = surfaceBuilding;
		buildingCost = surfaceBuilding.getBuildingCost(surfaceBuilding.getBuildingType());
		resourceManager = managePlanetUI.getResourceManager();
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		font = new BitmapFont();
		setupScreen();
	}
	
	private void setupScreen() {
		Group buttonGroup = new Group();
		Table buttonTable = new Table();
		Group labelGroup = new Group();
		Table labelTable = new Table();
		skin = new Skin(Gdx.files.internal("skins/neon/neon-ui.json"));
		
		TextButton purchase = new TextButton("Purchase", skin);
		TextButton exit = new TextButton("Exit", skin);
		
		Label titleLabel = new Label("Purchase Building", skin);
		
		exit.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				managePlanetUI.setPurchaseSelected(false);
				Gdx.input.setInputProcessor(managePlanetUI);
			}
		});
		
		purchase.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				boolean canPurchase = true;
				int index = 0;
				ArrayList<Integer> cost = buildingCost.getCostList();
				
				for(String resource : buildingCost.getResourceList()) {
					int costNum = cost.get(index);
					if(resourceManager.getResourceValue(resource) < costNum) {
						canPurchase = false;
					}
					index++;
				}
				
				index = 0;
				
				if(canPurchase) {
					for(String resource : buildingCost.getResourceList()) {
						int costNum = cost.get(index);
						resourceManager.changeResourceValue(resource, -costNum);
						index++;
					}
				}
				
				managePlanetUI.setSurfaceBuilding(surfaceBuilding);
				managePlanetUI.setPurchaseSelected(false);
				Gdx.input.setInputProcessor(managePlanetUI);
				
			}
		});
		
		buttonTable.add(purchase).pad(0, border * 4, 0, 0);
		buttonTable.add(exit).pad(0, border * 2, 0, 0);
		
		buttonGroup.addActor(buttonTable);
		
		buttonGroup.setPosition(screenWidth / 2 - boxWidth / 2 + border, screenHeight / 2 - boxHeight / 2 + border);
		
		labelTable.add(titleLabel).pad(0, border * 2.5f, border * 3, 0);
		
		labelGroup.addActor(labelTable);
		labelGroup.setPosition(screenWidth / 2 - boxWidth / 2 + border, screenHeight / 2 - boxHeight / 2 + border);
		labelGroup.setScale(1.5f);
		
		this.addActor(labelGroup);
		
		this.addActor(buttonGroup);
	}
	
	@Override
	public void draw() {
		this.getBatch().begin();
		drawBackgroundUI(this.getBatch());
		drawResourceCost(this.getBatch());
		this.getBatch().end();
		
		super.draw();
	}
	
	private void drawBackgroundUI(Batch batch) {
		batch.draw(UITextures.EMPTY_BOX, screenWidth / 2 - boxWidth / 2, screenHeight / 2 - boxHeight / 2, boxWidth, boxHeight);
	}
	
	private void drawResourceCost(Batch batch) {
		int index = 0;
		ArrayList<Integer> cost = buildingCost.getCostList();
		int startY = screenHeight / 2 - boxHeight / 2;
		
		for(String resource : buildingCost.getResourceList()) {
			int costNum = cost.get(index);
			String toDraw = resource + ": " + costNum;
			font.setColor(Color.FIREBRICK);
			font.draw(batch, toDraw, screenWidth / 2 - boxWidth / 2 + border * 2.5f, startY + border * 2);
			startY += 20;
			index++;
		}
		
	}
	
	
}
