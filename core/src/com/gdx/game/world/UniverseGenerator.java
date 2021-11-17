package com.gdx.game.world;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.gdx.game.utils.GameLogger;

public class UniverseGenerator {
	
	private final int STAR_SIZE = Star.DEFAULT_SIZE;
	private final int PLANET_SIZE = Planet.DEFAULT_SIZE;
	private final int SYSTEM_SIZE = 800;
	private final int MAX_PLANETS_IN_SYSTEM = 4;
	private final int MIN_PLANETS_IN_SYSTEM = 1;
	private final int MIN_PLANET_SPACING_FROM_CENTER = 200;
	private final int STAR_DENSITY = 30;
	private final int MAX_ATTEMPTS = 50000;
	
	private int width;
	private int height;
	private int numSystems;
	
	private ArrayList<Vector2> starPositions;
	private ArrayList<Vector2> planetPositions;
	private ArrayList<Vector2> systemCenters;
	private Vector2 galaxyCenter;
	
	private Random rand;
	
	private boolean generated;
	
	public UniverseGenerator(int width, int height, int numSystems) {
		this.width = width;
		this.height = height;
		this.numSystems = numSystems;
		starPositions = new ArrayList<Vector2>();
		planetPositions = new ArrayList<Vector2>();
		systemCenters = new ArrayList<Vector2>();
		galaxyCenter = new Vector2();
		rand = new Random();
		generated = false;
	}
	
	public void generate() {
		if(width == 0 || height == 0 || numSystems == 0) GameLogger.errorLog("bad args in universeGenerator");
		
		generateStars();
		generateSystems();
		generatePlanets();
		
		generated = true;
	}
	
	private void generateStars() {
		int numStars = numSystems * STAR_DENSITY;
		int currStars = 0;
		int attempts = 0;
		
		while(currStars < numStars && attempts < MAX_ATTEMPTS) {
			int starX = rand.nextInt(this.width);
			int starY = rand.nextInt(this.height);
			
			if(validStarPos(starX, starY)) {
				starPositions.add(new Vector2(starX, starY));
				currStars++;
			}else {
				attempts++;
			}
		}
		
	}
	
	private boolean validStarPos(int x, int y) {
		if(starPositions.size() == 0) return true;
		Rectangle newRect = new Rectangle(x, y, STAR_SIZE, STAR_SIZE);
		
		for(Vector2 vec : starPositions) {
			Rectangle starRect = new Rectangle(vec.x, vec.y, STAR_SIZE, STAR_SIZE);
			if(newRect.overlaps(starRect)) return false;
		}
		
		return true;
	}
	
	private void generateSystems() {
		int currSystems = 0;
		int attempts = 0;
		
		while(currSystems < numSystems && attempts < MAX_ATTEMPTS) {
			int systemX = rand.nextInt(this.width);
			int systemY = rand.nextInt(this.height);
			
			if(validSystemPos(systemX, systemY)) {
				systemCenters.add(new Vector2(systemX, systemY));
				currSystems++;
			}else {
				attempts++;
			}
		}
	}
	
	private boolean validSystemPos(int x, int y) {
		if(systemCenters.size() == 0) return true;
		Rectangle newRect = new Rectangle(x, y, SYSTEM_SIZE, SYSTEM_SIZE);
		
		for(Vector2 vec : systemCenters) {
			Rectangle systemRect = new Rectangle(vec.x, vec.y, SYSTEM_SIZE, SYSTEM_SIZE);
			if(newRect.overlaps(systemRect)) return false;
		}
		
		return true;
	}
	
	private void generatePlanets() {
		
		for(Vector2 system : systemCenters) {
			int numPlanets = rand.nextInt(MAX_PLANETS_IN_SYSTEM - MIN_PLANETS_IN_SYSTEM) + MIN_PLANETS_IN_SYSTEM;
			int generated = 0;
			int attempts = 0;
			
			while(generated < numPlanets && attempts < MAX_ATTEMPTS) {
				double angleToCenter = Math.random() * 360.0;
				float lengthFromCenter = rand.nextInt(SYSTEM_SIZE / 2 - MIN_PLANET_SPACING_FROM_CENTER) + MIN_PLANET_SPACING_FROM_CENTER;
				float planetX = lengthFromCenter * (float)Math.cos(angleToCenter);
				float planetY = lengthFromCenter * (float)Math.sin(angleToCenter);
				
				planetX += system.x;
				planetY += system.y;
				
				if(validPlanetPos(planetX, planetY)) {
					planetPositions.add(new Vector2(planetX, planetY));
					generated++;
				}else {
					attempts++;
				}
			}
		}
	}
	
	private boolean validPlanetPos(float x, float y) {	
		Rectangle newRect = new Rectangle(x, y, PLANET_SIZE, PLANET_SIZE);
		
		for(Vector2 vec : systemCenters) {
			Rectangle systemRect = new Rectangle(vec.x, vec.y, SYSTEM_SIZE, SYSTEM_SIZE);
			if(newRect.overlaps(systemRect)) return false;
		}
		
		for(Vector2 vec : planetPositions) {
			Rectangle planetRect = new Rectangle(vec.x - PLANET_SIZE, vec.y - PLANET_SIZE, PLANET_SIZE * 3, PLANET_SIZE * 3);
			if(newRect.overlaps(planetRect)) return false;
		}
		
		return true;
	}

	public ArrayList<Vector2> getStarPositions() {
		return starPositions;
	}

	public ArrayList<Vector2> getPlanetPositions() {
		return planetPositions;
	}

	public ArrayList<Vector2> getSystemCenters() {
		return systemCenters;
	}
	
	

}
