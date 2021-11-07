package com.gdx.game.world;

import java.util.ArrayList;

public class Empire {
	
	private String name;
	private ArrayList<Ship> ownedShips;
	private ArrayList<Planet> ownedPlanets;
	private int totalPopulation;
	private boolean mainPlayer;
	private Planet startingPlanet;
	private ResourceManager resourceManager;

	public Empire(String name, boolean mainPlayer) {
		this.name = name;
		this.mainPlayer = mainPlayer;
		ownedShips = new ArrayList<Ship>();
		ownedPlanets = new ArrayList<Planet>();
		resourceManager = new ResourceManager(this);
	}
	
	public void addShip(Ship newShip) {
		ownedShips.add(newShip);
	}
	
	public void addPlanet(Planet newPlanet) {
		if(ownedPlanets.isEmpty()) startingPlanet = newPlanet;
		ownedPlanets.add(newPlanet);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Ship> getOwnedShips() {
		return ownedShips;
	}

	public void setOwnedShips(ArrayList<Ship> ownedShips) {
		this.ownedShips = ownedShips;
	}

	public ArrayList<Planet> getOwnedPlanets() {
		return ownedPlanets;
	}

	public void setOwnedPlanets(ArrayList<Planet> ownedPlanets) {
		this.ownedPlanets = ownedPlanets;
	}

	public int getTotalPopulation() {
		return totalPopulation;
	}

	public void setTotalPopulation(int totalPopulation) {
		this.totalPopulation = totalPopulation;
	}

	public boolean isMainPlayer() {
		return mainPlayer;
	}
	
	public Planet getStartingPlanet() {
		return startingPlanet;
	}
	
	public ResourceManager getResourceManager() {
		return resourceManager;
	}
	
	

	
	
}
