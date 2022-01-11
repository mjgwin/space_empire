package com.gdx.game.world;

public class SurfaceBuilding {
	
	public enum BuildingType{
		HOUSING,
		ALLOYMINE,
		CURRENCYMINE,
		FARM,
		TURBINE,
		STONEEXTRACTOR
	}
	
	private BuildingType buildingType;
	private int level;
	private int resourcePerTurn;
	
	public SurfaceBuilding(BuildingType buildingType) {
		this.buildingType = buildingType;
		level = 1;
		resourcePerTurn = 10;
	}

	public BuildingType getBuildingType() {
		return buildingType;
	}

	public void setBuildingType(BuildingType buildingType) {
		this.buildingType = buildingType;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getResourcePerTurn() {
		return resourcePerTurn;
	}

	public void setResourcePerTurn(int resourcePerTurn) {
		this.resourcePerTurn = resourcePerTurn;
	}
	
	public BuildingCost getBuildingCost(BuildingType type) {
		BuildingCost returnCost = null;
		
		switch(type) {
			case HOUSING:
				returnCost = new BuildingCost();
				returnCost.addResourceCost("Food", 10);
				returnCost.addResourceCost("Stone", 10);
				break;
			case ALLOYMINE:
				returnCost = new BuildingCost();
				returnCost.addResourceCost("Currency", 10);
				returnCost.addResourceCost("Stone", 10);
				returnCost.addResourceCost("Energy", 10);
				break;
			case CURRENCYMINE:
				returnCost = new BuildingCost();
				returnCost.addResourceCost("Metal", 10);
				returnCost.addResourceCost("Energy", 10);
				break;
			case FARM:
				returnCost = new BuildingCost();
				returnCost.addResourceCost("Energy", 10);
				returnCost.addResourceCost("Stone", 10);
				break;
			case TURBINE:
				returnCost = new BuildingCost();
				returnCost.addResourceCost("Metal", 10);
				returnCost.addResourceCost("Energy", 10);
				break;
			case STONEEXTRACTOR:
				returnCost = new BuildingCost();
				returnCost.addResourceCost("Currency", 10);
				returnCost.addResourceCost("Metal", 10);
				returnCost.addResourceCost("Energy", 10);
				break;
		}
		
		return returnCost;
	}
	
	

}
