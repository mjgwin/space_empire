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
	
	

}
