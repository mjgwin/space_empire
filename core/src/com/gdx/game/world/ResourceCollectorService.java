package com.gdx.game.world;

import com.gdx.game.MainGameScreen;
import com.gdx.game.ui.InGameTimeUI;

public class ResourceCollectorService {
	
	private MainGameScreen game;
	private InGameTimeUI inGameTimeUI;
	private World world;
	private int currMonth = 0;

	public ResourceCollectorService(MainGameScreen game) {
		this.inGameTimeUI = game.getInGameTimeUI();
		this.world = game.getWorld();
	}
	
	public void update() {
		int newMonth = inGameTimeUI.getMonths();
		if(newMonth <= currMonth) return;
		
		for(Planet p : world.getPlanets()) {
			if(p.getOwner() == null) continue;
			
			Empire currEmpire = p.getOwner();
			ResourceManager currManager = currEmpire.getResourceManager();
			SurfaceCell[][] surface = p.getSurfaceCells();
			
			for (int i = 0; i < p.getSurfaceWidth(); i++) {
				for (int j = 0; j < p.getSurfaceHeight(); j++) {
					SurfaceCell currCell = surface[i][j];
					if(surface[i][j].getSurfaceBuilding() == null) continue;
					processCollection(currCell, currManager);
				}
			}
			
		}
		
		currMonth = newMonth;
	}
	
	private void processCollection(SurfaceCell cell, ResourceManager manager) {
		SurfaceBuilding building = cell.getSurfaceBuilding();
		switch(building.getBuildingType()) {
			case HOUSING:
				break;
			case ALLOYMINE:
				manager.changeResourceValue("Metal", building.getResourcePerTurn());
				break;
			case CURRENCYMINE:
				manager.changeResourceValue("Currency", building.getResourcePerTurn());
				break;
			case FARM:
				manager.changeResourceValue("Food", building.getResourcePerTurn());
				break;
			case TURBINE:
				manager.changeResourceValue("Energy", building.getResourcePerTurn());
				break;
			case STONEEXTRACTOR:
				break;
		}
	}
}
