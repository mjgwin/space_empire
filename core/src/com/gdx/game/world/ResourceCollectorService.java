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
					if(surface[i][j].getBuilding().equals("none")) continue;
					if(currCell.getBuilding().equals("alloyMine")) {
						currManager.changeResourceValue("Metal", 10);
					}
				}
			}
			
		}
		
		currMonth = newMonth;
	}
}
