package com.gdx.game.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.gdx.game.utils.DiskLoader;
import com.gdx.game.world.Ship.ShipType;
import com.gdx.game.world.Star.StarType;

public class World {
	
	private int width, height;

	private ArrayList<Star> stars;
	private ArrayList<Planet> planets;
	private ArrayList<String> planetNames;
	private ArrayList<Ship> ships;
	private ArrayList<Empire> empires;
	
	private Empire playerEmpire;
	private Sprite startingPlanetSymbol;
	private float startingSymbolSize = 15;
	private Sprite universeCenter;
	
	private Random rand;
	
	private Empire placeHolderEmpire;
	
	public World(int width, int height) {
		this.width = width;
		this.height = height;
		rand = new Random();
		stars = new ArrayList<Star>();
		planets = new ArrayList<Planet>();
		ships = new ArrayList<Ship>();
		empires = new ArrayList<Empire>();
		planetNames = DiskLoader.loadLinesFromFile("planet_names.data");
		placeHolderEmpire = new Empire("NO_OWNER_FOUND", false, true);
	}
	
	public void createUniverse(int numSystems) {
		UniverseGenerator universeGenerator = new UniverseGenerator(this.width, this.height, numSystems);
		universeGenerator.generate();
		
		ArrayList<Vector2> starPos = universeGenerator.getStarPositions();
		
		for(Vector2 pos : starPos) {
			stars.add(new Star(pos, StarType.WHITE_STAR));
		}
		
		ArrayList<Vector2> systemPos = universeGenerator.getSystemCenters();
		
		for(Vector2 pos : systemPos) {
			String systemName = generatePlanetName();
			Planet toAdd = new Planet(pos, systemName, Planet.TYPE_SUN);
			toAdd.setOwner(placeHolderEmpire);
			planets.add(toAdd);
		}
		
		ArrayList<Vector2> planetPos = universeGenerator.getPlanetPositions();
		
		for(Vector2 pos : planetPos) {
			String planetName = generatePlanetName();
			Planet toAdd = new Planet(pos, planetName, Planet.TYPE_PLANET);
			toAdd.setOwner(placeHolderEmpire);
			planets.add(toAdd);
		}
		
	}
	
	private String generatePlanetName() {
		int numWords = planetNames.size();
		String word1 = planetNames.get(rand.nextInt(numWords));
		String word2 = planetNames.get(rand.nextInt(numWords));
		int randNum = rand.nextInt(100);
		char letter = (char)(rand.nextInt(26) + 'a');
		
		StringBuilder sb = new StringBuilder();
		sb.append(word1);
		sb.append(" ");
		sb.append(word2);
		sb.append(" ");
		sb.append(randNum);
		sb.append(letter);
		
		return sb.toString();
	}
	
	private void createStartingPlanetSymbol() {
		startingPlanetSymbol = new Sprite(WorldTextures.STARTING_SYMBOL_TEX);
		Sprite startingSprite = playerEmpire.getStartingPlanet().getSprite();
		startingPlanetSymbol.setBounds(startingSprite.getX() + (startingSprite.getWidth() / 2.5f),
				startingSprite.getY() + (startingSprite.getHeight() / 2.5f), startingSymbolSize, startingSymbolSize);
	}
	
	public void spawnShip(Vector2 spawnPos, ShipType type, Empire owner) {
		String name = type.name() + "_" + owner.getOwnedShips().size();
		Ship newShip = new Ship(spawnPos, type, owner, name);
		ships.add(newShip);
		owner.addShip(newShip);
	}
	
	public void createEmpire(String name, boolean isMainPlayer) {
		Empire newEmpire = new Empire(name, isMainPlayer, false);
		empires.add(newEmpire);
		if(isMainPlayer) {
			playerEmpire = newEmpire;
			spawnEmpireOnStartingPlanet(playerEmpire);
			createStartingPlanetSymbol();
		}
	}
	
	public void update() {
		for(Ship s : ships) {
			s.updateMovement();
		}
	}

	public ArrayList<Star> getStars() {
		return stars;
	}
	
	
	public ArrayList<Planet> getPlanets() {
		return planets;
	}
	
	public ArrayList<Ship> getShips() {
		return ships;
	}
	
	public ArrayList<Empire> getEmpires(){
		return empires;
	}
	
	public Empire getEmpireByName(String name) {
		for(Empire e : empires) {
			if(e.getName() == name) {
				return e;
			}
		}
		return null;
	}
	
	public Empire getPlayerEmpire() {
		return playerEmpire;
	}
	
	public Sprite getStartingPlanetSymbol() {
		return startingPlanetSymbol;
	}
	
	public void spawnEmpireOnStartingPlanet(Empire e) {
		int numPlanets = planets.size();
		int toSpawn = rand.nextInt(numPlanets);
		Planet selected = planets.get(toSpawn);
		selected.setOwner(e);
		e.addPlanet(selected);
	}
	
	
	
	
	
}
